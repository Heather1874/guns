package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:29
 */
public interface FilmServiceApi {

    Map getIndex();

    Map getFilmConditionList(FilmGetConditionListParam params);

}
