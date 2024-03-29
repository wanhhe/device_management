package com.sicau.devicemanagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sicau.devicemanagement.common.core.redis.RedisCache;
import com.sicau.devicemanagement.common.utils.StringUtils;
import com.sicau.devicemanagement.common.utils.uuid.IdUtils;
import com.sicau.devicemanagement.domain.Lab;
import com.sicau.devicemanagement.domain.Schedule;
import com.sicau.devicemanagement.domain.sicau.*;
import com.sicau.devicemanagement.mapper.LabMapper;
import com.sicau.devicemanagement.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Component
public class SicauService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private LabMapper labMapper;

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private RedisCache redisCache;

    private String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI3MjA3MCIsInVzZXJfbmFtZSI6IjcyMDcwIiwic2Vzc2lvbiI6IldJcDgwaEJyYWlTTWNOOHdDeGYyeWNxSjQ5d3E3Z0RyX0k2LWJEQ2xWUGZqS0o5czV3TU4hODM0MjMxNjk5IiwiaXNTdXBlckFkbWluIjoiMCIsInVzZXJOYW1lIjoi5byg6I65IiwidXNlcklkIjoiMTE5MzU5IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImNsaWVudF9pZCI6InNjdS1yc3AtcHVibGljLWNsaWVudCIsInRnYyI6bnVsbCwic3VjY2VzcyI6dHJ1ZSwic2NvcGUiOlsicmVhZCJdLCJleHBpcmUiOjE2NDcwODgxNzM0MjYsImV4cCI6MTY0NzA4ODE3MywiZGVwYXJ0IjoiOTYwNkQ4RUUxRTQ0NEE2Qzg2NUUyQUNBNEVERUMwMUYiLCJqdGkiOiJiM2VlZWMwOS1hMmMwLTQ4MzUtOGVlNS02YTM3ZTBjMTdkMTkiLCJ0ZW5hbnQiOiJhYzg4Y2ViMzg2YWE0MjMxYjA5YmY0NzJjYjkzN2MyNCIsInN0YXR1cyI6MjAwfQ.i0EfOGkdAEHaTZVDRAdIe8pWtHCEdciIfHF3AWFUD9HNR-iOrkb5WEKjgb6zl-CkqtYxT7W0PIK0-R975d5z30Yy7bRcNTofLz8Qvx_A5KdLCWG7r68N5nSa_Vcm3PQS3SNl1Is_8gXLwX37T5MMeDcA11jpeAJ69M0SrkRdkl4";

    private static final String USERNAME = "72070";

    private static final String PASSWORD = "jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=";

    private static final String AUTH = "Basic c2N1LXJzcC1wdWJsaWMtY2xpZW50OjEyM3NjdS1yc3AtcHVibGljLWNsaWVudDMyMQ==";

    private static final String GRANT_TYPE = "password";

    private static final String IS_ENCRYPT = "2";

    private static final String OAUTH_URL = "http://bdo.sicau.edu.cn:9090/api/auth/oauth/token";

    private static final String CLASSINFO_URL = "http://bdo.sicau.edu.cn:9090/api/rsp/data/jsjbsj";

    private static final String CLASSRENT_URL = "http://bdo.sicau.edu.cn:9090/api/rsp/data/jssysj";

    private static final String UNDERGRADUATE_URL = "http://bdo.sicau.edu.cn:9090/api/rsp/data/bzkkkrw";

    private static final String GRADUATE_URL = "http://bdo.sicau.edu.cn:9090/api/rsp/data/yjsxskc";

    private HttpHeaders header;

    private static final String BUILDING_KEY = "building/";

    /**
     * 过时的状态码
     */
    private final int obsoleteStatus = 401;


    private void login() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.add("Authorization", AUTH);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", USERNAME);
        map.add("password", PASSWORD);
        map.add("is_encrypt", IS_ENCRYPT);
        map.add("grant_type", GRANT_TYPE);
        HttpEntity<MultiValueMap<String, String>> param = new HttpEntity<>(map, header);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(OAUTH_URL, param, String.class);
        String body = responseEntity.getBody();
        System.out.println("enter login");
        Oauth oauth = JSONObject.parseObject(body, Oauth.class);
        if (oauth == null) {
            System.out.println("boyd is null");
            return;
        }
        if (StringUtils.isNotEmpty(oauth.getAccessToken())) {
            this.token = oauth.getAccessToken();
            System.out.println("this token is "+ this.token);
        }
    }

    public void forceUpdateToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void updateRent() {
        updateRent(10, 1);
    }

    public void updateRent(int size, int page) {
        String json = post(CLASSRENT_URL, size, page, ClassRent.class);
        List<ClassRent> classRents = parseValue(json, ClassRent.class);
        for (ClassRent tmp : classRents) {

        }
    }

    public int updateClassInfo(int size, int page, List<String> types) {
        return updateClassInfo(size, page, types, "1");
    }

    public int updateClassInfo(int size, int page, List<String> types, String campus) {
        // 解析resultValue
        String json = post(CLASSINFO_URL, size, page, ClassInfo.class);
        List<ClassInfo> classInfos = parseValue(json, ClassInfo.class);
        handlerClassRoom(classInfos, types);
        boolean cam = false;
        if ("1".equals(campus) || "2".equals(campus) || "3".equals(campus)) {
            handlerClassRoom(classInfos, campus);
            cam = true;
        }
        System.out.println(classInfos);
        Lab lab = new Lab();
        String[] buildNum;
        int count = 0;
        Set<String> campusSet = new HashSet<>();
        System.out.println("size"+classInfos.size());
        for (ClassInfo tmp : classInfos) {
            System.out.println(tmp);
            if (isBuildingExists(campus, tmp.getJSH())) {
                System.out.println("enter ex");
                continue;
            }
            // 加入集合
            campusSet.add(tmp.getJSH());
            lab.setId(tmp.getID_());
            if (cam) {
                lab.setCampusId(campus);
            }
            // 教室号 3-226
            if (tmp.getJSH().contains("-")) {
                buildNum = splitClassRoomNum(tmp.getJSH());
                lab.setBuildNum(buildNum[0]);
                lab.setNumber(buildNum[1]);
            } else {
                lab.setBuildNum(tmp.getJSH());
                lab.setNumber("0");
            }
            if (labMapper.insertLab(lab) > 0) {
                count++;
            }
            System.out.println("haha");
        }
        // 存入redis
        redisCache.setCacheSet(BUILDING_KEY+campus, campusSet);
        return count;
    }

    public void updateGraduateClass(int size,  int page) {
        // 解析resultValue
        String json = post(GRADUATE_URL, size, page, GraduateClass.class);
        List<GraduateClass> graduateClasses = parseValue(json, GraduateClass.class);
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

    public void updateGraduateClass() {
        updateGraduateClass(10, 1);
    }

    public void updateUnGraduateClass(int size, int page) {
        // 解析resultValue
        String json = post(UNDERGRADUATE_URL, size, page, UnGraduateClass.class);
        List<UnGraduateClass> unGraduateClasses = parseValue(json, UnGraduateClass.class);
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

    public void updateUnGraduateClass() {
        updateUnGraduateClass(10, 1);
    }

    /**
     * 更新所有实验室
     *
     * @param types  类型
     * @param campus 校园
     * @return int
     * @author sora
     * @date 2022/03/12
     */
    public int updateAllBuilding(List<String> types, String campus) {
        int total = getTotal(CLASSINFO_URL, ClassInfo.class);
        int size = 100;
        int page = 1, num = 0, res = 0;
        while (num <= total) {
            res += updateClassInfo(size, page, types, campus);
            page++;
            num += size;
        }
        System.out.println("update all finish");
        return res;
    }

    /**
     * 更新所有实验室
     *
     * @param types  类型
     * @return int
     * @author sora
     * @date 2022/03/12
     */
    public int updateAllBuilding(List<String> types) {
        return updateAllBuilding(types, "1");
    }

    /**
     * 删除redis的实验室缓存
     *
     * @param key 关键
     * @author sora
     * @date 2022/03/12
     */
    public void delBuildCache(String key) {
        redisCache.deleteObject(key);
    }

    /**
     * 得到请求头
     *
     * @return {@link HttpHeaders }
     * @author sora
     * @date 2022/03/11
     */
    private HttpHeaders getHeader() {
        if (this.header == null) {
            this.header = new HttpHeaders();
            this.header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            this.header.add("Authorization", this.token);
        }
        return this.header;
    }

    /**
     * 返回页面表单数据参数
     *
     * @param size 大小
     * @param page 页面
     * @return {@link MultiValueMap<String, Integer> }
     * @author sora
     * @date 2022/03/11
     */
    private MultiValueMap<String, Integer> getPageFormDataParam(int size, int page) {
        MultiValueMap<String, Integer> map = new LinkedMultiValueMap<>();
        map.add("size",size);
        map.add("page",page);
        return map;
    }

    /**
     * 发送请求
     *
     * @param url         url
     * @param size        大小
     * @param page        页面
     * @param resultValue 结果值
     * @return {@link LinkedList<T> }
     * @author sora
     * @date 2022/03/11
     */
    private <T> String post(String url, int size, int page, Class<T> resultValue) {
        MultiValueMap<String, Integer> map = getPageFormDataParam(size, page);
        HttpEntity<MultiValueMap<String, Integer>> param = new HttpEntity<>(map, getHeader());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, param, String.class);
        String body = responseEntity.getBody();
        if (body == null) {
            System.out.println("updateRent body is nulll");
            return null;
        }
        SicauBody<T> sicauBody = JSONObject.parseObject(body, SicauBody.class);
        int status = sicauBody.getStatus();
        System.out.println(status);
        if (status == this.obsoleteStatus) {
            login();
            return post(url, size, page, resultValue);
        }
        return body;
    }

    private <T> LinkedList<T> parseValue(String json, Class<T> resultValue) {
        return new LinkedList<>(JSON.parseArray(JSON.parseObject(json).getString("resultValue"), resultValue));
    }


    private <T> int getTotal(String url, Class<T> resultValue) {
        MultiValueMap<String, Integer> map = getPageFormDataParam(10, 1);
        HttpEntity<MultiValueMap<String, Integer>> param = new HttpEntity<>(map, getHeader());
        System.out.println(3);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, param, String.class);
        System.out.println(2);
        String body = responseEntity.getBody();
        if (body == null) {
            System.out.println("updateRent body is nulll");
            return -1;
        }
        System.out.println(1);
        SicauBody<T> sicauBody = JSONObject.parseObject(body, SicauBody.class);
        System.out.println(4);
        int status = sicauBody.getStatus();
        System.out.println(status);
        if (status == this.obsoleteStatus) {
            login();
            return getTotal(url, resultValue);
        }
        return sicauBody.getTotal();
    }

    /**
     * 过滤开课学期码，研究生的学期码格式为 2018，本科生的学期码格式为 2020-2021-2
     *
     * @param list  列表
     * @param years 想要剩下的年度
     * @author sora
     * @date 2022/03/11
     */
    private void handlerGraduateSchoolYear(LinkedList<Curriculum> list, String[] years) {
        boolean flag = false;
        for (int i = list.size()-1; i >= 0; i--) {
            String schoolYear = list.get(i).getKKXND();
            for (String year : years) {
                if (schoolYear.equals(year)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                // 如果不属于想要留下的就直接删除
                list.remove(i);
            }
            flag = false;
        }
    }

    /**
     * 根据教室类型来过滤教室
     *
     * @param list 列表
     * @param types 类型
     * @author sora
     * @date 2022/03/11
     */
    private void handlerClassRoom(List<ClassInfo> list, List<String> types) {
        boolean flag = false;
        for (int i = list.size()-1; i >= 0; i--) {
            String classroomType = list.get(i).getJSLXM();
            if (StringUtils.isNotEmpty(classroomType)) {
                for (String type : types) {
                    if (classroomType.equals(type)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                // 如果不属于想要留下的就直接删除
                list.remove(i);
            }
            flag = false;
        }
    }

    /**
     * 根据校区号来过滤教室
     *
     * @param list 列表
     * @param campus 类型 可选应该为 1、2、3
     * @author sora
     * @date 2022/03/11
     */
    private void handlerClassRoom(List<ClassInfo> list, String campus) {
        for (int i = list.size()-1; i >= 0; i--) {
            String num = list.get(i).getXQH();
            if (StringUtils.isEmpty(num)) {
                list.remove(i);
            } else if (!num.equals(campus)) {
                list.remove(i);
            }
        }
    }

    /**
     * 分离教室楼号和房号   4-202
     *
     * @param num 全国矿工工会
     * @return {@link int[] } 长度为2的数组，第一个为楼号，第二个为房号
     * @author sora
     * @date 2022/03/11
     */
    private String[] splitClassRoomNum(String num) {
        return num.split("-", 2);
    }

    /**
     * 判断该教室是否存在
     *
     * @param campus   校园
     * @param location 位置 10-504
     * @return boolean
     * @author sora
     * @date 2022/03/12
     */
    private boolean isBuildingExists(String campus, String location) {
        System.out.println(location);
        System.out.println("is exists?");
        Set<String> cacheSet = redisCache.getCacheSet(BUILDING_KEY + campus);
        System.out.println("here");
        return cacheSet.contains(location);
    }

    /**
     * 判断该教室是否存在
     *
     * @param campus   校园
     * @param buildNum 建立全国矿工工会
     * @param number   数量
     * @return boolean
     * @author sora
     * @date 2022/03/12
     */
    private boolean isBuildingExists(String campus, String buildNum, String number) {
        String location = buildNum + "-" + number;
        return isBuildingExists(campus, location);
    }
}
