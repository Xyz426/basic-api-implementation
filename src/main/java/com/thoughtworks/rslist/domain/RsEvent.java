package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class RsEvent {

    public interface RsEventUserInfo{}

    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;

    private @Valid Integer userId;

    public RsEvent(){

    }

    public RsEvent(String eventName, String keyWord) {
        this.eventName = eventName;
        this.keyWord = keyWord;
    }

    public RsEvent(String eventName, String keyWord, Integer userId) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @JsonView(RsEventUserInfo.class)
    public Integer getUserId() {
        return userId;
    }

    @JsonView(RsEventUserInfo.class)
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}