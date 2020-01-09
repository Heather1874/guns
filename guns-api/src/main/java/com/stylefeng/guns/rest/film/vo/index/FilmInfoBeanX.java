package com.stylefeng.guns.rest.film.vo.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfoBeanX implements Serializable {
    private int expectNum;
    private String filmId;
    private String filmName;
    private int filmType;
    private String imgAddress;
    private String showTime;
}
