package com.sicau.devicemanagement.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.http.HttpUtils;
import com.sicau.devicemanagement.domain.model.*;
import com.sicau.devicemanagement.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class SicauService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private RestTemplate restTemplate;

    @Value("${sicau.token}")
    private String token;

    @Value("${sicau.oauth.username}")
    private int username;

    @Value("${sicau.oauth.password}")
    private String password;

    @Value("${sicau.oauth.auth}")
    private String auth;

    @Value("${sicau.oauth.grant_type}")
    private String grantType;

    @Value("${sicau.oauth.is_encrypt}")
    private String isEncrypt;

    @Value("${sicau.oauth.url}")
    private String oauthUrl;

    @Value("${sicau.url.class_rent}")
    private String classRentUrl;

    @Value("${sicau.url.undergraduate}")
    private String undergraduateUrl;

    @Value("${sicau.url.graduate}")
    private String graduateUrl;

    private final int obsoleteStatus = 40101;


    private void login() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization", auth);

        HttpEntity<OauthLogin> login = new HttpEntity<>(new OauthLogin(), header);
        ResponseEntity<Oauth> responseEntity = restTemplate.postForEntity(oauthUrl, login, Oauth.class);
        Oauth body = responseEntity.getBody();
        if (body == null) {
            System.out.println("boyd is null");
            return;
        }
        if (StringUtils.isNotEmpty(body.getAccessToken())) {
            this.token = body.getAccessToken();
        }
    }

    public void updateRent() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization", token);

        HttpEntity<PostEntity> post = new HttpEntity<>(new PostEntity(), header);
        ResponseEntity<SicauBody> responseEntity = restTemplate.postForEntity(classRentUrl, post, SicauBody.class);
        SicauBody body = responseEntity.getBody();
        BasicResponse basicResponse = body.getBasicResponse();
        if (basicResponse == null) {
            System.out.println("basicResponse is null");
            return;
        }
        int status = basicResponse.getStatus();
        if (status == obsoleteStatus) {
            login();
            updateRent();
        }
    }
}
