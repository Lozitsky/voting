package com.kirilo.restaurant.voting.controller.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilo.restaurant.voting.model.AbstractNamedEntity;
import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.util.RestaurantTestData;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Date;

import static com.kirilo.restaurant.voting.model.AbstractEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//    https://stackoverflow.com/questions/27126974/how-to-execute-sql-before-a-before-method/27156080
@Sql(value = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class AdminRestControlTest {
    private final String REST_URL = AdminControl.REST_URL + "/restaurant";
    private final String RESTAURANT_URL = RestaurantControl.REST_URL;
    private final String DISH_URL = AdminControl.REST_URL + "/dish";

    private String local = "http://localhost:";
//    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    @Autowired
    private final ObjectMapper jsonMapper = new ObjectMapper();

/*    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }*/

//    @Autowired
//    private CacheManager cacheManager;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @LocalServerPort
    private int port;

    private ValidationDateTime dateTime;
    private RestaurantTestData testData;

    @Before
    public void setUp() throws Exception {
//        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        jsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        local += port + "/";
        dateTime = new ValidationDateTime();
        testData = new RestaurantTestData();
    }

    @BeforeEach
    @Sql(value = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void setUpEach() {
//        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void restaurantCreate() throws Exception {
        Restaurant restaurant = new Restaurant("New Restaurant", "description restaurant", dateTime.getDateToday());
        ResponseEntity<Restaurant> responseEntity = (ResponseEntity<Restaurant>) createEntity(REST_URL, restaurant);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Restaurant responseRestaurant = responseEntity.getBody();
        assertTrue(responseRestaurant.getId() > START_SEQ);
        restaurant.setId(responseRestaurant.getId());
        assertEquals(restaurant, responseRestaurant);
    }

    @Test
    public void restaurantUpdate() throws URISyntaxException {
        Restaurant restaurant = new Restaurant(testData.RESTAURANT2_ID, "Second Restaurant", "description ресторан", dateTime.getDate("2018-09-10", "11:12:13"));
        Restaurant entity = (Restaurant) updateEntity(RESTAURANT_URL, restaurant);
        assertEquals(restaurant, entity);
    }

    @Test
    public void restaurantDelete() {
        deleteEntity(REST_URL, testData.RESTAURANT1_ID);
        assertThat(restaurantService.getAll()).isEqualTo(testData.sortedByDate(testData.RESTAURANT2, testData.RESTAURANT3, testData.RESTAURANT4, testData.RESTAURANT5, testData.RESTAURANT6, testData.RESTAURANT7));
    }

    @Test
    public void dishCreated() {
        Dish dish = new Dish("new name", 100, dateTime.getDateToday());
        ResponseEntity<Dish> responseEntity = (ResponseEntity<Dish>) createEntity(DISH_URL + "/" + testData.RESTAURANT1_ID, dish);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Dish entity = responseEntity.getBody();
        assertTrue(entity.getId() > START_SEQ);
        dish.setId(entity.getId());
        assertEquals(dish, entity);
    }

    @Test
    public void dishUpdated() throws URISyntaxException {
        Dish dish = new Dish(testData.DISH2_ID, "second name", 200, new Date());
        Dish entity = (Dish) updateEntity(DISH_URL, dish);
        assertThat(entity).isEqualTo(dish);
    }

    @Test
    public void dishDelete() {
    }

    @Test
    public void switchOffUser() {
    }

    @Test
    public void switchOnUser() {
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void getUserWithRolesById() {
    }

    private ResponseEntity<? extends AbstractNamedEntity> createEntity(String restUrl, AbstractNamedEntity entity) {
        return getAdminAuthor()
                .exchange(local + restUrl, HttpMethod.POST, new HttpEntity<>(entity, getHeaders()), entity.getClass());
    }

    private AbstractNamedEntity updateEntity(String strUrl, AbstractNamedEntity entity) throws URISyntaxException {
        int id = entity.getId();
        String url = local + strUrl + "/{id}";
        getAdminAuthor()
                .put(url, entity, id);
        return getAdminAuthor()
                .getForObject(url, entity.getClass(), id);
    }

    private void deleteEntity(String url, int id) {
        getAdminAuthor()
                .delete(local + url + "/" + id);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private RestTemplate getAdminAuthor() {
        return restTemplate.withBasicAuth("admin@gmail.com", "password").getRestTemplate();
    }
}