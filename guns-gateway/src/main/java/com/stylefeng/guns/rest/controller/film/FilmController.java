package com.stylefeng.guns.rest.controller.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.BaseResqVo;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.vo.NewCatInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @RequestMapping("/film/getConditionList")
    public BaseResqVo getFilmConditionListList(FilmGetConditionListParam params) {
        BaseResqVo<Object> baseResqVo = new BaseResqVo<>();
        Map map = filmServiceApi.getFilmConditionList(params);
        baseResqVo.setData(map);
        baseResqVo.setStatus(0);
        return baseResqVo;
    }

}
