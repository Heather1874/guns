package com.stylefeng.guns.rest.cinemacontroller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.cinema.CinemaService;

import org.springframework.web.bind.annotation.RequestMapping;

import com.stylefeng.guns.rest.common.CinemaReqsVo;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class)
    private CinemaService cinemaService;

    @RequestMapping("cinema/getCinemas")
    public CinemaReqsVo getCinemas(@RequestParam Map map) {
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        return cinemaReqsVo;
    }

    /**
     * 获取影院场次
     * @param cinemaId
     * @return
     */
    @RequestMapping("/cinema/getFields")
    public CinemaReqsVo getFields(Integer cinemaId) {
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
       Map fieldMap = cinemaService.getFields(cinemaId);

       cinemaReqsVo.setData(fieldMap);
       return cinemaReqsVo;
    }

    /**
     * 获取场次详细信息接口
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @RequestMapping(value = "/cinema/getFieldInfo",method = RequestMethod.POST)
    public CinemaReqsVo getFieldInfo(Integer cinemaId,Integer fieldId){
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        Map fieldInfoMap = cinemaService.getFieldInfo(cinemaId,fieldId);
        cinemaReqsVo.setData(fieldInfoMap);
        return cinemaReqsVo;


}
}
