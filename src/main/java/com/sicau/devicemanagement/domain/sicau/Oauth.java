package com.sicau.devicemanagement.domain.sicau;

import lombok.Data;

@Data
public class Oauth {

    /* 返回格式 */
//    "access_token": "xxxxxxxxxxxxxxxxxxxxxxxx",
//            "token_type": "bearer",
//            "refresh_token": "xxxxxxxxxxxxxxxxxxxxxxxxxx",
//            "expires_in": xxxx,
//            "scope": "read",
//            "tgc": null,
//            "sub": "xxxx",
//            "session": null,
//            "success": true,
//            "expire": "xxxx-xx-xx xx:xx:xx",
//            "isSuperAdmin": "xxxx",
//            "depart": "xxxx",
//            "userName": "xxxx",
//            "userId": "xxxx",
//            "tenant": "xxxx",
//            "status": 200,
//            "jti": "xxxx"

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private String expiresIn;

    private String scope;

    private String tgc;

    private String sub;

    private String session;

    private boolean success;

    private String expire;

    private String isSuperAdmin;

    private String depart;

    private String userName;

    private String UserId;

    private String tenant;

    private int status;
}
