package com.zmj.mapper;

import com.zmj.my.mapper.MyMapper;
import com.zmj.pojo.Category;
import com.zmj.pojo.Items;
import com.zmj.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom extends MyMapper<Category> {

    /**
     * 根据父级分类Id查询所有子分类
     * @param rootCatId
     * @return
     */
    public List<Category> getSubCatsList(Integer rootCatId);

    /**
     * 获取当前分类下的最新六个商品信息
     * @param map
     * @return
     */
    public List<NewItemsVO> getSixNewItems(@Param("paramsMap") Map<String, Object> map);

}