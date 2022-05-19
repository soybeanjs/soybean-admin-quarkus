package com.soybean.uaa.domain.converts;

import com.soybean.framework.commons.entity.BaseConverts;
import com.soybean.framework.db.configuration.dynamic.event.body.TenantDynamicDatasource;
import com.soybean.uaa.domain.vo.TenantDynamicDatasourceVO;
import org.springframework.beans.BeanUtils;

/**
 * @author wenxina
 */
public class TenantDynamicDatasourceConverts {

    public static final TenantDynamicDatasourceVO2TenantDynamicDatasourceConverts TENANT_DYNAMIC_DATASOURCE_VO_2_TENANT_DYNAMIC_DATASOURCE_CONVERTS = new TenantDynamicDatasourceVO2TenantDynamicDatasourceConverts();

    public static class TenantDynamicDatasourceVO2TenantDynamicDatasourceConverts implements BaseConverts<TenantDynamicDatasourceVO, TenantDynamicDatasource> {
        @Override
        public TenantDynamicDatasource convert(TenantDynamicDatasourceVO source) {
            if (source == null) {
                return null;
            }
            TenantDynamicDatasource target = new TenantDynamicDatasource();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }


}
