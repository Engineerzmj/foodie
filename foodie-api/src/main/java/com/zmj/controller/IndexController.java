package com.zmj.controller;

import com.zmj.enums.YesOrNot;
import com.zmj.pojo.Carousel;
import com.zmj.pojo.Category;
import com.zmj.pojo.vo.NewItemsVO;
import com.zmj.service.CarouselService;
import com.zmj.service.CategoryService;
import com.zmj.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult queryAllCarousel() {
        List<Carousel> result = carouselService.queryAll(YesOrNot.YES.type);
        return JSONResult.ok(result);
    }

    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult queryRootCats() {
        List<Category> result = categoryService.queryAllRootLevelCat();
        return JSONResult.ok(result);
    }

    @ApiOperation(value = "获取商品分类(子分类)", notes = "获取商品分类(子分类)", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<Category> result = categoryService.querySubCategory(rootCatId);
        return JSONResult.ok(result);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品信息", notes = "查询每个一级分类下的最新6条商品信息", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> result = categoryService.getSixNewItems(rootCatId);
        return JSONResult.ok(result);
    }
}
