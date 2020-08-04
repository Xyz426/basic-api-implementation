package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsEvent;
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
    void contextLoads() {
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {

        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("第一件事件")))
                .andExpect(jsonPath("$.keyWord",is("篮球")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRsEventBetween() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$[0].eventName",is("第一件事件")))
                .andExpect(jsonPath("$[0].keyWord",is("篮球")))
                .andExpect(jsonPath("$[1].eventName",is("第二件事件")))
                .andExpect(jsonPath("$[1].keyWord",is("游泳")))
                .andExpect(jsonPath("$[2].eventName",is("第三件事件")))
                .andExpect(jsonPath("$[2].keyWord",is("足球")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddOneRsEvent() throws Exception {
        RsEvent rsEvent = new RsEvent("第四件事件", "火山爆发");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/4"))
                .andExpect(jsonPath("$.eventName",is("第四件事件")))
                .andExpect(jsonPath("$.keyWord",is("火山爆发")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateOneRsEvent() throws Exception {
        int updateIndex = 1;
        RsEvent rsEvent = new RsEvent("第一件事件", "火山爆发是个假新闻");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(put("/rs/update/{index}",updateIndex).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("第一件事件")))
                .andExpect(jsonPath("$.keyWord",is("火山爆发是个假新闻")))
                .andExpect(status().isOk());
    }
}