package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCatDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeSourceDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeYearDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCatDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeSourceDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeYearDictT;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.vo.NewCatInfo;
import com.stylefeng.guns.rest.film.vo.NewSourceInfo;
import com.stylefeng.guns.rest.film.vo.NewYearInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 17:31
 */
@Component
@Service(interfaceClass = FilmServiceApi.class)
public class FilmServiceApiImpl implements FilmServiceApi {

    @Autowired
    MtimeCatDictTMapper mtimeCatDictTMapper;

    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;

    @Autowired
    MtimeYearDictTMapper mtimeYearDictTMapper;

    @Override
    public Map getFilmConditionList(FilmGetConditionListParam params) {
        Map map = new HashMap();
        //catInfo
        EntityWrapper<MtimeCatDictT> catDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeCatDictT> catDictTS = mtimeCatDictTMapper.selectList(catDictTEntityWrapper);
        List<NewCatInfo> catInfos = new ArrayList<>(catDictTS.size());
        for (MtimeCatDictT catDictT : catDictTS) {
            NewCatInfo catInfo = new NewCatInfo();
            catInfo.setCatName(catDictT.getShowName());
            catInfo.setCatId(catDictT.getUuid());
            if (catDictT.getUuid() == params.getCatId())
                catInfo.setActive(true);
            catInfos.add(catInfo);
        }
        map.put("catInfo", catInfos);

        //sourceInfo
        EntityWrapper<MtimeSourceDictT> sourceDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeSourceDictT> sourceDictTS = mtimeSourceDictTMapper.selectList(sourceDictTEntityWrapper);
        List<NewSourceInfo> sourceInfos = new ArrayList<>(sourceDictTS.size());
        for (MtimeSourceDictT sourceDictT : sourceDictTS) {
            NewSourceInfo sourceInfo = new NewSourceInfo();
            sourceInfo.setSourceName(sourceDictT.getShowName());
            sourceInfo.setSourceId(sourceDictT.getUuid());
            if (params.getSourceId() == sourceDictT.getUuid())
                sourceInfo.setActive(true);
            sourceInfos.add(sourceInfo);
        }
        map.put("sourceInfo",sourceInfos);
        //yearInfo
        EntityWrapper<MtimeYearDictT> yearDictTEntityWrapper = new EntityWrapper<>();
        List<MtimeYearDictT> yearDictTS = mtimeYearDictTMapper.selectList(yearDictTEntityWrapper);
        List<NewYearInfo> yearInfos = new ArrayList<>(yearDictTS.size());
        for (MtimeYearDictT yearDictT : yearDictTS) {
            NewYearInfo yearInfo = new NewYearInfo();
            yearInfo.setYearName(yearDictT.getShowName());
            yearInfo.setYearId(yearDictT.getUuid());
            if (params.getYearId() == yearDictT.getUuid())
                yearInfo.setActive(true);
        }
        map.put("yearInfo",yearInfos);
        return map;
    }
}
