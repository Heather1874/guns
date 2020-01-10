package com.stylefeng.guns.rest.film.vo.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class BoxRankingBean implements Serializable {
    private int boxNum;
    private String filmId;
    private String filmName;
    private String imgAddress;
}
