package com.stylefeng.guns.rest.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmFieldVo implements Serializable {

    private String beginTime;

    private String endTime;

    private Integer fieldId;

    private  String hallName;

    private String language;

    private Integer price;
}
