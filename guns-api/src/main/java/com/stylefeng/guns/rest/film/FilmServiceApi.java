package com.stylefeng.guns.rest.film;

import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.param.FilmGetFilmsParam;
import com.stylefeng.guns.rest.film.vo.FilmConditionVo;
import com.stylefeng.guns.rest.film.vo.FilmDetail;
import com.stylefeng.guns.rest.film.vo.FilmDetailVo;
import com.stylefeng.guns.rest.film.vo.NewIndexInfo;


import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:29
 */
public interface FilmServiceApi {


    NewIndexInfo getIndex();

    FilmConditionVo getFilmConditionList(FilmGetConditionListParam params);

    List<FilmDetail> getFilmsByCondition(FilmGetFilmsParam params);


    List<FilmDetail> getFilmsByIntegerKeyWord(Integer keyWord);

    List<FilmDetail> getFilmsByStringKeyWord(String kw);

    FilmDetailVo getFilmDetailById(String filmId);

    FilmDetailVo getFilmDetailByName(String filmName);
}
