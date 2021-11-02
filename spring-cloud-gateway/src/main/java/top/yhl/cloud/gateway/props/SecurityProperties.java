package top.yhl.cloud.gateway.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@AllArgsConstructor
@RefreshScope
@ConfigurationProperties(prefix = "yhl.security")
public class SecurityProperties {

    private Boolean enableSecurity = true;

    private IgnoreAllProperties ignoreAl = new IgnoreAllProperties();
}
