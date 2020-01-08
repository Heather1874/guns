package com.stylefeng.guns.rest.film.param;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 20:11
 */
@Data
public class FilmGetFilmsParam {
    Integer showType;
    Integer sortId;
    Integer catId;
    Integer sourceId;
    Integer yearId;
    Integer nowPage;
    Integer pageSize;
    Integer offset;
}
