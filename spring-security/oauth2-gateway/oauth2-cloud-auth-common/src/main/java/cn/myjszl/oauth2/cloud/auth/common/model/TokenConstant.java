package cn.myjszl.oauth2.cloud.auth.common.model;

public class TokenConstant {
    /**
     * JWT的秘钥
     * TODO 实际项目中需要统一配置到配置文件中，资源服务也需要用到
     */
    public final static String SIGN_KEY="myjszl";

    public final static String TOKEN_NAME="jwt-token";

    public final static String PRINCIPAL_NAME="principal";

    public static final String AUTHORITIES_NAME="authorities";

    public static final String USER_ID="user_id";

    public static final String JTI="jti";

    public static final String EXPR="expr";
}
