package com.tidb.hackathon.supports;

import com.tidb.hackathon.exception.BizException;
import com.tidb.hackathon.pojo.Result;
import com.tidb.hackathon.pojo.ResultInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result bizExceptionHandler(HttpServletRequest req, BizException e){
        return Result.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
        e.printStackTrace();
        System.out.println("~~~~~~~~~~req = " + req.toString() + ", e = " + e);
        return Result.error(ResultInfo.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e){
        e.printStackTrace();
        System.out.println("~~~~~~~~~~req = " + req.toString() + ", e = " + e);
        return Result.error(ResultInfo.INTERNAL_SERVER_ERROR);
    }
}
