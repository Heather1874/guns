package com.stylefeng.guns.rest.film.vo.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExpectRankingBean implements Serializable {
    private int expectNum;
    private String filmId;
    private String filmName;
    private String imgAddress;
}
