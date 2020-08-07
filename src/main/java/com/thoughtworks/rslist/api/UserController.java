package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    RsEventRepository rsEventRepository;

    @PostMapping("user")
    public void addUser(@RequestBody User user){
        UserEntity userEntity = UserEntity.builder().age(user.getAge()).userName(user.getUserName()).email(user.getEmail())
                .phone(user.getPhone()).gender(user.getGender()).build();

        userRepository.save(userEntity);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity getUserById(@PathVariable int userId){
        UserEntity userEntity = userRepository.findById(userId).get();

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("user/{userId}")
//  @Transactional
    public ResponseEntity deleteUserById(@PathVariable int userId){
        userRepository.deleteById(userId);
//      rsEventRepository.deleteAllByUserId(userId);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
