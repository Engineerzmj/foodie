package com.zmj.pojo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLevelCountVO {

    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;
    private Integer totalCounts;
}
