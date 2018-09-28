package com.kirilo.restaurant.voting.controller.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes={VotingApplicationTests.class})
//@SpringJUnitConfig(VotingApplicationTests.class)
//@ContextConfiguration(loader = SpringBootContextLoader.class)
/*@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest(AdminControl.class)*/
public class AdminResourceControllerTests {
    private static final String REST_URL = AdminControl.REST_URL;

//    @Autowired
    private MockMvc mvc;

    @Test
    public void getUser() throws Exception {

        mvc.perform(get(REST_URL + "/users")
                .with(user("admin@gmail.com").password("password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
