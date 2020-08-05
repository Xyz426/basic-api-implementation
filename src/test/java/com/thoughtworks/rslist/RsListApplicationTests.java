
package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddOneRsEvent() throws Exception {
        User user = new User("ddd", 21, "male", "xyz@123.com", "11234567890");
        RsEvent rsEvent = new RsEvent("第四件热搜", "4444成功", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("index","3"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/lists"))
                .andExpect(jsonPath("$[3].eventName", is("第四件热搜")))
                .andExpect(jsonPath("$[3].keyWord", is("4444成功")))
                .andExpect(jsonPath("$[3].user.userName", is("ddd")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("第二件热搜")))
                .andExpect(jsonPath("$.keyWord",is("2222成功")))
                .andExpect((jsonPath("$", not(hasKey("user")))))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/10"))
                .andExpect(jsonPath("$.error",is("invalid index")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllUser() throws Exception {
        mockMvc.perform(get("/rs/users"))
                .andExpect(jsonPath("$[0].user_name",is("aaa")))
                .andExpect(jsonPath("$[1].user_age",is(21)))
                .andExpect((jsonPath("$[2].user_gender", is("male"))))
                .andExpect((jsonPath("$[2].user_email", is("xyz@123.com"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRoundList() throws Exception {
        mockMvc.perform(get("/rs/list?start=-1&end=2"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());
    }
}
