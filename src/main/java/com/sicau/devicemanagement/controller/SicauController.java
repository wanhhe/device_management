package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.service.impl.SicauService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sicau")
public class SicauController {

    @Resource
    private SicauService sicauService;

    @GetMapping("/lab/{size}/{page}")
    public AjaxResult updateLab(@PathVariable("size") int size, @PathVariable("page") int page) {
        System.out.println("enter lab");
        sicauService.updateClassInfo(size, page);
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
}