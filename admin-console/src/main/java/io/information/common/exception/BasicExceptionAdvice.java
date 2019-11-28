package io.information.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@Slf4j
//@ControllerAdvice
public class BasicExceptionAdvice {

    //自定义异常返回(400)
    @ExceptionHandler(IMException.class)
    public ResponseEntity<ExceptionResult> handleException(IMException e) {
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResult(e));
    }

    //抛出服务器友好错误(500)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}