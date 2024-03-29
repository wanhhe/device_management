package com.sicau.devicemanagement.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sicau.devicemanagement.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails, Serializable {
    private static final long serialVersionUID = 1214213423L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录过期时间
     */
    private Long expireTime;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 登录用户
     */
    private User user;

    /**
     * 用户角色
     */
    private String role;

    public LoginUser(String userId, User user,String role) {
        this.userId = userId;
        this.user  = user;
        this.role = role;
    }


    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return user.getName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(this.role));
        return roles;
    }
}
