package com.soybean.uaa.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源 查询DTO
 *
 * @author wenxina
 * @since 2019/06/05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceQueryDTO {

    private Integer type;
    /**
     * 父资源id， 用于查询按钮
     */
    private Long parentId;
    /**
     * 登录人用户id
     */
    private Long userId;

}
