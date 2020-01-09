package com.stylefeng.guns.rest.film.vo.index;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SoonFilmsBean implements Serializable {
    private long filmNum;
    private List<FilmInfoBeanX> filmInfo;
}
