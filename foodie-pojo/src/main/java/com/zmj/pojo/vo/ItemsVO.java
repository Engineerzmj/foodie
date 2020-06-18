package com.zmj.pojo.vo;

import com.zmj.pojo.Items;
import com.zmj.pojo.ItemsImg;
import com.zmj.pojo.ItemsParam;
import com.zmj.pojo.ItemsSpec;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemsVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}
