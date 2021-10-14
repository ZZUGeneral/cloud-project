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
import top.yhl.cloud.oauth.utils.GlobalAuthUtils;

import java.util.Map;

/**
 * Github登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.0.0
 */
public class AuthGithubRequest extends AuthDefaultRequest {

    public AuthGithubRequest(AuthConfig config) {
        super(config, AuthDefaultSource.GITHUB);
    }

    public AuthGithubRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.GITHUB, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        Map<String, String> res = GlobalAuthUtils.parseStringToMap(response);

        this.checkResponse(res.containsKey("error"), res.get("error_description"));

        return AuthToken.builder()
            .accessToken(res.get("access_token"))
            .scope(res.get("scope"))
            .tokenType(res.get("token_type"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object.containsKey("error"), object.getString("error_description"));

        return AuthUser.builder()
            .uuid(object.getString("id"))
            .username(object.getString("login"))
            .avatar(object.getString("avatar_url"))
            .blog(object.getString("blog"))
            .nickname(object.getString("name"))
            .company(object.getString("company"))
            .location(object.getString("location"))
            .email(object.getString("email"))
            .remark(object.getString("bio"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    private void checkResponse(boolean error, String error_description) {
        if (error) {
            throw new AuthException(error_description);
        }
    }

}
