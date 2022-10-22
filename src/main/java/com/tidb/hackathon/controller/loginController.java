package com.tidb.hackathon.controller;


import cn.hutool.core.date.DateUnit;
import com.tidb.hackathon.entity.LoginUserVO;
import com.tidb.hackathon.exception.BizException;
import com.tidb.hackathon.pojo.Result;
import com.tidb.hackathon.pojo.ResultInfo;
import com.tidb.hackathon.util.Impl.RedisServiceImpl;
import com.tidb.hackathon.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class loginController {


    @Autowired
    private RedisServiceImpl redisService;


    /**
     * 登录获取jwt
     */
    @RequestMapping(value = "/api/getJWT", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public String getJWT(@RequestBody LoginUserVO loginUserVO) throws Exception{
        //1.判断用户名密码是否正确
        String password = redisService.get(loginUserVO.getUsername());
        if (password == null) {
            throw new BizException(ResultInfo.ACCUNT_PASSWORD_ERROR.getMsg());
        }
        if (!loginUserVO.getPassword().equals(password)) {
            throw new BizException(ResultInfo.ACCUNT_PASSWORD_ERROR.getMsg());
        }
        //创建签名
        String token  = JWTUtil.creatTokenByRS256(loginUserVO.getUsername());
        //3.存入token至redis
        String redisKey = loginUserVO.getUsername() + "_username";
        redisService.set(redisKey, token);
        //设置过期时间
        redisService.expire(redisKey,5, TimeUnit.MINUTES);

        return token;

    }

    /**
     * 注册账号
     */
    @RequestMapping(value = "/api/register", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public Result register(@RequestBody LoginUserVO loginUserVO){
        //1.判断用户是否存在
        String password = redisService.get(loginUserVO.getUsername());
        if (password != null) {
            throw new BizException(ResultInfo.ACCUNT_PASSWORD_HAVE.getMsg());
        }
        //2.账户存入redis
        redisService.set(loginUserVO.getUsername(),loginUserVO.getPassword());
        return Result.success();
    }



    /**
     * 对签名进行续期
     */
    @RequestMapping(value = "/api/refreshSign", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public Result refreshSign(@RequestBody String username){
        String redisKey = username + "_username";
        String sign = redisService.get(redisKey);
        if(sign == null ){
            throw new BizException(ResultInfo.ACCUNT_IS_FAULT.getMsg());
        }
        //设置过期时间
        redisService.expire(redisKey,5, TimeUnit.MINUTES);
        return Result.success();
    }


}
