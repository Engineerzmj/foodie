package com.zmj.controller;

import com.zmj.pojo.bo.ShopcartBO;
import com.zmj.utils.JSONResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    final static Logger logger = LoggerFactory.getLogger(ShopcartController.class);

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopcartBO shopcartBO,
                          HttpServletRequest request,
                          HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        System.out.println(shopcartBO.toString());

        //TODO 用户在登录情况下，会同步到redis缓存中

        return JSONResult.ok();
    }

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult add(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          HttpServletRequest request,
                          HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("");
        }

        //TODO 用户在登录情况下，前端删除购物车中的数据，后端要同步删除redis缓存中的数据

        return JSONResult.ok();
    }
}
