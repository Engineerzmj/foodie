package com.zmj.controller;

import com.zmj.enums.YesOrNot;
import com.zmj.pojo.*;
import com.zmj.pojo.vo.CommentLevelCountVO;
import com.zmj.pojo.vo.ItemsVO;
import com.zmj.pojo.vo.NewItemsVO;
import com.zmj.pojo.vo.ShopcartVO;
import com.zmj.service.CarouselService;
import com.zmj.service.CategoryService;
import com.zmj.service.ItemsService;
import com.zmj.utils.JSONResult;
import com.zmj.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品信息接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemsService itemsService;


    @ApiOperation(value = "获取商品详情", notes = "获取商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult getInfo(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        Items items = itemsService.getItemsById(itemId);
        List<ItemsImg> itemsImgs = itemsService.queryItemsImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemsService.queryItemsSpecList(itemId);
        ItemsParam itemsParams = itemsService.queryItemsParamList(itemId);
        ItemsVO itemsVO = new ItemsVO();
        itemsVO.setItem(items);
        itemsVO.setItemImgList(itemsImgs);
        itemsVO.setItemSpecList(itemsSpecs);
        itemsVO.setItemParams(itemsParams);

        return JSONResult.ok(itemsVO);
    }

    @ApiOperation(value = "获取商品评论等级数量", notes = "获取商品评论等级数量", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        CommentLevelCountVO commentLevelCountVO = itemsService.queryCommentLevelCount(itemId);

        return JSONResult.ok(commentLevelCountVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询的下一页是第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
            ) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemsService.queryPagedComment(itemId, level, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(
            @ApiParam(name = "keywords", value = "搜索关键词", required = false)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询的下一页是第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemsService.searchItems(keywords, sort, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "根据分类ID搜索商品列表", notes = "根据分类ID搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(
            @ApiParam(name = "catId", value = "分类id", required = false)
            @RequestParam String catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询的下一页是第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemsService.searchCatItems(catId, sort, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "根据商品规则ids查询最近的商品信息", notes = "根据商品规则ids查询最近的商品信息", httpMethod = "GET")
    @GetMapping("/refresh")
    public JSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接商品规格ids", required = true, example = "1,3,5")
            @RequestParam String itemSpecIds
    ) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }
        List<ShopcartVO> list = itemsService.queryItemsBySpecIds(itemSpecIds);
        return JSONResult.ok(list);
    }
}
