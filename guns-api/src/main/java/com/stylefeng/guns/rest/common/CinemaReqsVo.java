package com.stylefeng.guns.rest.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaReqsVo implements Serializable {
    Object data;

    String imgPre = "http://img.meetingshop.cn/";

    Integer status = 0;

    String msg;

    Integer nowPage = 1;

    Integer totalPage = 1;

}
