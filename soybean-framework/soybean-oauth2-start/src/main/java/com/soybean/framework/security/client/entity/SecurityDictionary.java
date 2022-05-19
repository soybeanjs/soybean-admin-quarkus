package com.soybean.framework.security.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wenxina
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SecurityDictionary<T> implements java.io.Serializable {

    private static final long serialVersionUID = 7662862173120506696L;

    private T id;
    private String label;


}
