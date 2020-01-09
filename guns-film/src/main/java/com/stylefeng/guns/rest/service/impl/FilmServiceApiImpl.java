package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCatDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeSourceDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeYearDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCatDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeSourceDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeYearDictT;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import com.stylefeng.guns.rest.film.param.FilmGetConditionListParam;
import com.stylefeng.guns.rest.film.param.FilmGetFilmsParam;
import com.stylefeng.guns.rest.film.vo.*;

import com.stylefeng.guns.rest.film.vo.NewCatInfo;
import com.stylefeng.guns.rest.film.vo.NewIndexInfo;
import com.stylefeng.guns.rest.film.vo.NewSourceInfo;
import com.stylefeng.guns.rest.film.vo.NewYearInfo;
import com.stylefeng.guns.rest.film.vo.index.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Override
    public FilmConditionVo getFilmConditionList(FilmGetConditionListParam params) {
        FilmConditionVo filmConditionVo = new FilmConditionVo();
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
        filmConditionVo.setCatInfo(catInfos);

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
        filmConditionVo.setSourceInfo(sourceInfos);
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
        filmConditionVo.setYearInfo(yearInfos);
        return filmConditionVo;
    }

    @Override
    public List<FilmDetail> getFilmsByCondition(FilmGetFilmsParam params) {
        EntityWrapper<MtimeFilmT> filmTEntityWrapper = new EntityWrapper<>();
        filmTEntityWrapper.eq("film_status",params.getShowType());
        filmTEntityWrapper.eq("film_cats",params.getCatId());
        filmTEntityWrapper.eq("film_source",params.getSourceId());
        filmTEntityWrapper.eq("film_date",params.getYearId());
        if (params.getSortId() == 1) {
            filmTEntityWrapper.orderBy("film_box_office");
        }
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(filmTEntityWrapper);

        return null;
    }

    @Autowired
    MtimeBannerTMapper bannerTMapper;

    /**
     * 获取首页信息
     * @return
     */
    @Override
    public NewIndexInfo getIndex() {
        NewIndexInfo newIndexInfo = new NewIndexInfo();
        int current = 1;
        int size = 8;

        // 查询 banners
        int isValid = 0;
        List<BannersBean> bannerList = bannerTMapper.selectListByStatus(isValid);
        newIndexInfo.setBanners(bannerList);

        // 查询 hotFilms
        int hotFilmStatus = 1;
        HotFilmsBean hotFilmsBean = new HotFilmsBean();
        List<FilmInfoBean> hotFilmInfoList = mtimeFilmTMapper.selectHotFilmInfoListByStatus(hotFilmStatus, (current - 1) * size, size);
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", hotFilmStatus);
        long total = mtimeFilmTMapper.selectCount(entityWrapper);
        hotFilmsBean.setFilmNum(total);
        hotFilmsBean.setFilmInfo(hotFilmInfoList);
        newIndexInfo.setHotFilms(hotFilmsBean);

        // 查询 boxRanking
        List<BoxRankingBean> boxRankingBeanList = mtimeFilmTMapper.selectBoxRandingList(hotFilmStatus, (current - 1) * size, size);
        newIndexInfo.setBoxRanking(boxRankingBeanList);

        // 查询 soonFilms
        int soonFilmStatus = 2;
        SoonFilmsBean soonFilmsBean = new SoonFilmsBean();
        List<FilmInfoBeanX> soonFilmInfoList = mtimeFilmTMapper.selectSoonFilmInfoListByStatus(soonFilmStatus, (current - 1) * size, size);
        EntityWrapper<MtimeFilmT> entityWrapper2 = new EntityWrapper<>();
        entityWrapper2.eq("film_status", soonFilmStatus);
        long total2 = mtimeFilmTMapper.selectCount(entityWrapper2);
        soonFilmsBean.setFilmNum(total2);
        soonFilmsBean.setFilmInfo(soonFilmInfoList);
        newIndexInfo.setSoonFilms(soonFilmsBean);

        // 查询 expectRanking
        List<ExpectRankingBean> expectRankingBeanList = mtimeFilmTMapper.selectExpectRankingList(soonFilmStatus, (current - 1) * size, size);
        newIndexInfo.setExpectRanking(expectRankingBeanList);

        // 查询 top100
        List<Top100Bean> top100BeanList = mtimeFilmTMapper.selectTop100List((current - 1) * size, size);
        newIndexInfo.setTop100(top100BeanList);

        return newIndexInfo;
    }
}
