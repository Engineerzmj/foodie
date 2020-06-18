package com.zmj.service.impl;

import com.zmj.enums.YesOrNot;
import com.zmj.mapper.UserAddressMapper;
import com.zmj.pojo.UserAddress;
import com.zmj.pojo.bo.AddressBO;
import com.zmj.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        List<UserAddress> list = userAddressMapper.select(userAddress);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addAddress(AddressBO addressBO) {

        Integer isDefault = 0;
        List<UserAddress> userAddresses = this.queryAll(addressBO.getUserId());
        if (userAddresses == null || userAddresses.size() == 0 || userAddresses.isEmpty()) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAddress(AddressBO addressBO) {

        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUserId(addressBO.getUserId());

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateAddress(AddressBO addressBO) {

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1. 查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNot.YES.type);
        List<UserAddress> list  = userAddressMapper.select(queryAddress);
        for (UserAddress ua : list) {
            ua.setIsDefault(YesOrNot.NOT.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNot.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress singleAddress = new UserAddress();
        singleAddress.setId(addressId);
        singleAddress.setUserId(userId);

        return userAddressMapper.selectOne(singleAddress);
    }
}
