package com.zmj.service;

import com.zmj.pojo.Items;
import com.zmj.pojo.ItemsImg;
import com.zmj.pojo.ItemsParam;
import com.zmj.pojo.ItemsSpec;
import com.zmj.pojo.vo.CommentLevelCountVO;
import com.zmj.pojo.vo.ItemCommentVO;
import com.zmj.pojo.vo.SearchItemsVO;
import com.zmj.pojo.vo.ShopcartVO;
import com.zmj.utils.PagedGridResult;

import java.util.List;

public interface ItemsService {

    /**
     * 根据商品ID查询商品信息
     * @param itemId
     * @return
     */
    public Items getItemsById(String itemId);

    /**
     * 根据商品ID查询商品图片
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemsImgList(String itemId);

    /**
     * 根据商品ID查询商品规则
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemsSpecList(String itemId);

    /**
     * 根据商品ID查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemsParamList(String itemId);

    /**
     * 根据商品ID查询商品的评价等级数量
     * @param itemId
     * @return
     */
    public CommentLevelCountVO queryCommentLevelCount(String itemId);

    /**
     * 根据商品ID查询商品的评价（带有分页的）
     * @return
     */
    public PagedGridResult queryPagedComment(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表（根据关键词）
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchCatItems(String catId, String sort, Integer page, Integer pageSize);

    public List<ShopcartVO> queryItemsBySpecIds(String specIds);
}
