package com.zmj.service.impl;

import com.zmj.enums.Sex;
import com.zmj.mapper.UsersMapper;
import com.zmj.pojo.Users;
import com.zmj.pojo.bo.UserBO;
import com.zmj.service.UserService;
import com.zmj.utils.DateUtil;
import com.zmj.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "https://img2.woyaogexing.com/2019/04/15/eede21c86c484241a75ed01c19373033!400x400.jpeg";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(userExample);
        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setNickname(user.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(DateUtil.stringToDate("1970-01-01"));
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);

        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        Users result = usersMapper.selectOneByExample(example);
        return result;
    }
}
