package com.thoughtworks.rslist.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RsController {
    private List<RsEvent> rsList = new ArrayList<>();
    private Map<String,User> userMap = new HashMap<>();


    @GetMapping("rs/list")
    public List<RsEvent> shouldGetRsEvent(){
        return rsList;
    }

    @PostMapping("/rs/event")
    public void shouldAddOneRsEvent(@RequestBody @Valid RsEvent rsEvent){
        @Valid
        User user = rsEvent.getUser();
        if(!userMap.containsKey(user.getUserName())){
            userMap.put(user.getUserName(),user);
        }

        rsList.add(rsEvent);
    }

}
