package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:13
 */
@Data
public class NewCatInfo {
    private Boolean active;

    private Integer catId;

    private String catName;

    public NewCatInfo() {
        this.active = false;
    }
}
