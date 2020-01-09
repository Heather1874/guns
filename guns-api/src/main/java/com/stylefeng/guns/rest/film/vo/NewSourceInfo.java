package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:16
 */
@Data
public class  NewSourceInfo implements Serializable {
    private Boolean active;

    private Integer sourceId;

    private String sourceName;

    public NewSourceInfo() {
        this.active = false;
    }
}
