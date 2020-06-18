package com.zmj.service.impl;

import com.zmj.mapper.StuMapper;
import com.zmj.pojo.Stu;
import com.zmj.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStu(Long id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveStu() {
        return 0;
    }

    @Override
    public int updateStu() {
        return 0;
    }

    @Override
    public int deleteStu() {
        return 0;
    }
}
