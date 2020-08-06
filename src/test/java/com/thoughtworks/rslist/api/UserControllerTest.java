package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RsEventRepository rsEventRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
    }

    @Test
    void shouldAddUser() throws Exception {
        User user = new User("ddd", 21, "male", "xyz@123.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/add/user").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();
        assertEquals(1, users.size());
    }

    @Test
    void shouldGetUser() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(jsonPath("$.age",is(21)))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        UserEntity userEntity = userRepository.save(UserEntity.builder().age(18).email("aa@a.com").gender("male").phone("1222334567").userName("hhh").build());

        rsEventRepository.save(RsEventEntity.builder().userEntity(userEntity).keyWord("hewa").eventName("nizh").build());
        rsEventRepository.save(RsEventEntity.builder().userEntity(userEntity).keyWord("aaa").eventName("xxxx").build());
        mockMvc.perform(delete("/user/"+userEntity.getId()))
                .andExpect(status().isOk());


        assertEquals(0,userRepository.findAll().size());
        assertEquals(0,rsEventRepository.findAll().size());
    }
}
