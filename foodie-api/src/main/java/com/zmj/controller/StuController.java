package com.zmj.controller;

import com.zmj.pojo.Stu;
import com.zmj.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping("getStu/{id}")
    public Stu getStu(@PathVariable Long id) {
        return stuService.getStu(id);
    }
}
