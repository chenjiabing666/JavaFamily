package cn.myjszl.oauth2.cloud.auth.common.model;

/**
 * 系统常量
 */
public class SysConstant {
    /**
     * 权限<->url对应的KEY
     */
    public final static String OAUTH_URLS="oauth2:oauth_urls";

    /**
     * JWT令牌黑名单的KEY
     */
    public final static String JTI_KEY_PREFIX="oauth2:black:";

    /**
     * 角色前缀
     */
    public final static String ROLE_PREFIX="ROLE_";

    public final static String METHOD_SUFFIX=":";

    public final static String ROLE_ROOT_CODE="ROLE_ROOT";

}
