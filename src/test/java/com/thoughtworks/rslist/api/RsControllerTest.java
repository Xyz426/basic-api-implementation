package com.thoughtworks.rslist.api;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
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
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RsEventRepository rsEventRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldAddOneRsEvent() throws Exception {
        //UserEntity userEntity = userRepository.save(UserEntity.builder().gender("female").phone("12334343222").email("xyz@qq.com").userName("hello").age(18).build());

        RsEvent rsEvent = new RsEvent("第3件热搜", "4444成功", 3);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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

    @Test
    void shouleAddUser() throws Exception {
        User user = new User("ddd", 2221, "male", "xyz@123.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid user")))
                .andExpect(status().isBadRequest());
    }
}
