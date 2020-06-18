package com.zmj.service;

import com.zmj.pojo.Category;
import com.zmj.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有以及分类
     * @return
     */
    public List<Category> queryAllRootLevelCat();

    public List<Category> querySubCategory(Integer id);

    public List<NewItemsVO> getSixNewItems(Integer rootCatId);
}
