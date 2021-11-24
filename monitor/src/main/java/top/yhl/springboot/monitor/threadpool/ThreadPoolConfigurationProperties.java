package top.yhl.springboot.monitor.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yang_hl3
 */
@ConfigurationProperties(prefix = "monitor.threadpool")
@Data
public class ThreadPoolConfigurationProperties {
    private List<ThreadPoolProperties> executor = new ArrayList<>();
}
