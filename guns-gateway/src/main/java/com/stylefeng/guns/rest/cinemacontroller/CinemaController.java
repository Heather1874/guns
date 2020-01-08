package com.stylefeng.guns.rest.cinemacontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.common.CinemaReqsVo;
import com.stylefeng.guns.rest.common.resultenum.CinemaEnums;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class)
    CinemaService cinemaService;

    /**
     * 用于获取影院列表以及影厅列表
     * 集成了查找功能
     * @param map
     * @return
     */
    @RequestMapping("cinema/getCinemas")
    public CinemaReqsVo getCinemas(@RequestParam Map map){
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        Map resultMap = cinemaService.getCinemas(map);
        cinemaReqsVo.setData(resultMap);
        return cinemaReqsVo;
    }

}
