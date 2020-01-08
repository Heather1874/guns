package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.param.FilmGetFilmsParam;
import com.stylefeng.guns.rest.film.vo.FilmConditionVo;
import com.stylefeng.guns.rest.film.vo.FilmDetail;


import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:29
 */
public interface FilmServiceApi {


    Map getIndex();

    FilmConditionVo getFilmConditionList(FilmGetConditionListParam params);

    List<FilmDetail> getFilmsByCondition(FilmGetFilmsParam params);

}
