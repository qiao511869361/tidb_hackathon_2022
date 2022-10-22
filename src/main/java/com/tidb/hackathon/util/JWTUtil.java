//package com.tidb.hackathon.util;
//
//import cn.hutool.json.JSON;
//import cn.hutool.json.JSONObject;
//import com.sun.org.slf4j.internal.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class JWTUtil {
//    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
//
//    //私钥
//    private static final String TOKEN_SECRET = "123456";
//
//    /**
//     * 生成token，自定义过期时间 毫秒
//     *
//     * @param userTokenDTO
//     * @return
//     */
//    public static String generateToken(UserTokenDTO userTokenDTO) {
//        try {
//            // 私钥和加密算法
//            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//            // 设置头部信息
//            Map<String, Object> header = new HashMap<>(2);
//            header.put("Type", "Jwt");
//            header.put("alg", "HS256");
//
//            return JWT.create()
//                    .withHeader(header)
//                    .withClaim("token", JSONObject.toJSONString(userTokenDTO))
//                    //.withExpiresAt(date)
//                    .sign(algorithm);
//        } catch (Exception e) {
//            logger.error("generate token occur error, error is:{}", e);
//            return null;
//        }
//    }
//
//    /**
//     * 检验token是否正确
//     *
//     * @param token
//     * @return
//     */
//    public static UserTokenDTO parseToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT jwt = verifier.verify(token);
//        String tokenInfo = jwt.getClaim("token").asString();
//        return JSON.parseObject(tokenInfo, UserTokenDTO.class);
//    }
//}
