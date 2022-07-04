package com.soybean.framework.security.client.entity;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenxina
 * @since 2019-04-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDetails implements UserDetails, CredentialsContainer, java.io.Serializable {

    private static final long serialVersionUID = 666236878598344789L;

    private Long userId;
    private String email;
    private Integer sex;
    private String mobile;
    private Long tenantId;
    private String tenantCode;
    private String companyName;
    private String nickName;
    private String realName;
    private String username;
    private String password;
    private String avatar;
    private Boolean enabled;
    private String description;
    private Collection<String> permissions = new ArrayList<>();
    private Collection<String> roles = new ArrayList<>();
    private Collection<GrantedAuthority> authorities;
    /**
     * 上下文字段，不进行持久化
     * <p>
     * 1. 用于基于 LoginUser 维度的临时缓存
     */
    @JsonIgnore
    private Map<String, Object> context;


    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // ========== 上下文 ==========

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setContext(String key, Object value) {
        if (context == null) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }

    public <T> T getContext(String key, Class<T> type) {
        return MapUtil.get(context, key, type);
    }
}
