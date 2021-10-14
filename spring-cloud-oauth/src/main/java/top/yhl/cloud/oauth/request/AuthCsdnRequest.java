package top.yhl.cloud.oauth.request;

import com.alibaba.fastjson.JSONObject;
import top.yhl.cloud.oauth.cache.AuthStateCache;
import top.yhl.cloud.oauth.entity.AuthConfig;
import top.yhl.cloud.oauth.entity.AuthDefaultSource;
import top.yhl.cloud.oauth.enums.AuthUserGender;
import top.yhl.cloud.oauth.exception.AuthException;
import top.yhl.cloud.oauth.entity.AuthCallback;
import top.yhl.cloud.oauth.entity.AuthToken;
import top.yhl.cloud.oauth.entity.AuthUser;

/**
 * CSDN登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.0.0
 */
@Deprecated
public class AuthCsdnRequest extends AuthDefaultRequest {

    public AuthCsdnRequest(AuthConfig config) {
        super(config, AuthDefaultSource.CSDN);
    }

    public AuthCsdnRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.CSDN, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);
        return AuthToken.builder().accessToken(accessTokenObject.getString("access_token")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);
        return AuthUser.builder()
            .uuid(object.getString("username"))
            .username(object.getString("username"))
            .remark(object.getString("description"))
            .blog(object.getString("website"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("error_code")) {
            throw new AuthException(object.getString("error"));
        }
    }
}
