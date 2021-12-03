package top.yhl.cloud.oauth.third.request;

import com.alibaba.fastjson.JSONObject;
import top.yhl.cloud.oauth.third.cache.AuthStateCache;
import top.yhl.cloud.oauth.third.entity.AuthConfig;
import top.yhl.cloud.oauth.third.entity.AuthDefaultSource;
import top.yhl.cloud.oauth.third.enums.AuthUserGender;
import top.yhl.cloud.oauth.third.exception.AuthException;
import top.yhl.cloud.oauth.third.entity.AuthCallback;
import top.yhl.cloud.oauth.third.entity.AuthToken;
import top.yhl.cloud.oauth.third.entity.AuthUser;

/**
 * Gitee登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.0.0
 */
public class AuthGiteeRequest extends AuthDefaultRequest {

    public AuthGiteeRequest(AuthConfig config) {
        super(config, AuthDefaultSource.GITEE);
    }

    public AuthGiteeRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.GITEE, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);
        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .refreshToken(accessTokenObject.getString("refresh_token"))
            .scope(accessTokenObject.getString("scope"))
            .tokenType(accessTokenObject.getString("token_type"))
            .expireIn(accessTokenObject.getIntValue("expires_in"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfo = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(userInfo);
        this.checkResponse(object);
        return AuthUser.builder()
            .uuid(object.getString("id"))
            .username(object.getString("login"))
            .avatar(object.getString("avatar_url"))
            .blog(object.getString("blog"))
            .nickname(object.getString("name"))
            .company(object.getString("company"))
            .location(object.getString("address"))
            .email(object.getString("email"))
            .remark(object.getString("bio"))
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
        if (object.containsKey("error")) {
            throw new AuthException(object.getString("error_description"));
        }
    }
}
