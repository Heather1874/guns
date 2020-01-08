package com.stylefeng.guns.rest.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 用来接收播放场次里面cinemaInfo的信息
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaInfoVo implements Serializable {

    private Integer cinemaId;

    private String cinemaAdress;

    private String cinemaName;

    private String cinemaPhone;

    private String imgUrl;
}
