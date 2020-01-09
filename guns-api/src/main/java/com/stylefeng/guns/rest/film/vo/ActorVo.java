package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 22:57
 */
@Data
public class ActorVo implements Serializable {
    private String directorName;
    private String roleName;
    private String imgAddress;
}
