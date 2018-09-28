package com.kirilo.restaurant.voting.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.util.UserTestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static com.kirilo.restaurant.voting.util.TestUtil.userHttpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
class AdminControlTest {
    private final UserTestData userData = new UserTestData();
    private final ObjectMapper mapper = new ObjectMapper();;
    private static final String REST_URL = AdminControl.REST_URL + "/";

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    //    @Autowired
//    private TestRestTemplate restTemplate;

    protected MockMvc mockMvc;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void restaurantCreate() throws Exception {
        Restaurant restaurant = new Restaurant("Ресторан Golden Star", "some text");
        mockMvc.perform(post(REST_URL + "restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userData.ADMIN))
                .content(mapper.writeValueAsString(restaurant)))
                .andExpect(status().isUnprocessableEntity())
//                .andExpect(jsonPath("$.type").value("Validation error"))
                .andDo(print());
    }

    @Test
    void restaurantUpdate() {
    }

    @Test
    void restaurantDelete() {
    }

    @Test
    void dishCreated() {
    }

    @Test
    void dishUpdated() {
    }

    @Test
    void dishDelete() {
    }

    @Test
    void switchOffUser() {
    }

    @Test
    void switchOnUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void getUserWithRolesById() {
    }
}