package com.sicau.devicemanagement.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Lab;
import com.sicau.devicemanagement.domain.Schedule;
import com.sicau.devicemanagement.domain.model.*;
import com.sicau.devicemanagement.mapper.LabMapper;
import com.sicau.devicemanagement.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SicauService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private LabMapper labMapper;

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

    private HttpHeaders header;

    private final int obsoleteStatus = 401;


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
        updateRent(10, 1);
    }

    public void updateRent(int size, int page) {
        HttpHeaders header = getHeader();
        HttpEntity<PostEntity> post = new HttpEntity<>(new PostEntity(size, page), header);
        ResponseEntity<SicauBody> responseEntity = restTemplate.postForEntity(classRentUrl, post, SicauBody.class);
        SicauBody body = responseEntity.getBody();
        if (body == null) {
            System.out.println("updateRent body is nulll");
            return;
        }
        BasicResponse basicResponse = body.getBasicResponse();
        if (basicResponse == null) {
            System.out.println("basicResponse is null");
            return;
        }
        int status = basicResponse.getStatus();
        if (status == obsoleteStatus) {
            login();
            updateRent();
        } else {
            // 解析resultValue
            List<ClassRent> classRents = JSONArray.parseArray(body.getResultValue(), ClassRent.class);

        }

    }

    public void updateClassInfo() {
        updateClassInfo(10, 1);
    }

    public void updateClassInfo(int size, int page) {
        HttpHeaders header = getHeader();
        HttpEntity<PostEntity> post = new HttpEntity<>(new PostEntity(size, page), header);
        ResponseEntity<SicauBody> responseEntity = restTemplate.postForEntity(classRentUrl, post, SicauBody.class);
        SicauBody body = responseEntity.getBody();
        if (body == null) {
            System.out.println("updateClass body is null");
            return;
        }
        int total = body.getTotal();
        BasicResponse basicResponse = body.getBasicResponse();
        if (basicResponse == null) {
            System.out.println("basicResponse is null");
            return;
        }
        int status = basicResponse.getStatus();
        System.out.println("status is "+status);
        if (status == obsoleteStatus) {
            login();
            updateClassInfo();
        } else {
            // 解析resultValue
            List<ClassInfo> classInfos = JSONArray.parseArray(body.getResultValue(), ClassInfo.class);
            Lab lab = new Lab();
            for (ClassInfo tmp : classInfos) {
                // 如果教室类型不是实验室就筛掉
                if (!"实验室".equals(tmp.getJSLXM())) {
                    continue;
                }
                lab.setId(IdUtils.simpleUUID());
                lab.setCampusId(tmp.getXQH());
                // 教室号 3-226
                lab.setBuildNum(tmp.getJSH());
                labMapper.insertLab(lab);
            }
        }
    }

    public void updateGraduateClass(int size,  int page) {
        HttpHeaders header = getHeader();
        HttpEntity<PostEntity> post = new HttpEntity<>(new PostEntity(size, page), header);
        ResponseEntity<SicauBody> responseEntity = restTemplate.postForEntity(classRentUrl, post, SicauBody.class);
        SicauBody body = responseEntity.getBody();
        if (body == null) {
            System.out.println("updateRent body is nulll");
            return;
        }
        BasicResponse basicResponse = body.getBasicResponse();
        if (basicResponse == null) {
            System.out.println("basicResponse is null");
            return;
        }
        int status = basicResponse.getStatus();
        if (status == obsoleteStatus) {
            login();
            updateClassInfo();
        } else {
            // 解析resultValue
            List<GraduateClass> graduateClasses = JSONArray.parseArray(body.getResultValue(), GraduateClass.class);
            Schedule schedule = new Schedule();
            for (GraduateClass tmp : graduateClasses) {
                // 如果不是2022年的课就跳过
                if (!"2022".equals(tmp.getKKXND())) {
                    continue;
                }
                if (!"第二学期".equals(tmp.getKKXQM())) {
                    continue;
                }
                schedule.setId(IdUtils.simpleUUID());
                schedule.setTerm("");
                // 教学班号 4-C612
                String jxdd = tmp.getJXDD();
                // TODO: 2022/2/18 想办法填入campus
                Lab lab = labMapper.selectLabByNum(null, jxdd);
                if (lab == null) {
                    continue;
                }
                schedule.setLabId(lab.getId());
                scheduleMapper.insertSchedule(schedule);
            }
        }
    }

    public void updateGraduateClass() {
        updateGraduateClass(10, 1);
    }

    public void updateUnGraduateClass(int size, int page) {
        HttpHeaders header = getHeader();
        HttpEntity<PostEntity> post = new HttpEntity<>(new PostEntity(size, page), header);
        ResponseEntity<SicauBody> responseEntity = restTemplate.postForEntity(classRentUrl, post, SicauBody.class);
        SicauBody body = responseEntity.getBody();
        if (body == null) {
            System.out.println("updateRent body is nulll");
            return;
        }
        int total = body.getTotal();
        BasicResponse basicResponse = body.getBasicResponse();
        if (basicResponse == null) {
            System.out.println("basicResponse is null");
            return;
        }
        int status = basicResponse.getStatus();
        if (status == obsoleteStatus) {
            login();
            updateClassInfo();
        } else {
            // 解析resultValue
            List<UnGraduateClass> unGraduateClasses = JSONArray.parseArray(body.getResultValue(), UnGraduateClass.class);
            Schedule schedule = new Schedule();
            for (UnGraduateClass tmp : unGraduateClasses) {
                if (!"雅安".equals(tmp.getXQMC())) {
                    continue;
                }
                // 开学学年度 2021-2022-2
//                String date = tmp.getKKXND();
                if (!"2021-2022-2".equals(tmp.getKKXND())) {
                    continue;
                }
                schedule.setId(IdUtils.simpleUUID());
                String[] zc = tmp.getZC().split("-");
                int begin = Integer.parseInt(zc[0]);
                int end = Integer.parseInt(zc[1]);
                for (int i = begin; i <= end; i++) {
                    // TODO: 2022/2/18 补全是哪一天
                    schedule.setWeekDay(i + "this is day");
                    // TODO: 2022/2/18 补全数据
                    schedule.setTerm("");
                    schedule.setLabId(null);
                    scheduleMapper.insertSchedule(schedule);
                }
            }
        }
    }

    public void updateUnGraduateClass() {
        updateUnGraduateClass(10, 1);
    }

    private HttpHeaders getHeader() {
        if (this.header == null) {
            this.header = new HttpHeaders();
            this.header.setContentType(MediaType.APPLICATION_JSON);
            this.header.add("Authorization", this.token);
        }
        return this.header;
    }
}
