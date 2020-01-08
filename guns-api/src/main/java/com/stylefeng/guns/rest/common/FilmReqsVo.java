package com.stylefeng.guns.rest.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 20:04
 */
@Data
public class FilmReqsVo<Object> implements Serializable {

    Object data;

    String imgPre = "http://img.meetingshop.cn/";

    String msg;

    Integer nowPage;

    Integer status = 0;

    Integer totalPage;
}
