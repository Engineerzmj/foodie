package com.zmj.service;

import com.zmj.pojo.Stu;

public interface StuService {

    public Stu getStu(Long id);

    public int saveStu();

    public int updateStu();

    public int deleteStu();
}
