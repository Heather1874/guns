package com.stylefeng.guns.rest.common.persistence.model;

import lombok.Data;
/*
* 用来接收播放场次里面cinemaInfo的信息
* */
@Data
public class CinemaInfo {

    private Integer cinemaId;

    private String cinemaAdress;

    private String cinemaName;

    private String cinemaPhone;

    private String imgUrl;
}
