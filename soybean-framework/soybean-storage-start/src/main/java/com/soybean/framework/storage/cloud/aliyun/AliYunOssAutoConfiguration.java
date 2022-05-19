package com.soybean.framework.storage.cloud.aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.soybean.framework.storage.endpoint.OssEndpoint;
import com.soybean.framework.storage.properties.AliYunStorageProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.soybean.framework.storage.StorageOperation.OSS_CONFIG_PREFIX_ALIYUN;

/**
 * 阿里云 OSS
 *
 * @author wenxina
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(AliYunStorageProperties.class)
@ConditionalOnProperty(prefix = OSS_CONFIG_PREFIX_ALIYUN, name = "enabled", havingValue = "true")
public class AliYunOssAutoConfiguration {


    @Bean
    public OSSClient ossClient(AliYunStorageProperties properties) {
        CredentialsProvider provider = new DefaultCredentialProvider(properties.getAccessKey(), properties.getSecretKey());
        return new OSSClient(properties.getEndpoint(), provider, new ClientConfiguration());
    }

    @Bean
    public OssEndpoint aliYunOssEndpoint() {
        return new OssEndpoint();
    }
}