package com.sicau.devicemanagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.domain.model.BigJson;
import com.sicau.devicemanagement.domain.model.JSONBO;
import com.sicau.devicemanagement.domain.sicau.ClassInfo;
import com.sicau.devicemanagement.domain.sicau.GraduateClass;
import com.sicau.devicemanagement.domain.sicau.SicauBody;
import com.sicau.devicemanagement.service.impl.SicauService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/sicau")
public class SicauController {

    @Resource
    private SicauService sicauService;

    @GetMapping("/lab/{size}/{page}")
    public AjaxResult updateLab(@PathVariable("size") int size, @PathVariable("page") int page, @RequestParam("types") List<String> types) {
        System.out.println("enter lab");
        sicauService.updateClassInfo(size, page, types);
        return AjaxResult.success();
    }

    @GetMapping("/lab/{size}/{page}/{campus}")
    public AjaxResult updateLab(@PathVariable("size") int size, @PathVariable("page") int page,
                                @PathVariable("campus") String campus, @RequestParam("types") List<String> types) {
        System.out.println("enter lab");
        for (String s : types) {
            System.out.println(s);
        }
        sicauService.updateClassInfo(size, page, types, campus);
        return AjaxResult.success();
    }

    @GetMapping("/graduate/{size}/{page}")
    public AjaxResult updateGraduateClass(@PathVariable("size") int size, @PathVariable("page") int page) {
        sicauService.updateGraduateClass(size, page);
        return AjaxResult.success();
    }

    @GetMapping("/ungraduate/{size}/{page}")
    public AjaxResult updateUnGraduateClass(@PathVariable("size") int size, @PathVariable("page") int page) {
        sicauService.updateUnGraduateClass(size, page);
        return AjaxResult.success();
    }

    @GetMapping("/rent/{size}/{page}")
    public AjaxResult updateRent(@PathVariable("size") int size, @PathVariable("page") int page) {
        sicauService.updateRent(size, page);
        return AjaxResult.success();
    }

    @PutMapping("/token/{token}")
    public AjaxResult forceUpdateToken(@PathVariable("token") String token) {
        sicauService.forceUpdateToken(token);
        return AjaxResult.success();
    }

    @GetMapping("/token")
    public AjaxResult getToken() {
        return AjaxResult.success(sicauService.getToken());
    }

//    @GetMapping("/json")
//    public AjaxResult json() {
//        JSONBO jsonbo = new JSONBO();
//        List<JSONBO> list = new ArrayList<>();
//        list.add(jsonbo);
//        JSONBO j1 = new JSONBO();
//        list.add(j1);
//        return AjaxResult.success(list);
//    }
//
//    @GetMapping("/parse")
//    public void parse(@RequestParam("json") String json) {
//        String text = "{\n" +
//                "    \"msg\": \"操作成功\",\n" +
//                "    \"code_code\": 200,\n" +
//                "    \"data\": [\n" +
//                "        {\n" +
//                "            \"id\": \"1\",\n" +
//                "            \"sub\": {\n" +
//                "                \"username\": \"sora\",\n" +
//                "                \"password\": \"123\"\n" +
//                "            }\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"id\": \"1\",\n" +
//                "            \"sub\": {\n" +
//                "                \"username\": \"sora\",\n" +
//                "                \"password\": \"123\"\n" +
//                "            }\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//        BigJson ajaxResult = JSONObject.parseObject(text, BigJson.class);
//        System.out.println(ajaxResult);
//        List<JSONBO> data = ajaxResult.getData();
//        for (JSONBO tmp : data) {
//            System.out.println(tmp);
//        }
//    }
//
//
//    public <T> void parse(Class<T> resultValue) {
//        String text = "{\"status\":200,\"success\":true,\"resultHead\":[\"JSGLBM\",\"YXZWS\",\"JSH\",\"JSMS\",\"XQH\",\"SZLC\",\"FLAG_\",\"DATA_FROM_\",\"ZWS\",\"JXLH\",\"JSYT\",\"ID_\",\"JSLXM\",\"KSZWS\",\"ID_\"],\"resultValue\":[{\"JSGLBM\":\"\",\"JSLXM\":\"多媒体\",\"ID_\":\"1870837\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"106\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"106\",\"YXZWS\":\"106\",\"SZLC\":\"\",\"JSH\":\"10-A707\"},{\"JSGLBM\":\"\",\"JSLXM\":\"实验室\",\"ID_\":\"1870838\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"85\",\"JSMS\":\"\",\"XQH\":\"3\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"85\",\"YXZWS\":\"85\",\"SZLC\":\"\",\"JSH\":\"都江堰_校区外\"},{\"JSGLBM\":\"\",\"JSLXM\":\"多媒体\",\"ID_\":\"1870839\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"30\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"30\",\"YXZWS\":\"30\",\"SZLC\":\"\",\"JSH\":\"1-403\"},{\"JSGLBM\":\"\",\"JSLXM\":\"多媒体\",\"ID_\":\"1870840\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"91\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"91\",\"YXZWS\":\"91\",\"SZLC\":\"\",\"JSH\":\"10-A311\"},{\"JSGLBM\":\"\",\"JSLXM\":\"操场\",\"ID_\":\"1870841\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"45\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"45\",\"YXZWS\":\"45\",\"SZLC\":\"\",\"JSH\":\"2校区运动场03\"},{\"JSGLBM\":\"\",\"JSLXM\":\"实验室\",\"ID_\":\"1870842\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"40\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"40\",\"YXZWS\":\"40\",\"SZLC\":\"\",\"JSH\":\"4-504\"},{\"JSGLBM\":\"\",\"JSLXM\":\"实验室\",\"ID_\":\"1870843\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"24\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"24\",\"YXZWS\":\"24\",\"SZLC\":\"\",\"JSH\":\"4-505\"},{\"JSGLBM\":\"\",\"JSLXM\":\"实验室\",\"ID_\":\"1870844\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"25\",\"JSMS\":\"\",\"XQH\":\"1\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"25\",\"YXZWS\":\"25\",\"SZLC\":\"\",\"JSH\":\"4-506\"},{\"JSGLBM\":\"\",\"JSLXM\":\"实验室\",\"ID_\":\"1870845\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"45\",\"JSMS\":\"\",\"XQH\":\"2\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"45\",\"YXZWS\":\"45\",\"SZLC\":\"\",\"JSH\":\"5-703\"},{\"JSGLBM\":\"\",\"JSLXM\":\"实验室\",\"ID_\":\"1870846\",\"JSYT\":\"\",\"JXLH\":\"\",\"KSZWS\":\"58\",\"JSMS\":\"\",\"XQH\":\"2\",\"FLAG_\":\"0\",\"DATA_FROM_\":\"教务\",\"ZWS\":\"58\",\"YXZWS\":\"58\",\"SZLC\":\"\",\"JSH\":\"5-627\"}],\"total\":865,\"num\":10,\"pages\":[{\"name\":\"page\",\"value\":\"1\",\"desc\":\"当前页\"},{\"name\":\"size\",\"value\":\"10\",\"desc\":\"当前页大小\"}],\"sqlType\":\"SELECT\"}";
//        SicauBody<ClassInfo> sicauBody = JSONObject.parseObject(text, SicauBody.class);
//        System.out.println(sicauBody);
//        List<T> value = JSON.parseArray(JSON.parseObject(text).getString("resultValue"), resultValue);
//        for (T t : value) {
//            System.out.println(t);
//        }
//    }
//
//    @GetMapping("/parse/json")
//    public void parse() {
//        parse(ClassInfo.class);
//    }
//
//    @GetMapping("/list")
//    public void list() {
//        List<String> test = new LinkedList<>();
//        test.add("1");
//        test.add("2");
//        test.add("3");
//        test.add("1");
//        for (int i = test.size()-1; i >= 0; i--) {
//            if ("1".equals(test.get(i))) {
//                test.remove(i);
//            }
//        }
//        System.out.println(test);
//    }
}