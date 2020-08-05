package com.thoughtworks.rslist.api;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.*;

@Data
@NotNull
public class User {
    public User() {
    }

    public User(String userName, int age, String gender, String email, String phone) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    @Size(max = 8)
    @JsonProperty("user_name")
    private String userName;

    @Min(18)
    @Max(100)
    @NotNull
    @JsonProperty("user_age")
    private int age;

    @NotNull
    @JsonProperty("user_gender")
    private String gender;

    @Email
    @JsonProperty("user_email")
    private String email;

    @Pattern(regexp = "1\\d{10}")
    @NotNull
    @JsonProperty("user_phone")
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
