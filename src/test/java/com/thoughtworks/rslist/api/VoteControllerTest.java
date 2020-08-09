package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import java.time.LocalDateTime;
import java.util.List;

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

        int tickets = 2;

        String requstJson = "{\"tickets\":" + tickets + ",\"userId\":" + userId + "}";


        //指定投票对应的事件ID
        int rsEventId = rsEventEntity.getId();
        mockMvc.perform(put("/rs/vote/{rsEventId}", rsEventId).content(requstJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //需要重新在数据库内取
        rsEventEntity = rsEventRepository.findById(rsEventEntity.getId()).get();
        assertEquals(2, rsEventEntity.getTickets());
    }

    @Test
    void shouldQueryRsEvntVoteRoundTime() throws Exception {
        VoteEntity voteTemp = VoteEntity.builder().rsEventId(1).tickets(3).userId(1)
                .voteTime(LocalDateTime.of(2020, 8, 1, 14, 2, 3))
                .build();

        voteRepository.save(voteTemp);

        voteTemp = VoteEntity.builder().rsEventId(1).tickets(3).userId(1)
                .voteTime(LocalDateTime.of(2020, 8, 3, 23, 2, 3))
                .build();

        voteRepository.save(voteTemp);

        voteTemp = VoteEntity.builder().rsEventId(1).tickets(3).userId(1)
                .voteTime(LocalDateTime.of(2020, 8, 5, 21, 2, 3))
                .build();

        voteRepository.save(voteTemp);


        VoteEntity voteTest1 = VoteEntity.builder().rsEventId(1).tickets(3).userId(1)
                .voteTime(LocalDateTime.of(2020, 7, 1, 14, 2, 3))
                .build();
        VoteEntity voteTest2 = VoteEntity.builder().rsEventId(1).tickets(3).userId(1)
                .voteTime(LocalDateTime.of(2020, 8, 4, 14, 2, 3))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String startJson = objectMapper.writeValueAsString(voteTest1);
        String endJson = objectMapper.writeValueAsString(voteTest2);

        mockMvc.perform(get("/vote/time?{start}&{end}",startJson,endJson))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isOk());

    }
}
