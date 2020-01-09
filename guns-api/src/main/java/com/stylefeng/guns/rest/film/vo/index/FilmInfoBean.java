package com.stylefeng.guns.rest.film.vo.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfoBean implements Serializable {
    private String filmId;
    private String filmName;
    private String filmScore;
    private int filmType;
    private String imgAddress;
}
