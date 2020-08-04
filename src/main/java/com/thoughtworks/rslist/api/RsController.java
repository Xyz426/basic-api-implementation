package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = init();

    private List<RsEvent> init() {
        rsList = new ArrayList<>();
        rsList.add(new RsEvent("第一件事件", "篮球"));
        rsList.add(new RsEvent("第二件事件", "游泳"));
        rsList.add(new RsEvent("第三件事件", "足球"));

        return rsList;
    }


    @GetMapping("rs/{index}")
    public RsEvent shouldGetOneRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }

    @GetMapping("rs/list")
    public List<RsEvent> shouldGetRsEventBetween(@RequestParam int start, @RequestParam int end) {
        return rsList.subList(start - 1, end);
    }

    @PostMapping("rs/event")
    public void shouldAddOneRsEvent(@RequestBody String rsEventString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RsEvent rsEvent = objectMapper.readValue(rsEventString, RsEvent.class);
        rsList.add(rsEvent);
    }

    @PutMapping("rs/update/{index}")
    public void shouldUpdateOneRsEvent(@PathVariable int index, @RequestBody String rsEventString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RsEvent rsEvent = objectMapper.readValue(rsEventString, RsEvent.class);

        if (rsList.size() >= index - 1) {
            rsList.get(index - 1).setEventName(rsEvent.getEventName());
            rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
        }
    }

    @DeleteMapping("rs/delete/{indexDelete}")
    public void shouldDeleteOneRsEvent(@PathVariable int indexDelete) {
        if (rsList.get(indexDelete) != null) {
            rsList.remove(indexDelete - 1);
        }
    }

}
