package com.tidb.hackathon.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tidb.hackathon.exception.BizException;
import com.tidb.hackathon.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String[] strs = token.trim().split("\\.");
        if(strs.length != 3)
            throw new BizException("400", "Invalid JWT Format");

        try {
            return JWTUtil.verifyToken(strs);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            logger.error("Invalid Signature: {}", token);
            throw new BizException("400", "Invalid Token Signature");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            logger.error("Token Expired: {}", token);
            throw new BizException("400", "Token Expired");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            logger.error("Algorithm Mismatch: {}", token);
            throw new BizException("400", "Algorithm Mismatch");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("JWT verification error: {}", token);
            throw new BizException("500", "JWT Verification Error");
        }
    }
}
