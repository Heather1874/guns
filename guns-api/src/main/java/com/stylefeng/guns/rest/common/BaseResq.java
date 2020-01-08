package com.stylefeng.guns.rest.common;

import lombok.Data;

@Data
public class BaseResq<T> {
    T data;
    Integer status;
    String msg;
}
