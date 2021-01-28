package com.mqtt.mqttbasis.utils;

import java.io.Serializable;

/**
 * @author bjy
 */
public class AjaxResult implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 4404203832037984839L;

    private Integer  code;

    private boolean success;

    private String msg;

    private Object data;


    public AjaxResult(){

    }

    public AjaxResult(Integer code, boolean success, String msg, Object data){
        this.code=code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public AjaxResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public static AjaxResult success(Object data){
        return new AjaxResult(200,true, null, data);
    }

    public static AjaxResult success(Object data, String msg){
        return new AjaxResult(200,true, msg, data);
    }

    public static AjaxResult success(String msg){
        return new AjaxResult(200,true, msg, null);
    }

    public static AjaxResult fail(String msg){

        return new AjaxResult(10001,false, msg, null);
    }

    public static AjaxResult failLocation(Object data, String msg){
        return new AjaxResult(200,false, msg, data);
    }


}
