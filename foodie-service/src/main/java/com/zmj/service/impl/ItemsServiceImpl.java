package com.zmj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.zmj.enums.CommentLevel;
import com.zmj.mapper.*;
import com.zmj.pojo.*;
import com.zmj.pojo.vo.CommentLevelCountVO;
import com.zmj.pojo.vo.ItemCommentVO;
import com.zmj.pojo.vo.SearchItemsVO;
import com.zmj.pojo.vo.ShopcartVO;
import com.zmj.service.ItemsService;
import com.zmj.utils.DesensitizationUtil;
import com.zmj.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items getItemsById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemsImgList(String itemId) {
        Example itemImgExample = new Example(ItemsImg.class);
        Example.Criteria criteria = itemImgExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(itemImgExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemsSpecList(String itemId) {
        Example itemSpecExample = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemSpecExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(itemSpecExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemsParamList(String itemId) {
        Example itemParamExample = new Example(ItemsParam.class);
        Example.Criteria criteria = itemParamExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(itemParamExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountVO queryCommentLevelCount(String itemId) {
        int goodCommentCount = getCommentCount(itemId, CommentLevel.GOOD.type);
        int normalCommentCount = getCommentCount(itemId, CommentLevel.NORMAL.type);
        int badCommentCount = getCommentCount(itemId, CommentLevel.BAD.type);
        int totalCount = goodCommentCount + normalCommentCount + badCommentCount;

        CommentLevelCountVO commentLevelCountVO = new CommentLevelCountVO();
        commentLevelCountVO.setGoodCounts(goodCommentCount);
        commentLevelCountVO.setNormalCounts(normalCommentCount);
        commentLevelCountVO.setBadCounts(badCommentCount);
        commentLevelCountVO.setTotalCounts(totalCount);
        return commentLevelCountVO;
    }

    private int getCommentCount(String itemId, Integer commentLevel) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (commentLevel != null) {
            itemsComments.setCommentLevel(commentLevel);
        }
        return itemsCommentsMapper.selectCount(itemsComments);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComment(String itemId, Integer level, Integer page, Integer pageSize) {
        Map map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        //开始分页
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO itemCommentVO : list) {
            itemCommentVO.setNickName(DesensitizationUtil.commonDisplay(itemCommentVO.getNickName()));
        }
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);
        //开始分页
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchCatItems(String catId, String sort, Integer page, Integer pageSize) {
        Map map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);
        //开始分页
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchCatItems(map);
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
        List<String> list = Splitter.on(",").omitEmptyStrings().splitToList(specIds);
        return itemsMapperCustom.queryItemsBySpecIds(list);
    }


    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
