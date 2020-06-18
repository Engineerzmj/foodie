package com.zmj.pojo.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressBO {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
