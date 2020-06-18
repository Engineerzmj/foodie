package com.zmj.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    private List<SubCategoryVO> subCatList;
}
