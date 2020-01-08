package com.stylefeng.guns.rest.common;

import lombok.Data;

@Data
public class BaseResqVo<T> {
    T data;
    String imgPre = "http://img.meetingshop.cn/";
    Integer status = 0;
    String msg = "成功";
}
