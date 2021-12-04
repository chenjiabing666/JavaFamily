package cn.myjszl.common.base.model;

public enum ResultCode {

    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),

    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),

    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),

    TOKEN_INVALID(1004,"token失效！"),

    NO_PERMISSION(1005,"无权限访问！"),

    UNAUTHORIZED(401, "系统错误");



    private final int code;

    private final String msg;

    private ResultCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
