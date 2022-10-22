package com.tidb.hackathon.util;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.tidb.hackathon.entity.RSA256Key;
import com.tidb.hackathon.entity.UserTokenDTO;
import com.tidb.hackathon.exception.BizException;
import com.tidb.hackathon.pojo.ResultInfo;
import org.bouncycastle.jcajce.provider.util.SecretKeyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    //私钥
    private static final String TOKEN_SECRET = "123456";


    //数字签名
    public static final String KEY_ALGORITHM = "RSA";

    //RSA密钥长度
    public static final int KEY_SIZE = 1024;

    //唯一的密钥实例
    private static volatile RSA256Key rsa256Key;


    private static volatile String ISSUER = "admin";

//    @Value("${tidb.publicStr}")
    private static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCaGn5+Yq9o7kxmDS2zwIREIEkcpKbx44uPT/PX22XOjgzkMvbCEamu7KyatNhkrrSH6E14l1F739WztXffJ/OCXUmgF4egMedyGCA8ymZWbzyhwnZcywa8me8S2rjb3gkCD6VFq8BSPJx9vaMvlR7eZxe1O5lllaV6tE6SlNY7QIDAQAB";
//    @Value("${tidb.privateStr}")
    private static String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJoafn5ir2juTGYNLbPAhEQgSRykpvHji49P89fbZc6ODOQy9sIRqa7srJq02GSutIfoTXiXUXvf1bO1d98n84JdSaAXh6Ax53IYIDzKZlZvPKHCdlzLBryZ7xLauNveCQIPpUWrwFI8nH29oy+VHt5nF7U7mWWVpXq0TpKU1jtAgMBAAECgYBOzKsk+s4EM5dnSXKo+ENmblOq43SFnzrh4+7X7vD4zZxCRH96NfEDNS4Qs45RSmmLKMOwHL2B0etfWBJSPisNZomhOBgTSfvHDSIFNbvVFqQBH5zEtxIB3bPrkW5X6EDF2BYCatC70Al21kmzHr2dBaL/c2p/4ueTdna/zKSKwQJBAMZLvFeyLi9jiIXXUzv7AkxF4SICtOOL56mKMkVlAXOWQgQe9FVsO8lTTK6GqqLrYe5cqgTiQqcLCP8/5n33oF0CQQCoW0ospTg8JEqStTmwemF8HfgxLU6YGj5kegD2Fq6HfCTq/z59THohbq6l+yoSxO3mjZb+7Zv1zu6oDR7YXFHRAkB66dEaDtFAAJNMWxc107Yt7xbIzSKw9TSoy4ezqhNHQXk0MrfDB27bsS2T9NdqWzr91CRzGIi2IEn4ZfSKWmblAkB2S1bOEfV2hMWFWiND9mnDDUfUPhKIW4BVl0hPodZWSouiN2DQJ8l07lF3PQjuEUNcCUb8rzYzvIgCut1eh1fRAkEAqBOEqBuRq9W7vrBlHbqEk1EXu2MfeRLZWljZn6uoyedJCdSh+6neUUvqHDGi3DNdss/ByWHI9e+9zOXTURmMaQ==";


    /**
     * 生成 公钥/私钥
     *
     *  由双重校验锁保证创建唯一的密钥实例，因此创建完成后仅有唯一实例。
     *  当被JVM回收后，才会创建新的实例
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static RSA256Key generateRSA256Key() throws Exception {

        //第一次校验：单例模式只需要创建一次实例，若存在实例，不需要继续竞争锁，
        if (rsa256Key == null) {
            //RSA256Key单例的双重校验锁
            synchronized(RSA256Key.class) {
                //第二次校验：防止锁竞争中自旋的线程，拿到系统资源时，重复创建实例
                if (rsa256Key == null) {
                    //密钥生成所需的随机数源
                    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
                    keyPairGen.initialize(KEY_SIZE);
                    //通过KeyPairGenerator生成密匙对KeyPair
//                    KeyPair keyPair = keyPairGen.generateKeyPair();
//                    //获取公钥和私钥
//                    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//                    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    rsa256Key = new RSA256Key();
                    RSAPrivateKey privateKey = (RSAPrivateKey)getPrivateKey(privateKeyStr);
                    RSAPublicKey publicKey = (RSAPublicKey)getPublicKey(publicKeyStr);
                    rsa256Key.setPublicKey(publicKey);
                    rsa256Key.setPrivateKey(privateKey);
                }

            }
        }
        return rsa256Key;
    }



    /**
     * 签发Token
     *
     * withIssuer()给PAYLOAD添加一跳数据 => token发布者
     * withClaim()给PAYLOAD添加一跳数据 => 自定义声明 （key，value）
     * withIssuedAt() 给PAYLOAD添加一条数据 => 生成时间
     * withExpiresAt()给PAYLOAD添加一条数据 => 保质期
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String creatTokenByRS256(Object data) throws Exception {
        //初始化 公钥/私钥
        RSA256Key rsa256Key = generateRSA256Key();

        if(rsa256Key.getPrivateKey() == null) {
            throw new BizException(ResultInfo.RSA_IS_NULL.getMsg());
        }
        //加密时，使用私钥生成RS算法对象
        Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPrivateKey());

        Date date = new Date();
        return JWT.create()
                //签发人
                .withIssuer(ISSUER)
                //接收者
                .withAudience(data.toString())
                //签发时间
                .withIssuedAt(date)
                //过期时间
                .withExpiresAt(DateUtil.offsetDay(date, 2))
                //相关信息
                .withClaim("data", JSONUtil.toJsonStr(data))
                //签入
                .sign(algorithm);
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }


    /**
     * 生成token，自定义过期时间 毫秒
     *
     * @param userTokenDTO
     * @return
     */
    public static String generateToken(UserTokenDTO userTokenDTO) {
        try {
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "RS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("token", JSONUtil.toJsonStr(userTokenDTO))
                    //.withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            logger.error("generate token occur error, error is:{}", e);
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param token
     * @return
     */
    public static UserTokenDTO parseToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String tokenInfo = jwt.getClaim("token").asString();
        return JSONUtil.toBean(tokenInfo,UserTokenDTO.class);
    }
}
