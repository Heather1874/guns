package com.stylefeng.guns.rest.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    MtimeCinemaTMapper cinemaMapper;

    /**
     * 查询所有影院信息
     * @param map
     * @return
     */
    @Override
    public Map getCinemas(Map map) {

        return null;
    }
}
