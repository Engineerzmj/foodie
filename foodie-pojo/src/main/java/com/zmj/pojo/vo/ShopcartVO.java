package com.zmj.pojo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopcartVO {

    private String itemId;
    private String itemName;
    private String itemImgUrl;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
