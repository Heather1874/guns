package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

@Data
public class ResponseVo {
    private String msg;
    private Integer status = 0;
    private Object data;
}
