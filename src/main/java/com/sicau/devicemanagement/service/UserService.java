package com.sicau.devicemanagement.service;

import com.sicau.devicemanagement.domain.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    /**
     *
     * @param username
     * @return
     */
    User selectUserByUserName(String username);
}
