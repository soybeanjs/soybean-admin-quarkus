package com.soybean.framework.storage.endpoint;

import com.google.common.collect.Maps;
import com.soybean.framework.storage.AliYunStorageOperation;
import com.soybean.framework.storage.properties.AliYunStorageProperties;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wenxina
 */
@Endpoint(id = "/storage/oss")
public class OssEndpoint {

    @Resource
    private AliYunStorageProperties properties;


    @ReadOperation
    public Object invoke() {
        Map<String, Object> info = Maps.newLinkedHashMap();
        info.put("upload.success", AliYunStorageOperation.FILE_UPLOAD_SUCCESS.get());
        info.put("upload.fail", AliYunStorageOperation.FILE_UPLOAD_FAIL.get());
        info.put("get.count", AliYunStorageOperation.FILE_GET_COUNTS.get());
        info.put("delete.count", AliYunStorageOperation.FILE_DELETE_COUNTS.get());
        return info;
    }
}