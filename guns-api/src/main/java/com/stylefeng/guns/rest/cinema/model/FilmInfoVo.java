package com.stylefeng.guns.rest.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmInfoVo implements Serializable {

    private Integer filmId;

    private String filmName;

    private String filmLength;

    private String filmType;

    private String filmCats;

    private String actors;

    private String imgAddress;

    private List<FilmFieldVo> filmFields;


}
