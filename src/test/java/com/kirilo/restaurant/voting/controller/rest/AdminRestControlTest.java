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
import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Test;
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
import java.time.LocalDateTime;
import java.util.List;

import static com.kirilo.restaurant.voting.model.AbstractEntity.START_SEQ;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//    https://stackoverflow.com/questions/27126974/how-to-execute-sql-before-a-before-method/27156080
@Sql(value = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class AdminRestControlTest {
    private final Logger logger = Logger.getLogger(AdminRestControlTest.class);
    private final String REST_URL = AdminControl.REST_URL + "/restaurant";
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

//    @BeforeEach
    @Sql(value = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void setUpEach() {
//        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void restaurantCreate() throws Exception {
        Restaurant restaurant = new Restaurant("New Restaurant", "description restaurant", LocalDateTime.now());
        ResponseEntity<Restaurant> responseEntity = (ResponseEntity<Restaurant>) createEntity(REST_URL, restaurant);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Restaurant responseRestaurant = responseEntity.getBody();
        assertTrue(responseRestaurant.getId() > START_SEQ);
        restaurant.setId(responseRestaurant.getId());
        assertEquals(restaurant, responseRestaurant);
    }

    @Test
    public void restaurantUpdate() throws URISyntaxException {
        Restaurant restaurant = new Restaurant(testData.RESTAURANT2_ID, "Second Restaurant", "description ресторан", dateTime.getLocalDateTime("2018-09-10", "11:12:13"));
        Restaurant entity = (Restaurant) updateEntity(REST_URL, restaurant);
        assertEquals(restaurant, entity);
    }

    @Test
    public void restaurantDelete() throws URISyntaxException {
        Restaurant entity = (Restaurant) deleteEntity(REST_URL, testData.RESTAURANT1);
        assertThat(restaurantService.getAll()).isEqualTo(testData.sortedByDate(testData.RESTAURANT2, testData.RESTAURANT3, testData.RESTAURANT4, testData.RESTAURANT5, testData.RESTAURANT6, testData.RESTAURANT7));
        assertEquals(testData.RESTAURANT1, entity);
    }

    @Test
    public void getRestaurant() {
        String localUrl = local + REST_URL + "/{id}";
        Restaurant restaurant = (Restaurant) getEntity(localUrl, testData.RESTAURANT1);
        assertThat(restaurant).isEqualTo(testData.RESTAURANT1);
        logger.info(restaurant);
    }

    @Test
    public void dishCreated() {
        Dish dish = new Dish("new name", 100, LocalDateTime.now());
        ResponseEntity<Dish> responseEntity = (ResponseEntity<Dish>) createEntity(DISH_URL + "/" + testData.RESTAURANT1_ID, dish);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Dish entity = responseEntity.getBody();
        assertTrue(entity.getId() > START_SEQ);
        dish.setId(entity.getId());
        assertEquals(dish, entity);
    }

    @Test
    public void dishUpdated() throws URISyntaxException {
        Dish dish = new Dish(testData.DISH2_ID, "second name", 200, LocalDateTime.now());
        Dish entity = (Dish) updateEntity(DISH_URL, dish);
        assertThat(entity).isEqualTo(dish);
    }

    @Test
    public void dishDelete() throws URISyntaxException {
        Dish entity = (Dish) deleteEntity(DISH_URL, testData.DISH3);
        List<Dish> dishes = dishService.getAll(testData.RESTAURANT2_ID);
        List<Dish> expected = testData.sortedByDate(testData.DISH4, testData.DISH7, testData.DISH8);
        logger.info(dishes);
        logger.info(expected);
        assertThat(dishes).isEqualTo(expected);
        assertEquals(testData.DISH3, entity);
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

    private AbstractNamedEntity updateEntity(String strUrl, AbstractNamedEntity entity) {
        String url = local + strUrl + "/{id}";
        getAdminAuthor()
                .put(url, entity, entity.getId());
        return getEntity(url, entity);
    }

    private AbstractNamedEntity deleteEntity(String url, AbstractNamedEntity entity) {
        String localUrl = local + url + "/{id}";
        AbstractNamedEntity namedEntity = getEntity(localUrl, entity);
        getAdminAuthor()
                .delete(localUrl, entity.getId());
        return namedEntity;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private RestTemplate getAdminAuthor() {
        return restTemplate.withBasicAuth("admin@gmail.com", "password").getRestTemplate();
    }

    private AbstractNamedEntity getEntity(String url, AbstractNamedEntity entity) {
        return getAdminAuthor()
                .getForObject(url, entity.getClass(), entity.getId());
    }
}