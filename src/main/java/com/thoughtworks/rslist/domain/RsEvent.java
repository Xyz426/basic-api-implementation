package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private @Valid User user;

    public RsEvent(){

    }

    public RsEvent(String eventName, String keyWord) {
        this.eventName = eventName;
        this.keyWord = keyWord;
    }

    public RsEvent(String eventName, String keyWord,User user) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.user = user;
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
    public User getUser() {
        return user;
    }

    @JsonView(RsEventUserInfo.class)
    public void setUser(User user) {
        this.user = user;
    }
}