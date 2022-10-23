package com.tidb.hackathon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSA256Key {
    private RSAPublicKey rsaPublicKey;
    private RSAPrivateKey rsaPrivateKey;
}
