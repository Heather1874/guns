package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
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

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    MtimeFilmInfoTMapper mtimeFilmInfoTMapper;

    @Autowired
    MtimeFilmActorTMapper mtimeFilmActorTMapper;

    @Autowired
    MtimeActorTMapper mtimeActorTMapper;

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
            yearInfos.add(yearInfo);
        }
        filmConditionVo.setYearInfo(yearInfos);
        return filmConditionVo;
    }

    @Override
    public List<FilmDetail> getFilmsByCondition(FilmGetFilmsParam params) {
        EntityWrapper<MtimeFilmT> filmTEntityWrapper = new EntityWrapper<>();
        filmTEntityWrapper.eq("film_status", params.getShowType());
        if (params.getCatId() != 99)
            filmTEntityWrapper.like("film_cats", "#" + params.getCatId() + "#");
        if (params.getSourceId() != 99)
            filmTEntityWrapper.eq("film_source", params.getSourceId());
        if (params.getYearId() != 99)
            filmTEntityWrapper.eq("film_date", params.getYearId());
        if (params.getSortId() == 1) {
            filmTEntityWrapper.orderBy("film_box_office");
        }
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(filmTEntityWrapper);
        return getFilmDetailList(mtimeFilmTS);
    }


    @Override
    public List<FilmDetail> getFilmsByIntegerKeyWord(Integer keyWord) {
        EntityWrapper<MtimeFilmT> filmTEntityWrapper = new EntityWrapper<>();
        filmTEntityWrapper.eq("UUID", keyWord);
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(filmTEntityWrapper);
        return getFilmDetailList(mtimeFilmTS);
    }

    @Override
    public List<FilmDetail> getFilmsByStringKeyWord(String kw) {
        EntityWrapper<MtimeFilmT> filmTEntityWrapper = new EntityWrapper<>();
        filmTEntityWrapper.like("film_name", kw);
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(filmTEntityWrapper);
        return getFilmDetailList(mtimeFilmTS);
    }

    @Override
    public FilmDetailVo getFilmDetailById(String filmId) {
        EntityWrapper<MtimeFilmT> filmTEntityWrapper = new EntityWrapper<>();
        filmTEntityWrapper.eq("UUID", filmId);
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(filmTEntityWrapper);
        MtimeFilmT mtimeFilmT = null;
        if (mtimeFilmTS != null && mtimeFilmTS.size() > 0)
            mtimeFilmT = mtimeFilmTS.get(0);
        else return null;
        return getFilmDetailVo(mtimeFilmT);
    }

    @Override
    public FilmDetailVo getFilmDetailByName(String filmName) {
        EntityWrapper<MtimeFilmT> filmTEntityWrapper = new EntityWrapper<>();
        filmTEntityWrapper.in("film_name", filmName);
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(filmTEntityWrapper);
        MtimeFilmT mtimeFilmT = new MtimeFilmT();
        if (mtimeFilmTS != null && mtimeFilmTS.size() > 0)
            mtimeFilmT = mtimeFilmTS.get(0);
        else
            return null;
        return getFilmDetailVo(mtimeFilmT);
    }

    private FilmDetailVo getFilmDetailVo(MtimeFilmT mtimeFilmT) {
        /*EntityWrapper<MtimeFilmInfoT> filmInfoTEntityWrapper = new EntityWrapper<>();
        filmInfoTEntityWrapper.eq("film_id", mtimeFilmT.getUuid());
        List<MtimeFilmInfoT> mtimeFilmInfoTS = mtimeFilmInfoTMapper.selectList(filmInfoTEntityWrapper);
        MtimeFilmInfoT mtimeFilmInfoT = null;
        if (mtimeFilmInfoTS != null && mtimeFilmInfoTS.size() > 0)
            mtimeFilmInfoT = mtimeFilmInfoTS.get(0);*/
        MtimeFilmInfoT mtimeFilmInfoT = mtimeFilmInfoTMapper.selectById(mtimeFilmT.getUuid());

        EntityWrapper<MtimeFilmActorT> filmActorTEntityWrapper = new EntityWrapper<>();
        filmActorTEntityWrapper.eq("film_id", mtimeFilmT.getUuid());
        List<MtimeFilmActorT> mtimeFilmActorTS = mtimeFilmActorTMapper.selectList(filmActorTEntityWrapper);

        //info01
        String filmCats = mtimeFilmT.getFilmCats();
        String[] filmCatList = filmCats.split("#");
        StringBuffer info01StringBuffer = new StringBuffer();
        EntityWrapper<MtimeCatDictT> catDictTEntityWrapper = new EntityWrapper<>();
        catDictTEntityWrapper.in("UUID", filmCatList);
        List<MtimeCatDictT> mtimeCatDictTS = mtimeCatDictTMapper.selectList(catDictTEntityWrapper);
        for (MtimeCatDictT mtimeCatDictT : mtimeCatDictTS) {
            info01StringBuffer.append(",").append(mtimeCatDictT.getShowName());
        }
        info01StringBuffer.deleteCharAt(0);
        String info01 = info01StringBuffer.toString();

        //info02
        MtimeSourceDictT mtimeSourceDictT = mtimeSourceDictTMapper.selectById(mtimeFilmT.getFilmSource());
        String info02 = "";
        info02 += mtimeSourceDictT.getShowName() + "/" + mtimeFilmInfoT.getFilmLength();

        //info03
        String info03 = mtimeFilmT.getFilmTime().toString();
        info03 += mtimeSourceDictT.getShowName() + "上映";

        Map info04 = new HashMap();
        Map actors = new HashMap();
        List<ActorVo> actorVoList = new ArrayList<>(mtimeFilmActorTS.size());
        if (mtimeFilmActorTS != null && mtimeFilmActorTS.size() > 0) {
            for (MtimeFilmActorT mtimeFilmActorT : mtimeFilmActorTS) {
                EntityWrapper<MtimeActorT> actorTEntityWrapper = new EntityWrapper<>();
                actorTEntityWrapper.eq("UUID", mtimeFilmActorT.getActorId());
                // 如果报错就是这了
                MtimeActorT mtimeActorT = mtimeActorTMapper.selectById(mtimeFilmActorT.getActorId());
                ActorVo actorVo = new ActorVo();
                actorVo.setRoleName(mtimeFilmActorT.getRoleName());
                actorVo.setImgAddress(mtimeActorT.getActorImg().replace("actors/", ""));
                actorVo.setDirectorName(mtimeActorT.getActorName());
                actorVoList.add(actorVo);
                if (mtimeFilmInfoT.getDirectorId() == mtimeFilmActorT.getActorId()) {
                    ActorVo director = new ActorVo();
                    director.setImgAddress(mtimeActorT.getActorImg().replace("actors/", ""));
                    director.setDirectorName(mtimeActorT.getActorName());
                    actors.put("director", director);
                }
            }
            actors.put("actors", actorVoList);
        }
        if (mtimeFilmInfoT.getFilmImgs() != null) {
            String imgs = mtimeFilmInfoT.getFilmImgs();
            String[] imgList = imgs.split(",");
            Map imgVO = new HashMap();
            for (int i = 0; i < imgList.length; i++) {
                String img = imgList[i];
                img.replace("films/", "");
                if (i != 0) {
                    imgVO.put("img0" + i, img);
                } else {
                    imgVO.put("mainImg", img);
                }
            }
            info04.put("imgVO", imgVO);
        }
        info04.put("actors", actors);
        info04.put("biopgraphy", mtimeFilmInfoT.getBiography());
        info04.put("filmId", mtimeFilmInfoT.getFilmId());
        // imgVO以后再来封装
        FilmDetailVo filmDetailVo = new FilmDetailVo();
        filmDetailVo.setFilmEnName(mtimeFilmInfoT.getFilmEnName());
        filmDetailVo.setFilmId(mtimeFilmT.getUuid().toString());
        filmDetailVo.setFilmName(mtimeFilmT.getFilmName());
        filmDetailVo.setImgAddress(mtimeFilmT.getImgAddress());

        filmDetailVo.setInfo01(info01);
        filmDetailVo.setInfo02(info02);
        filmDetailVo.setInfo03(info03);
        filmDetailVo.setInfo04(info04);
        filmDetailVo.setScore(mtimeFilmInfoT.getFilmScore());
        filmDetailVo.setScoreNum(mtimeFilmInfoT.getFilmScoreNum().toString());
        filmDetailVo.setTotalBox(mtimeFilmT.getFilmBoxOffice().toString());

        return filmDetailVo;
    }


    private List<FilmDetail> getFilmDetailList(List<MtimeFilmT> mtimeFilmTS) {
        if (mtimeFilmTS == null || mtimeFilmTS.size() == 0)
            return null;
        List<FilmDetail> filmDetailList = new ArrayList<>(mtimeFilmTS.size());
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            EntityWrapper<MtimeFilmInfoT> filmInfoTEntityWrapper = new EntityWrapper<>();
            filmInfoTEntityWrapper.eq("film_id", mtimeFilmT.getUuid());
            List<MtimeFilmInfoT> mtimeFilmInfoTS = mtimeFilmInfoTMapper.selectList(filmInfoTEntityWrapper);
            MtimeFilmInfoT mtimeFilmInfoT = new MtimeFilmInfoT();
            if (mtimeFilmInfoTS != null && mtimeFilmInfoTS.size() > 0)
                mtimeFilmInfoT = mtimeFilmInfoTS.get(0);

            FilmDetail filmDetail = new FilmDetail();
            filmDetail.setBoxNum(mtimeFilmT.getFilmBoxOffice());
            filmDetail.setExpectNum(mtimeFilmT.getFilmPresalenum());
            filmDetail.setFilmCats(mtimeFilmT.getFilmCats());
            filmDetail.setFilmId(mtimeFilmT.getUuid().toString());
            filmDetail.setFilmLength(mtimeFilmInfoT.getFilmLength().toString());
            filmDetail.setFilmName(mtimeFilmT.getFilmName());
            filmDetail.setFilmScore(mtimeFilmInfoT.getFilmScore());
            filmDetail.setFilmType(mtimeFilmT.getFilmType());
            filmDetail.setImgAddress(mtimeFilmT.getImgAddress());
            filmDetail.setScore(mtimeFilmT.getFilmScore());
            filmDetail.setShowTime(mtimeFilmT.getFilmTime().toString());

            filmDetailList.add(filmDetail);
        }
        return filmDetailList;
    }


    @Autowired
    MtimeBannerTMapper bannerTMapper;

    /**
     * 获取首页信息
     *
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
