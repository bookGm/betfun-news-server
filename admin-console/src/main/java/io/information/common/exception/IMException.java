package io.information.common.exception;

import lombok.Getter;

//自定义业务异常类
@Getter
public class IMException extends RuntimeException {
    private int status;

    public IMException(ExceptionEnum em) {
        super(em.getMessage());
        this.status = em.getStatus();
    }

    public IMException(String message, int status) {
        super(message);
        this.status = status;
    }

    public IMException(ExceptionEnum em, Throwable cause) {
        super(em.getMessage(), cause);
        this.status = em.getStatus();
    }

    public IMException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }
}