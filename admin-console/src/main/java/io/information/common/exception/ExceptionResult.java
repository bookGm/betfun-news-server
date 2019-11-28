package io.information.common.exception;

import lombok.Getter;

import java.util.Date;

//自定义异常返回
@Getter
public class ExceptionResult {
    private int status;
    private String messages;
    private String timestamp;

    public ExceptionResult(IMException e) {
        this.status = e.getStatus();
        this.messages = e.getMessage();
        this.timestamp = new Date().toString();
    }
}