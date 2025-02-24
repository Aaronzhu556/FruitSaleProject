package org.songxueyu.cdgy.fruitsaleproject.Response;

public enum StatusCode {
    SUCCESS(200,"请求成功"),
    FAILURE(201,"");
    private int code;
    private String msg;
    StatusCode(int code ,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
