package io.information.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    USER_PASSWORD_ERROR(400,"用户名或密码错误!"),
    NODE_PARENT_PATH(400,"当前节点为父节点"),

    INVALID_FILE_TYPE(400, "无效的文件类型！"),
    INVALID_NOTIFY_PARAM(400, "回调参数有误！"),
    INVALID_NOTIFY_SIGN(400, "回调签名有误！"),
    INVALID_PARAM_ERROR(400, "无效的请求参数！"),
    INVALID_PHONE_NUMBER(400, "无效的手机号码"),
    INVALID_SERVER_ID_SECRET(400, "无效的服务id和密钥！"),
    INVALID_VERIFY_CODE(400, "验证码错误！"),
    INVALID_USERNAME_PASSWORD(400, "无效的用户名和密码！"),

    DATA_TRANSFER_ERROR(500, "数据转换异常！"),
    DELETE_OPERATION_FAIL(500, "删除操作失败！"),
    DIRECTORY_WRITER_ERROR(500, "目录写入失败！"),
    FILE_UPLOAD_ERROR(500, "文件上传失败！"),
    FILE_WRITER_ERROR(500, "文件写入失败！"),
    INSERT_OPERATION_FAIL(500, "新增操作失败！"),
    SEND_MESSAGE_ERROR(500, "短信发送失败！"),
    UPDATE_OPERATION_FAIL(500, "更新操作失败！"),

    UNAUTHORIZED(401, "登录失效或未登录！"),
    ;

    private int status;
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}