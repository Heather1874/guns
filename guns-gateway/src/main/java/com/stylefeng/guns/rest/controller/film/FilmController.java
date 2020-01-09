package com.stylefeng.guns.rest.controller.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.BaseResqVo;
import com.stylefeng.guns.rest.common.FilmReqsVo;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.param.FilmGetFilmsParam;
import com.stylefeng.guns.rest.film.vo.FilmConditionVo;
import com.stylefeng.guns.rest.film.vo.FilmDetail;
import com.stylefeng.guns.rest.film.vo.FilmDetailVo;
import org.springframework.web.bind.annotation.PathVariable;
import com.stylefeng.guns.rest.film.vo.NewIndexInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:26
 */
@RestController
public class FilmController {

    @Reference(interfaceClass = FilmServiceApi.class)
    FilmServiceApi filmServiceApi;

    /**
     * 列出影片的条件（类型、区域、年代）
     *
     * @return
     */
    @RequestMapping("/film/getIndex")
    public BaseResqVo<Object> getIndex() {

        NewIndexInfo newIndexInfo = filmServiceApi.getIndex();

        BaseResqVo<Object> baseResqVo = new BaseResqVo<>();
        baseResqVo.setData(newIndexInfo);
        return baseResqVo;
    }

    @RequestMapping("/film/getConditionList")
    public FilmReqsVo getFilmConditionListList(FilmGetConditionListParam params) {
        FilmReqsVo<Object> filmReqsVo = new FilmReqsVo<>();
        FilmConditionVo filmConditionVo = filmServiceApi.getFilmConditionList(params);
        filmReqsVo.setData(filmConditionVo);
        filmReqsVo.setStatus(0);
        return filmReqsVo;
    }

    /**
     * 根据关键字来查询电影，通过是否可以转为Integer来判断关键是影片id还是影片name
     *
     * @param params
     * @return
     */
    @RequestMapping("/film/getFilms")
    public FilmReqsVo getFilms(FilmGetFilmsParam params) {
        FilmReqsVo<Object> filmReqsVo = new FilmReqsVo<>();
        List<FilmDetail> filmDetailList;
        if (params.getKw() == null)
            filmDetailList = filmServiceApi.getFilmsByCondition(params);
        else {
            String kw = params.getKw();
            try {
                Integer keyWord = Integer.parseInt(kw);
                filmDetailList = filmServiceApi.getFilmsByIntegerKeyWord(keyWord);
            } catch (Exception e) {
                filmDetailList = filmServiceApi.getFilmsByStringKeyWord(kw);
            }
        }
        filmReqsVo.setData(filmDetailList);
        return filmReqsVo;
    }

    @RequestMapping(value = "/film/films/{film}",method = RequestMethod.GET)
    public FilmReqsVo getFilmDetail(@PathParam("searchType") Integer searchType,@PathVariable("film") String film) {
        FilmReqsVo<Object> filmReqsVo = new FilmReqsVo<>();
        FilmDetailVo filmDetailVo;
        if (searchType == 0) {
            filmDetailVo = filmServiceApi.getFilmDetailById(film);
        }
        else {
            filmDetailVo = filmServiceApi.getFilmDetailByName(film);
        }
        filmReqsVo.setData(filmDetailVo);
        return filmReqsVo;
    }


}
