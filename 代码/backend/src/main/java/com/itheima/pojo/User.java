package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
@Data
public class User {
    @NonNull
    private Integer id;
    @NonNull
    private String username;
    @JsonIgnore
    private String password;
    @NonNull
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @Email
    private String email;
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
