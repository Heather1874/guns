package com.stylefeng.guns.rest.controller.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.BaseResqVo;
import com.stylefeng.guns.rest.common.FilmReqsVo;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.param.FilmGetFilmsParam;
import com.stylefeng.guns.rest.film.vo.FilmConditionVo;
import com.stylefeng.guns.rest.film.vo.FilmDetail;
import com.stylefeng.guns.rest.film.vo.NewIndexInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取首页信息
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

    @RequestMapping("/film/getFilms")
    public FilmReqsVo getFilms(FilmGetFilmsParam params) {
        FilmReqsVo<Object> filmReqsVo = new FilmReqsVo<>();
        List<FilmDetail> filmDetailList = filmServiceApi.getFilmsByCondition(params);
        filmReqsVo.setData(filmDetailList);
        return filmReqsVo;
    }

}
