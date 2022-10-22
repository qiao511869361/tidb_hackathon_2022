package com.tidb.hackathon.exception;

import com.tidb.hackathon.pojo.ResultInfo;

/**
 * Created on 2020/11/30.
 *
 * @author allen4tech
 */
public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public BizException() {
        super();
    }

    public BizException(ResultInfo errorInfoInterface) {
        super(errorInfoInterface.getCode());
        this.errorCode = errorInfoInterface.getCode();
        this.errorMsg = errorInfoInterface.getMsg();
    }

    public BizException(ResultInfo errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getCode(), cause);
        this.errorCode = errorInfoInterface.getCode();
        this.errorMsg = errorInfoInterface.getMsg();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BizException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    /**
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
