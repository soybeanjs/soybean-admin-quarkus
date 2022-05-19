package com.soybean.framework.storage.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wenxina
 */
@Data
public class BaseStorageProperties implements java.io.Serializable {

    private static final long serialVersionUID = 3372604246046939938L;
    private boolean enabled = false;


    @Value("${spring.application.name:'oss'}")
    private String bucket;

    private String mappingPath = "http://ip:port.com/";

    /**
     * 访问key
     **/
    private String accessKey;

    /**
     * 访问秘钥
     **/
    private String secretKey;

    /**
     * 本地文件临时目录
     */
    private String tmpDir;

    public enum StorageType {
        /**
         * 阿里云
         */
        ALIYUN,
        /**
         * 腾讯云
         */
        TENCENT,
        /**
         * 七牛云
         */
        QINIU,
        ;
    }


}
