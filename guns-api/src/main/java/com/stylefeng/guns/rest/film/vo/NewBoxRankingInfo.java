package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewBoxRankingInfo implements Serializable {
    int boxNum;
    int expectNum;
    String filmCats;
    String filmId;
    String filmLength;
    String filmName;
    String filmScore;
    int filmType;
    String imgAddress;
    String score;
    String showTime;
}
