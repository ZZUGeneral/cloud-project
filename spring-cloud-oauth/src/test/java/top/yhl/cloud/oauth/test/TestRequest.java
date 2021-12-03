package top.yhl.cloud.oauth.test;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import top.yhl.cloud.oauth.third.entity.*;
import top.yhl.cloud.oauth.third.request.AuthQqRequest;
import top.yhl.cloud.oauth.third.request.AuthRequest;
import top.yhl.cloud.oauth.utils.AuthStateUtils;

public class TestRequest {
    @Test
    public void authorize() {
        AuthRequest request = new AuthQqRequest(AuthConfig.builder()
                .clientId("clientId")
                .clientSecret("clientSecret")
                .redirectUri("http://redirectUri")
                .build());
        String authorize = request.authorize(AuthStateUtils.createState());
        System.out.println(authorize);
    }

    @Test
    public void login() {
        AuthRequest request = new AuthQqRequest(AuthConfig.builder()
                .clientId("clientId")
                .clientSecret("clientSecret")
                .redirectUri("http://redirectUri")
                .build());

        String state = AuthStateUtils.createState();
        request.authorize(state);
        AuthCallback callback = AuthCallback.builder()
                .code("code")
                .state(state)
                .build();
        AuthResponse response = request.login(callback);

        AuthUser user = (AuthUser) response.getData();

        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void revoke() {
        AuthRequest request = new AuthQqRequest(AuthConfig.builder()
                .clientId("clientId")
                .clientSecret("clientSecret")
                .redirectUri("http://redirectUri")
                .build());

        AuthResponse response = request.revoke(AuthToken.builder().build());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void refresh() {
        AuthRequest request = new AuthQqRequest(AuthConfig.builder()
                .clientId("clientId")
                .clientSecret("clientSecret")
                .redirectUri("http://redirectUri")
                .build());

        AuthResponse response = request.refresh(AuthToken.builder().build());
        System.out.println(JSON.toJSONString(response.getData()));

    }
}
