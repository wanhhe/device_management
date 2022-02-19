package com.sicau.devicemanagement.controller;

import com.sicau.devicemanagement.common.core.controller.entity.AjaxResult;
import com.sicau.devicemanagement.service.impl.SicauService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sicau")
public class SicauController {

    @Resource
    private SicauService sicauService;

    @GetMapping("/lab")
    public AjaxResult updateLab(int size, int page) {
        sicauService.updateClassInfo(size, page);
        return AjaxResult.success();
    }

    @GetMapping("/graduate")
    public AjaxResult updateGraduateClass(int size, int page) {
        sicauService.updateGraduateClass(size, page);
        return AjaxResult.success();
    }

    @GetMapping("/ungraduate")
    public AjaxResult updateUnGraduateClass(int size, int page) {
        sicauService.updateUnGraduateClass(size, page);
        return AjaxResult.success();
    }

    @GetMapping("/rent")
    public AjaxResult updateRent(int size, int page) {
        sicauService.updateRent(size, page);
        return AjaxResult.success();
    }
}