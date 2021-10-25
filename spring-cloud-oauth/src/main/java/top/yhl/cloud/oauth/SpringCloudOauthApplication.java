package top.yhl.cloud.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.yhl.cloud.oauth.entity.AuthCallback;
import top.yhl.cloud.oauth.entity.AuthConfig;
import top.yhl.cloud.oauth.request.AuthQqRequest;
import top.yhl.cloud.oauth.request.AuthRequest;

@SpringBootApplication
public class SpringCloudOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOauthApplication.class, args);
    }

    public void test() {
        //  创建授权request
        AuthRequest authRequest = new AuthQqRequest(AuthConfig.builder()
                .clientId("")
                .clientSecret("")
                .redirectUri("")
                .build());
        // 生成授权页面
        authRequest.authorize("state");
        AuthCallback callback = AuthCallback.builder()
                .code("")
                .state("")
                .build();
        // 授权登录后会返回code（auth_code（仅限支付宝））、state，1.8.0版本后，可以用AuthCallback类作为回调接口的参数
        // 注：JustAuth默认保存state的时效为3分钟，3分钟内未使用则会自动清除过期的state
        authRequest.login(callback);
    }

}
