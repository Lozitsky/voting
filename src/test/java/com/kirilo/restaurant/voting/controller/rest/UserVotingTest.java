package com.kirilo.restaurant.voting.controller.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.service.VotingService;
import com.kirilo.restaurant.voting.util.RestaurantTestData;
import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//    https://stackoverflow.com/questions/27126974/how-to-execute-sql-before-a-before-method/27156080
@Sql(value = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class UserVotingTest {
    private final Logger logger = Logger.getLogger(UserVotingTest.class);
    private final String VOTE_URL = UserVoting.REST;
    private String local = "http://localhost:";
    private RestaurantTestData testData;

    @Autowired
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VotingService votingService;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        jsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        local += port;
        testData = new RestaurantTestData();
    }

    @Test
    public void voteFor() {
        Vote voted = doVote(testData.RESTAURANT3_ID, "user@ukr.net");
        assertThat(voted.getNumberOfVotes()).isEqualTo(1);
        Vote voted2 = doVote(testData.RESTAURANT3_ID, "user2@yandex.ru");
        assertThat(voted2.getNumberOfVotes()).isEqualTo(2);
        Vote voted3 = doVote(testData.RESTAURANT3_ID, "user3@mail.ru");
        assertThat(voted3.getNumberOfVotes()).isEqualTo(3);
        Vote vote = votingService.getByRestaurantId(testData.RESTAURANT3_ID);
        assertThat(vote.getNumberOfVotes()).isEqualTo(voted3.getNumberOfVotes());
    }

    @Test
    public void voteWhenNoMenu() {
        ResponseEntity<Vote> responseEntity = doVoteWithGetEntity(testData.RESTAURANT7_ID, getUserAuthor("user@ukr.net"));
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    public void voteWhenNoRestaurant() {
        ResponseEntity<Vote> responseEntity = doVoteWithGetEntity(testData.RESTAURANT7_ID + 1, getUserAuthor("user@ukr.net"));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void votingAdmin() {
        ResponseEntity<Vote> responseEntity = doVoteWithGetEntity(testData.RESTAURANT3_ID, getAdminAuthor());
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    public void badLink() {
        ResponseEntity<Vote> responseEntity = getUserAuthor("user@ukr.net")
                .getForEntity(local + "/voteFor/{id}", Vote.class, testData.RESTAURANT3_ID);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void votesTodayRedirectWhenNoVoted() {
        ResponseEntity<List> responseEntity = getEntities("/votes", "user@ukr.net");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List expected = getUserAuthor("user@ukr.net")
                .getForObject(local + VOTE_URL + "/dishes/forVoting", List.class);
        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    public void votesTodayWhenVoted() {
        doVote(testData.RESTAURANT3_ID, "user@ukr.net");
        List<Vote> expectedList = votingService.getWithRestaurantsByDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));

        ResponseEntity<List<Vote>> responseEntity = getParametrizedTypeEntities("/votes", "user@ukr.net");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        logger.info(responseEntity.getBody());
        List<Vote> actual = responseEntity.getBody();
        assertThat(actual).isEqualTo(expectedList);
    }

    @Test
    public void dishesWithRestaurants() {
    }


    private RestTemplate getAdminAuthor() {
        return restTemplate.withBasicAuth("admin@gmail.com", "password").getRestTemplate();
    }

    private RestTemplate getUserAuthor(String login) {
        return restTemplate.withBasicAuth(login, "password").getRestTemplate();
    }


    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private Vote doVote(int id, String login) {
        return getUserAuthor(login)
                .getForObject(local + VOTE_URL + "/voteFor/{id}", Vote.class, id);
    }

    private ResponseEntity<Vote> doVoteWithGetEntity(int id, RestTemplate authorization) {
        return authorization
                .getForEntity(local + VOTE_URL + "/voteFor/{id}", Vote.class, id);
    }

    private ResponseEntity<List> getEntities(String url, String login) {
        return getUserAuthor(login)
                .getForEntity(local + VOTE_URL + url, List.class);
    }

    private ResponseEntity<List<Vote>> getParametrizedTypeEntities(String url, String login) {
        ParameterizedTypeReference<List<Vote>> paramType = new ParameterizedTypeReference<List<Vote>>() {};
        return getUserAuthor(login)
                .exchange(local + VOTE_URL + url, HttpMethod.GET, null, paramType);
    }
}