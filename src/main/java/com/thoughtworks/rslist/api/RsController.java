package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.CommonError;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidPostRsParamException;
import com.thoughtworks.rslist.exception.InvalidPostUserParamException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class RsController {
    private List<RsEvent> rsList = new ArrayList<>();
    private List<String> userNameList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    public RsController() {
        userList.add(new User("aaa", 21, "male", "xyz@123.com", "11234567890"));
        userList.add(new User("bbb", 21, "male", "xyz@123.com", "11234567890"));
        userList.add(new User("ccc", 21, "male", "xyz@123.com", "11234567890"));
        userNameList.add("aaa");
        userNameList.add("bbb");
        userNameList.add("ccc");

        rsList.add(new RsEvent("第一件热搜", "1111成功", 1));
        rsList.add(new RsEvent("第二件热搜", "2222成功", 2));
        rsList.add(new RsEvent("第三件热搜", "3333成功", 3));
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RsEventRepository rsEventRepository;

    @GetMapping("rs/lists")
    public ResponseEntity<List<RsEvent>> getRsEvent() {
        return ResponseEntity.ok(rsList);
    }

    @PostMapping("/rs/event")
    @JsonView(RsEvent.RsEventUserInfo.class)
    public ResponseEntity addOneRsEvent(@RequestBody @Valid RsEvent rsEvent, BindingResult result) throws InvalidPostRsParamException {
        if (result.hasErrors()) {
            throw new InvalidPostRsParamException("invalid param");
        }

        if (!userRepository.existsById(rsEvent.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<UserEntity> userEntity = userRepository.findById(rsEvent.getUserId());
        RsEventEntity rsEventEntity = RsEventEntity.builder().keyWord(rsEvent.getKeyWord()).eventName(rsEvent.getEventName()).userEntity(userEntity.get()).build();
        rsEventRepository.save(rsEventEntity);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rs/list")
    public ResponseEntity getRoundList(@RequestParam int start, @RequestParam int end) throws InvalidIndexException {
        if (start < 0 || end > rsList.size()) {
            throw new InvalidIndexException("invalid request param");
        }

        return ResponseEntity.ok(rsList.subList(start, end));
    }

    @ExceptionHandler({InvalidIndexException.class, InvalidPostRsParamException.class, InvalidPostUserParamException.class})
    public ResponseEntity exceptionHandler(Exception ex) {
        Logger logger = LoggerFactory.getLogger(RsController.class);
        logger.error(ex.getMessage());

        CommonError commonError = new CommonError();
        commonError.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonError);
    }

    @GetMapping("/rs/{index}")
    public ResponseEntity getOneRsEvent(@PathVariable int index) throws InvalidIndexException {
        if (index < 0 || index > rsList.size()) {
            throw new InvalidIndexException("invalid index");
        }
        RsEvent rsEvent = rsList.get(index);
        return ResponseEntity.ok(rsEvent);
    }

    @GetMapping("rs/users")
    public ResponseEntity getAllUser() {

        return ResponseEntity.ok(userList);
    }

    @PostMapping("user")
    @JsonView(RsEvent.RsEventUserInfo.class)
    public ResponseEntity addUser(@RequestBody @Valid User user, BindingResult result) throws InvalidPostUserParamException {
        if (result.hasErrors()) {
            throw new InvalidPostUserParamException("invalid user");
        }

        userList.add(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/rs/update/{rsEventId}")
    public ResponseEntity updateRsEvent(@PathVariable int rsEventId,@RequestBody RsEvent rsEvent){
        if(!rsEventRepository.existsById(rsEventId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        RsEventEntity rsEventEntity = rsEventRepository.findById(rsEventId).get();
        int userId = rsEventEntity.getUserEntity().getId();
        if(userId != rsEvent.getUserId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(rsEvent.getEventName() != null){
            rsEventEntity.setEventName(rsEvent.getEventName());
        }

        if(rsEvent.getKeyWord() != null){
            rsEventEntity.setKeyWord(rsEvent.getKeyWord());
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
