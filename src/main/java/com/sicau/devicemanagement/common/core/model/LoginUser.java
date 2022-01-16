package com.sicau.devicemanagement.common.core.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sicau.devicemanagement.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */
@Data
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;

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
        System.out.println("==============password");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("==============username");
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
        return null;
    }
}
