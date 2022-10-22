package com.tidb.hackathon.entity;

import lombok.Data;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Data
public class RSA256Key {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSA256Key() {
    }

    public RSA256Key(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
}
