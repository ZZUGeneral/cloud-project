package top.yhl.cloud.oauth.third.request;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.http.HttpUtil;
import top.yhl.cloud.common.util.StringUtils;
import top.yhl.cloud.oauth.third.cache.AuthStateCache;
import top.yhl.cloud.oauth.third.entity.AuthConfig;
import top.yhl.cloud.oauth.third.entity.AuthDefaultSource;
import top.yhl.cloud.oauth.third.enums.AuthResponseStatus;
import top.yhl.cloud.oauth.third.enums.AuthUserGender;
import top.yhl.cloud.oauth.third.exception.AuthException;
import top.yhl.cloud.oauth.third.entity.AuthCallback;
import top.yhl.cloud.oauth.third.entity.AuthResponse;
import top.yhl.cloud.oauth.third.entity.AuthToken;
import top.yhl.cloud.oauth.third.entity.AuthUser;
import top.yhl.cloud.oauth.utils.GlobalAuthUtils;
import top.yhl.cloud.oauth.utils.UrlBuilder;


import java.util.Map;

/**
 * qq登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @author yangkai.shen (https://xkcoding.com)
 * @since 1.1.0
 */
public class AuthQqRequest extends AuthDefaultRequest {
    public AuthQqRequest(AuthConfig config) {
        super(config, AuthDefaultSource.QQ);
    }

    public AuthQqRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.QQ, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doGetAuthorizationCode(authCallback.getCode());
        return getAuthToken(response);
    }

    @Override
    public AuthResponse refresh(AuthToken authToken) {
        String response = cn.hutool.http.HttpUtil.get(refreshTokenUrl(authToken.getRefreshToken()));
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(getAuthToken(response)).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String openId = this.getOpenId(authToken);
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);
        if (object.getIntValue("ret") != 0) {
            throw new AuthException(object.getString("msg"));
        }
        String avatar = object.getString("figureurl_qq_2");
        if (StringUtils.isEmpty(avatar)) {
            avatar = object.getString("figureurl_qq_1");
        }

        String location = String.format("%s-%s", object.getString("province"), object.getString("city"));
        return AuthUser.builder()
            .username(object.getString("nickname"))
            .nickname(object.getString("nickname"))
            .avatar(avatar)
            .location(location)
            .uuid(openId)
            .gender(AuthUserGender.getRealGender(object.getString("gender")))
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 获取QQ用户的OpenId，支持自定义是否启用查询unionid的功能，如果启用查询unionid的功能，
     * 那就需要开发者先通过邮件申请unionid功能，参考链接 {@see http://wiki.connect.qq.com/unionid%E4%BB%8B%E7%BB%8D}
     *
     * @param authToken 通过{@link AuthQqRequest#getAccessToken(AuthCallback)}获取到的{@code authToken}
     * @return openId
     */
    private String getOpenId(AuthToken authToken) {
        String response = HttpUtil.get(UrlBuilder.fromBaseUrl("https://graph.qq.com/oauth2.0/me")
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("unionid", config.isUnionId() ? 1 : 0)
            .build());
        String removePrefix = response.replace("callback(", "");
        String removeSuffix = removePrefix.replace(");", "");
        String openId = removeSuffix.trim();
        JSONObject object = JSONObject.parseObject(openId);
        if (object.containsKey("error")) {
            throw new AuthException(object.get("error") + ":" + object.get("error_description"));
        }
        authToken.setOpenId(object.getString("openid"));
        if (object.containsKey("unionid")) {
            authToken.setUnionId(object.getString("unionid"));
        }
        return StringUtils.isEmpty(authToken.getUnionId()) ? authToken.getOpenId() : authToken.getUnionId();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken 用户授权token
     * @return 返回获取userInfo的url
     */
    @Override
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("oauth_consumer_key", config.getClientId())
            .queryParam("openid", authToken.getOpenId())
            .build();
    }

    private AuthToken getAuthToken(String response) {
        Map<String, String> accessTokenObject = GlobalAuthUtils.parseStringToMap(response);
        if (!accessTokenObject.containsKey("access_token") || accessTokenObject.containsKey("code")) {
            throw new AuthException(accessTokenObject.get("msg"));
        }
        return AuthToken.builder()
            .accessToken(accessTokenObject.get("access_token"))
            .expireIn(Integer.valueOf(accessTokenObject.get("expires_in")))
            .refreshToken(accessTokenObject.get("refresh_token"))
            .build();
    }
}
