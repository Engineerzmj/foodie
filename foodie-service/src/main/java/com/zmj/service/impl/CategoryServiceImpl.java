package com.zmj.service.impl;

import com.zmj.mapper.CategoryMapper;
import com.zmj.mapper.CategoryMapperCustom;
import com.zmj.pojo.Carousel;
import com.zmj.pojo.Category;
import com.zmj.pojo.vo.NewItemsVO;
import com.zmj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> querySubCategory(Integer id) {
        return categoryMapperCustom.getSubCatsList(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItems(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItems(map);
    }
}
