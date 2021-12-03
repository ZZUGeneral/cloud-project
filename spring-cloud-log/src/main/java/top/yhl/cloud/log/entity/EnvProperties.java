package top.yhl.cloud.log.entity;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ConfigurationProperties("spring")
public class EnvProperties implements EnvironmentAware, EnvironmentCapable {

    @Nullable
    private Environment environment;

    @Getter
    private final Map<String, String> prop = new HashMap<>();

    @Nullable
    public String get(String key) {
        return get(key, null);
    }

    @Nullable
    public String get(String key, @Nullable String defaultValue) {
        String value = prop.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Nullable
    public Integer getInt(String key) {
        return getInt(key, null);
    }

    @Nullable
    public Integer getInt(String key, @Nullable Integer defaultValue) {
        String value = prop.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.valueOf(value.trim());
    }

    @Nullable
    public Long getLong(String key) {
        return getLong(key, null);
    }

    @Nullable
    public Long getLong(String key, @Nullable Long defaultValue) {
        String value = prop.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Long.valueOf(value.trim());
    }

    @Nullable
    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Nullable
    public Boolean getBoolean(String key, @Nullable Boolean defaultValue) {
        String value = prop.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.valueOf(value.trim());
    }

    @Nullable
    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    @Nullable
    public Double getDouble(String key, @Nullable Double defaultValue) {
        String value = prop.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Double.valueOf(value.trim());
    }

    public boolean contains(String key) {
        return prop.containsKey(key);
    }

    public String getEnv() {
        Objects.requireNonNull(environment, "Spring Boot 环境下 Environment 不可能为 NULL");
        String env = environment.getProperty("spring.profile.active");
        return env;
    }

    public String getName() {
        Objects.requireNonNull(environment, "Spring Boot 环境下 Environment 不可能为 NULL");
        return environment.getProperty("spring.application.name");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        Objects.requireNonNull(environment, "Spring Boot 环境下 Environment 不可能为 NULL");
        return this.environment;
    }
}
