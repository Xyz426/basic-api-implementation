package com.thoughtworks.rslist.api;


import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("add/user")
    @JsonView(RsEvent.RsEventUserInfo.class)
    public void addUser(@RequestBody User user){
        UserEntity userEntity = UserEntity.builder().age(user.getAge()).userName(user.getUserName()).email(user.getEmail())
                .phone(user.getPhone()).gender(user.getGender()).build();

        userRepository.save(userEntity);
    }
}
