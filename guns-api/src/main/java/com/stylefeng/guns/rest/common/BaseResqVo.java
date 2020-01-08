package com.stylefeng.guns.rest.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResqVo<T> implements Serializable {
    T data;
    String imgPre = "http://img.meetingshop.cn/";
    Integer status = 0;
    String msg = "成功";
}
