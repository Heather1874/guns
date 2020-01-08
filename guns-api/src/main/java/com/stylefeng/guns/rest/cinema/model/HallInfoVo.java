package com.stylefeng.guns.rest.cinema.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVo implements Serializable {

    private Integer hallFieldId;

    private String hallName;

    private Integer price;

    private String seatFile;
//关联订单才能查询
    private String soldSeats;
}
