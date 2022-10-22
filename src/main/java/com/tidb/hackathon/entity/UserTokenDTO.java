package com.tidb.hackathon.entity;

import lombok.Data;

@Data
public class UserTokenDTO {

    private String id;

    private String username;

    private String password;

    private Long gmtCreate;


}
