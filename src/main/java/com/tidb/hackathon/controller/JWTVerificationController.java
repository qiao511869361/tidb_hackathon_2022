package com.tidb.hackathon.controller;


import com.tidb.hackathon.pojo.DBCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTVerificationController {

    @Value("${credential.username}")
    private String username;
    @Value("${credential.pwd}")
    private String pwd;
    @GetMapping(path = "/verification")
    public DBCredential verifyJWT() {
        return new DBCredential(username, pwd);
    }
}
