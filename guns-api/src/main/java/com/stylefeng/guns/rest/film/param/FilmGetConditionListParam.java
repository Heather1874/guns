package com.stylefeng.guns.rest.film.param;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:41
 */
@Data
public class FilmGetConditionListParam {
    private Integer catId;

    private Integer sourceId;

    private Integer yearId;
}
