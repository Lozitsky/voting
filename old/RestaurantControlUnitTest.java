package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.util.RestaurantTestData;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
public class RestaurantControlUnitTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private String REST_URL = RestaurantControl.REST_URL;

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    RestaurantControl controller;

    @Autowired
    RestaurantService service;

    //@Autowired
    private final RestaurantTestData testData = new RestaurantTestData();
    private final ValidationDateTime dateTime = new ValidationDateTime();

    private List<Restaurant> restaurants;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();

        restaurants = new ArrayList<>();
//        dateTime.getDate(stringDate, "00:00:00"), dateTime.getDate("02-02-2018", "02:02:02")
        restaurants.add(new Restaurant(testData.RESTAURANT1_ID, "Ararat", "some description", dateTime.getDate("2018-02-02", "02:02:02")));

/*        restaurants.add(testData.RESTAURANT1);
        restaurants.add(testData.RESTAURANT2);
        restaurants.add(testData.RESTAURANT3);
        restaurants.add(testData.RESTAURANT4);
        restaurants.add(testData.RESTAURANT5);
        restaurants.add(testData.RESTAURANT6);
        restaurants.add(testData.RESTAURANT7);*/
    }

    @WithMockUser(value = "admin@gmail.com")
    @Test
    public void getAll() throws Exception {

        List<Restaurant> list = service.getAll();
//        when(list).thenReturn(restaurants);

        mockMvc.perform(get("/rest/restaurants").contentType(MediaType.APPLICATION_JSON)
//                .with(SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken("admin@gmail.com","password"))))
//                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin@gmail.com", "password")))
        )
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$[0].id", is(testData.RESTAURANT1)));
    }


}
//import static com.kirilo.restaurant.voting.controller.rest.AdminControl.REST_URL_RESTAURANT;
