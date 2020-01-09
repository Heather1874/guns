package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 20:35
 */
@Data
public class FilmConditionVo implements Serializable {

    private List<NewCatInfo> catInfo;

    private List<NewSourceInfo> sourceInfo;

    private List<NewYearInfo> yearInfo;
}
