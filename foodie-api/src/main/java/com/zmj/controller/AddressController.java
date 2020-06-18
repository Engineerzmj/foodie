package com.zmj.controller;

import com.zmj.pojo.UserAddress;
import com.zmj.pojo.bo.AddressBO;
import com.zmj.service.AddressService;
import com.zmj.utils.JSONResult;
import com.zmj.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户在确认订单页面，可以针对收货地址做如下操作：
 * 1. 查询用户的所有收货地址列表
 * 2. 新增收货地址
 * 3. 删除收货地址
 * 4. 修改收货地址
 * 5. 设置默认地址
 */

@Api(value = "地址相关接口", tags = "地址相关的api接口")
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "查询地址列表", notes = "查询地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(
            @ApiParam(value = "用户id", name = "用户id", required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        List<UserAddress> list = addressService.queryAll(userId);
        return JSONResult.ok(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestBody AddressBO addressBO) {
        JSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != HttpStatus.OK.value()) {
            return checkRes;
        }
        addressService.addAddress(addressBO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getUserId()) || StringUtils.isBlank(addressBO.getAddressId())) {
            return JSONResult.errorMsg("");
        }

        addressService.deleteAddress(addressBO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return JSONResult.errorMsg("addressId不能为空");
        }

        JSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.updateAddress(addressBO);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public JSONResult setDefalut(@RequestBody AddressBO addressBO) {

        String userId = addressBO.getUserId();
        String addressId = addressBO.getAddressId();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }

        return JSONResult.ok();
    }
}
