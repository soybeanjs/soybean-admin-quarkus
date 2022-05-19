package com.soybean.uaa.domain.entity.tenant;


import com.baomidou.mybatisplus.annotation.TableName;
import com.soybean.framework.commons.entity.SuperEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户配置信息
 * </p>
 *
 * @author wenxina
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_tenant_config")
public class TenantConfig extends SuperEntity<Long> {

    private Long tenantId;
    private Long dynamicDatasourceId;

}
