package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("add/user")
    public void addUser(@RequestBody User user){
        UserEntity userEntity = UserEntity.builder().age(user.getAge()).userName(user.getUserName()).email(user.getEmail())
                .phone(user.getPhone()).gender(user.getGender()).build();

        userRepository.save(userEntity);
    }

    @GetMapping("user/{index}")
    public ResponseEntity getUserById(@PathVariable int index){
        UserEntity userEntity = userRepository.findById(index).get();

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("user/{index}")
    public ResponseEntity deleteUserById(@PathVariable int index){
        userRepository.deleteById(index);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
