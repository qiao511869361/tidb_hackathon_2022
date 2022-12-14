package com.tidb.hackathon.pojo;

/**
 * Created on 2020/11/30.
 *
 * @author allen4tech
 */
public enum ResultInfo {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!"),
    ACCUNT_PASSWORD_ERROR("999","账号或密码错误，请重试!"),
    ACCUNT_PASSWORD_HAVE("998","账号已存在!"),
    RSA_IS_NULL("997","RSA为空!"),
    ACCUNT_IS_FAULT("996","账号不存在，或已下线!");

    /** 错误码 */
    private String resultCode;

    /** 错误描述 */
    private String resultMsg;

    ResultInfo(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getCode() {
        return resultCode;
    }

    public String getMsg() {
        return resultMsg;
    }
}
