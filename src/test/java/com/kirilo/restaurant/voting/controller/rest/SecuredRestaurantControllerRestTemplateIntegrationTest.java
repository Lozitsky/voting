package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.Restaurant;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class SecuredRestaurantControllerRestTemplateIntegrationTest {
    private final Logger logger = Logger.getLogger(SecuredRestaurantControllerRestTemplateIntegrationTest.class);
    private final static String URL = RestaurantControl.REST_URL;
    private String local = "http://localhost:";

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    private int port;

    private List<Restaurant> restaurants;

    private RestaurantTestData testData;

    @Before
    public void setup() {
        local += port + "/";
        testData = new RestaurantTestData();
        restaurants = new ArrayList<>();

        restaurants.add(testData.RESTAURANT1);
        restaurants.add(testData.RESTAURANT2);
        restaurants.add(testData.RESTAURANT3);
        restaurants.add(testData.RESTAURANT4);
        restaurants.add(testData.RESTAURANT5);
        restaurants.add(testData.RESTAURANT6);
        restaurants.add(testData.RESTAURANT7);
    }

/*    @Test
    public void givenAuthRequest() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("admin@gmail.com", "password")
                .getForEntity("/rest/restaurants", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }*/

    @Test
    public void getAll() throws Exception{
        ParameterizedTypeReference<List<Restaurant>> paramType = new ParameterizedTypeReference<List<Restaurant>>() {};
        ResponseEntity<List<Restaurant>> list = template
                .withBasicAuth("admin@gmail.com", "password")
                .exchange(local + URL, HttpMethod.GET, null, paramType);

        assertThat(list.getBody()).isEqualTo(testData.sortedByDate(restaurants));
    }

    @Test
    public void getWithDishes() {
        ParameterizedTypeReference<List<Restaurant>> paramType = new ParameterizedTypeReference<List<Restaurant>>() {};
        ResponseEntity<List<Restaurant>> list = template
                .withBasicAuth("admin@gmail.com", "password")
                .exchange(local + URL + "/dishes", HttpMethod.GET, null, paramType);
        List<Restaurant> actual = list.getBody();
        assertThat(actual)
//                .usingElementComparatorIgnoringFields("dishes")
                .containsAll(restaurants);
        assertThat((actual.get(2)).getDishes()).isEqualTo(testData.sortedByDate(testData.DISH1, testData.DISH2));
        assertThat(actual.get(6).getDishes()).isEqualTo(testData.sortedByDate(testData.DISH3, testData.DISH7, testData.DISH8, testData.DISH4));
        assertThat(actual.get(4).getDishes()).isEqualTo(testData.sortedByDate(testData.DISH6, testData.DISH5));
    }

    @Test
    public void getWithDishesById() {
    }

    @Test
    public void getWithDishesByDate() {
    }

    @Test
    public void getWithVotesByDate() {
    }

    @Test
    public void getWithVotes() {
    }

    @Test
    public void getWithVotesById() {
    }


}
