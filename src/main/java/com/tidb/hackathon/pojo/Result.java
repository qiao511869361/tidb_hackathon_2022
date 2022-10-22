package com.tidb.hackathon.pojo;

import cn.hutool.json.JSONUtil;

/**
 * Created on 2020/11/30.
 *
 * @author allen4tech
 */
public class Result {
    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public Result() {
    }

    public Result(ResultInfo errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 成功
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result rb = new Result();
        rb.setCode(ResultInfo.SUCCESS.getCode());
        rb.setMessage(ResultInfo.SUCCESS.getMsg());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static Result error(ResultInfo errorInfo) {
        Result rb = new Result();
        rb.setCode(errorInfo.getCode());
        rb.setMessage(errorInfo.getMsg());
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static Result error(String code, String message) {
        Result rb = new Result();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static Result error( String message) {
        Result rb = new Result();
        rb.setCode("-1");
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
