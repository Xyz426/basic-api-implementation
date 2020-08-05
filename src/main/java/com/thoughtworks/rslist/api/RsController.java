package com.thoughtworks.rslist.api;

import org.springframework.http.ResponseEntity;
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

        rsList.add(new RsEvent("第一件热搜", "1111成功", userList.get(0)));
        rsList.add(new RsEvent("第二件热搜", "2222成功", userList.get(1)));
        rsList.add(new RsEvent("第三件热搜", "3333成功", userList.get(2)));
    }


    @GetMapping("rs/list")
    public ResponseEntity<List<RsEvent>> shouldGetRsEvent() {
        return ResponseEntity.ok(rsList);
    }

    @PostMapping("/rs/event")
    public ResponseEntity shouldAddOneRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        User user = rsEvent.getUser();
        if (!userNameList.contains(user.getUserName())) {
            userNameList.add(user.getUserName());
            userList.add(user);
        }
        rsList.add(rsEvent);

        int index = rsList.size() - 1;
        return ResponseEntity.created(null).header("index", String.valueOf(index)).build();
    }

    @GetMapping("/rs/{index}")
    public ResponseEntity shouldGetOneRsEvent(@PathVariable int index) {
        RsEvent rsEvent = rsList.get(index);
        return ResponseEntity.ok(rsEvent);
    }

    @GetMapping("rs/users")
    public ResponseEntity shouldGetAllUser(){

        return ResponseEntity.ok(userList);
    }
}
