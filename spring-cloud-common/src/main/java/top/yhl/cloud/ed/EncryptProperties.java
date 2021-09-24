package top.yhl.cloud.ed;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.encrypt")
@Component
public class EncryptProperties {

    private final static String DEFAULT_KEY = "www.yanghl.top12";

    private String key = DEFAULT_KEY;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
