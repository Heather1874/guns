package com.stylefeng.guns.rest.cinemacontroller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.CinemaService;


import com.stylefeng.guns.rest.cinema.bean.CinemaItem;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylefeng.guns.rest.common.CinemaReqsVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class)
    CinemaService cinemaService;

    /**
     * 用于获取影院列表以及影厅列表
     * 集成了查找功能
     * <p>
     *
     *
     * @param map
     * @return
     */
    @RequestMapping("cinema/getCinemas")
    public CinemaReqsVo getCinemas(@RequestParam Map map) {
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        List cinemaItems = cinemaService.getCinemas(map);
        cinemaReqsVo.setData(cinemaItems);
        cinemaReqsVo.setStatus(0);
        return cinemaReqsVo;
    }


    @RequestMapping("/cinema/getFields")
    public CinemaReqsVo getFields(Integer cinemaId) {
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        Map fieldMap = cinemaService.getFields(cinemaId);
        return cinemaReqsVo;
    }

    /**
     * 获取影院首页  集成选中高亮
     *data:中有一个areaList,
     *      * 一个 brandList
     *      * 一个halltypeList
     * @param map
     * @return
     */
    @RequestMapping("cinema/getCondition")
    public CinemaReqsVo getCinemaCondition(@RequestParam Map map) {
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        Map resultMap = cinemaService.getConditions(map);
        cinemaReqsVo.setData(resultMap);
        return cinemaReqsVo;
    }
}
