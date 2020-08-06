package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RsEventRepository rsEventRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
        voteRepository.deleteAll();
    }

    @Test
    void shouldAddRsEvntVoteNum() throws Exception {

        UserEntity userEntity = userRepository.save(UserEntity.builder().age(18).
                email("aa@a.com").gender("male").phone("1222334567").userName("hhh").tickets(5).build());//该用户有5票
        int userId = userEntity.getId();



        RsEventEntity rsEventEntity = rsEventRepository.save(RsEventEntity.builder().userEntity(userEntity).keyWord("111！")
                .eventName("热搜111！").build());

        int tickets = 2;//用户投两派哦

//          在建vote表时加上date会报错
//        Date date = new Date(System.currentTimeMillis());
//        String requstJson = "{\"tickets\":" + tickets + ",\"userId\":" + userId + ",\"currentTime\":" + date.toString() + "}";
        String requstJson = "{\"tickets\":" + tickets + ",\"userId\":" + userId + "}";


        //指定投票对应的事件ID
        int rsEventId = rsEventEntity.getId();
        mockMvc.perform(put("/rs/vote/{rsEventId}", rsEventId).content(requstJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //???神马情况，明明数据库已表中已经有了票
        assertEquals(2, rsEventEntity.getTickets());


    }
}
