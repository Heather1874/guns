package com.stylefeng.guns.rest.filmcontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.common.BaseResqVo;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IndexController {
    @Reference
    FilmServiceApi filmServiceApi;

    /**
     * 获取首页信息
     * @return
     */
    @RequestMapping("/film/getIndex")
    public BaseResqVo<Object> getIndex() {

        Map map = filmServiceApi.getIndex();

        BaseResqVo<Object> baseResqVo = new BaseResqVo<>();
        //baseResqVo.setData();
        return baseResqVo;
    }
}
