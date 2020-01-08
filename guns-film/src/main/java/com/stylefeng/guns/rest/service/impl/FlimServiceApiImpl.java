package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:31
 */
@Component
@Service(interfaceClass = FilmServiceApi.class)
public class FlimServiceApiImpl implements FilmServiceApi {
}
