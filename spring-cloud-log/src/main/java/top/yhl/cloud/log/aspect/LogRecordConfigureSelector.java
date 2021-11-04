package top.yhl.cloud.log.aspect;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import top.yhl.cloud.log.config.LogRecordProxyAutoConfiguration;

public class LogRecordConfigureSelector implements ImportSelector {
    
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                LogRecordProxyAutoConfiguration.class.getName()
        };
    }
}
