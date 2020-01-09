package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:18
 */@Data
public class NewYearInfo implements Serializable {
     private Boolean active;

     private Integer yearId;

     private String yearName;

    public NewYearInfo() {
        this.active = false;
    }
}
