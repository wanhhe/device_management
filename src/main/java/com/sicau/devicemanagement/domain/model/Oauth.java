package com.sicau.devicemanagement.domain.model;

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

    private String refreshToken;

    private int status;

    private String expire;

    private boolean success;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
