package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 20:17
 */
@Data
public class FilmDetail implements Serializable {

    /**
     * filmCats :
     * score : 9.7
     * expectNum : 231432491
     * boxNum : 309600
     * filmId : 2
     * filmLength :
     * filmType : 0
     * showTime : 2018-07-05
     * filmName : 我不是药神
     * imgAddress : 238e2dc36beae55a71cabfc14069fe78236351.jpg
     * filmScore : 9.7
     */
    private String filmCats;
    private String score;
    private Integer expectNum;
    private Integer boxNum;
    private String filmId;
    private String filmLength;
    private Integer filmType;
    private String showTime;
    private String filmName;
    private String imgAddress;
    private String filmScore;

}
