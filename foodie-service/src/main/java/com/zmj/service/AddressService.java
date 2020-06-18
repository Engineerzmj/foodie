package com.zmj.service;

import com.zmj.pojo.UserAddress;
import com.zmj.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 根据用户id查询地址列表
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);

    public void addAddress(AddressBO addressBO);

    public void deleteAddress(AddressBO addressBO);

    public void updateAddress(AddressBO addressBO);

    public void updateUserAddressToBeDefault(String userId, String addressId);

    public UserAddress queryUserAddress(String userId, String addressId);
}
