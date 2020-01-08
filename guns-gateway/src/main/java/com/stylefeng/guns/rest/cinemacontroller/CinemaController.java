package com.stylefeng.guns.rest.cinemacontroller;


import org.springframework.web.bind.annotation.RequestMapping;

import com.stylefeng.guns.rest.common.CinemaReqsVo;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CinemaController {

    @RequestMapping("cinema/getCinemas")
    public CinemaReqsVo getCinemas(@RequestParam Map map){
        CinemaReqsVo cinemaReqsVo = new CinemaReqsVo();
        return cinemaReqsVo;
    }

}
