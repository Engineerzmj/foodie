package com.zmj.mapper;

import com.zmj.pojo.vo.ItemCommentVO;
import com.zmj.pojo.vo.SearchItemsVO;
import com.zmj.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchCatItems(@Param("paramsMap") Map<String, Object> map);

    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List<String> specIdsList);
}