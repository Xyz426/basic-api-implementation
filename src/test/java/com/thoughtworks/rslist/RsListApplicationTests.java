
package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsEvent;
import com.thoughtworks.rslist.api.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddOneRsEvent() throws Exception {
        User user = new User("xiaolizi", 21, "male", "xyz@123.com", "11234567890");

        RsEvent rsEvent = new RsEvent("第一件热搜", "hhhh成功", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一件热搜")))
                .andExpect(jsonPath("$[0].keyWord", is("hhhh成功")))
                .andExpect(jsonPath("$[0].user.userName", is("xiaolizi")))
                .andExpect(status().isOk());
    }

    @Test
    void nameShouldNotLonger8() throws Exception {
        User user = new User("xiaolizixxxx", 21, "male", "xyz@123.com", "11234567890");

        RsEvent rsEvent = new RsEvent("第二件热搜", "hhhh失败", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldBetween18and100() throws Exception {
        User user = new User("xiaolizixxxx", 212, "male", "xyz@123.com", "11234567890");

        RsEvent rsEvent = new RsEvent("第二件热搜", "hhhh失败", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldNotNullGender() throws Exception {
        User user = new User("xiaoxxxx", 212, "", "xyz@123.com", "11234567890");

        RsEvent rsEvent = new RsEvent("第二件热搜", "hhhh失败", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldPattenEmail() throws Exception {
        User user = new User("xiaoxxxx", 212, "male", "xyz123.com", "11234567890");

        RsEvent rsEvent = new RsEvent("第二件热搜", "hhhh失败", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldLengthMoreThan11() throws Exception {
        User user = new User("xiaoxxxx", 212, "male", "xyz123.com", "234567890");

        RsEvent rsEvent = new RsEvent("第二件热搜", "hhhh失败", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
