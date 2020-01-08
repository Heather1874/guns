package com.stylefeng.guns.rest.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.model.CinemaInfoVo;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeAreaDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBrandDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;

import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.model.FilmFieldVo;
import com.stylefeng.guns.rest.cinema.model.FilmInfoVo;
import com.stylefeng.guns.rest.cinema.model.HallInfoVo;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallFilmInfoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    //这个用于获取当前页所有条目
    Map conditionsMap;
    List<CinemaItem> cinemaItemList;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;
    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;
    @Autowired
    MtimeAreaDictTMapper mtimeAreaDictTMapper;
    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;


    MtimeFieldTMapper fieldMapper;
    @Autowired
    MtimeHallFilmInfoTMapper filmInfoTMapper;
    @Autowired
    MtimeHallDictTMapper hallDictMapper;

    /**
     * 获取播放场次
     *
     * @param cinemaId
     * @return
     */

    @Override
    public Map getFields(Integer cinemaId) {
        //返回map
        Map map = new HashMap();
        //先查出影院信息
        MtimeCinemaT cinema = mtimeCinemaTMapper.selectById(cinemaId);
        CinemaInfoVo cinemaInfo = new CinemaInfoVo(cinema.getAreaId(), cinema.getCinemaAddress(), cinema.getCinemaName(), cinema.getCinemaPhone(), cinema.getImgAddress());
        map.put("cinemaInfo", cinemaInfo);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("cinema_id", cinemaId);
        //根据影院id查出放映场次表
        List<MtimeFieldT> listField = fieldMapper.selectList(entityWrapper);
        EntityWrapper fielmEntityWrapper = new EntityWrapper<>();
        //把影院场次表中的影片id取出来 去除重复的
        HashSet hashSet = new HashSet();
        for (MtimeFieldT mtimeFieldT : listField) {
            Integer filmId1 = mtimeFieldT.getFilmId();
            hashSet.add(filmId1);
        }
        //根据filmId查出 影厅电影信息表
        fielmEntityWrapper.in("film_id", hashSet);
        List<MtimeHallFilmInfoT> listFilmInfo = filmInfoTMapper.selectList(fielmEntityWrapper);
        List<FilmInfoVo> filmInfoVoList = new ArrayList<>();
        //把查出来的信息封装到Bean中
        for (MtimeHallFilmInfoT filmInfo : listFilmInfo) {
            FilmInfoVo filmInfoVo = new FilmInfoVo();
            List<FilmFieldVo> filmFieldVos = new ArrayList<>();
            filmInfoVo.setActors(filmInfo.getActors());
            filmInfoVo.setFilmCats(filmInfo.getFilmCats());
            filmInfoVo.setFilmId(filmInfo.getFilmId());
            filmInfoVo.setFilmLength(filmInfo.getFilmLength());
            filmInfoVo.setFilmName(filmInfo.getFilmName());
            filmInfoVo.setFilmType(filmInfo.getFilmLanguage());
            filmInfoVo.setImgAddress(filmInfo.getImgAddress());
            //对比filed中的filmId一样放入bean中
            for (MtimeFieldT mtimeField : listField) {
                if (mtimeField.getFilmId() == filmInfo.getFilmId()) {
                    FilmFieldVo filmFieldVo = new FilmFieldVo();
                    filmFieldVo.setBeginTime(mtimeField.getBeginTime());
                    filmFieldVo.setEndTime(mtimeField.getEndTime());
                    filmFieldVo.setFieldId(mtimeField.getFilmId());
                    filmFieldVo.setHallName(mtimeField.getHallName());
                    filmFieldVo.setLanguage(filmInfo.getFilmLanguage());
                    filmFieldVo.setPrice(mtimeField.getPrice());
                    filmFieldVos.add(filmFieldVo);
                }
            }
            filmInfoVo.setFilmFields(filmFieldVos);
            filmInfoVoList.add(filmInfoVo);
        }
        map.put("filmList", filmInfoVoList);
        return map;
    }


    /**
     * 需要显示所有地区,影院,演播厅,
     * 刚刷新是默认显示所有的
     * 初步设想设置局部变量一次查找终生受益
     * 同时返回高亮(可以复用)
     *
     * @param map
     * @return
     */
    @Override
    public Map getConditions(Map map) {
        //获取当前页面所有数据并选中高亮
        Map parseParam = parseParam(map);
        Map resultMap = getCheckedItems(parseParam);
        return resultMap;
    }

    /**
     * 这个可以串参数,改造一下
     *
     * @param parseParam
     */
    private Map getCheckedItems(Map parseParam) {
        Map currentConditionMap = new HashMap();
        Integer areaId = (Integer) parseParam.get("areaId");
        Integer brandId = (Integer) parseParam.get("brandId");
        Integer halltypeId = (Integer) parseParam.get("halltypeId");
        //获取全部地区的List
        EntityWrapper<MtimeAreaDictT> mtimeAreaDictTEntityWrapper = new EntityWrapper<>();
        mtimeAreaDictTEntityWrapper.ne("UUID", -1);
        List<MtimeAreaDictT> mtimeAreaDictTS = mtimeAreaDictTMapper.selectList(mtimeAreaDictTEntityWrapper);
        List areaList = new ArrayList();
        //进行封装
        for (MtimeAreaDictT mtimeAreaDictT : mtimeAreaDictTS) {
            Map map = new HashMap();
            map.put("active", false);
            if (mtimeAreaDictT.getUuid() == areaId) {
                map.put("active", true);
            }
            map.put("areaId", mtimeAreaDictT.getUuid());
            map.put("areaName", mtimeAreaDictT.getShowName());
            areaList.add(map);
        }
        currentConditionMap.put("areaList", areaList);
        //获取全部影城品牌的List
        EntityWrapper<MtimeBrandDictT> mtimeBrandDictTEntityWrapper = new EntityWrapper<>();
        mtimeBrandDictTEntityWrapper.ne("UUID", -1);
        List<MtimeBrandDictT> mtimeBrandDictTS = mtimeBrandDictTMapper.selectList(mtimeBrandDictTEntityWrapper);
        List brandList = new ArrayList();
        for (MtimeBrandDictT mtimeBrandDictT : mtimeBrandDictTS) {
            Map map = new HashMap();
            map.put("active", false);
            if (mtimeBrandDictT.getUuid() == brandId) {
                map.put("active", true);
            }
            map.put("brandId", mtimeBrandDictT.getUuid());
            map.put("brandName", mtimeBrandDictT.getShowName());
            brandList.add(map);
        }
        currentConditionMap.put("brandList", brandList);
        //获取全部影厅的List
        EntityWrapper<MtimeHallDictT> mtimeHallDictTEntityWrapper = new EntityWrapper<>();
        mtimeHallDictTEntityWrapper.ne("UUID", -1);
        List<MtimeHallDictT> mtimeHallDictTS = mtimeHallDictTMapper.selectList(mtimeHallDictTEntityWrapper);
        List halltypeList = new ArrayList();
        for (MtimeHallDictT mtimeHallDictT : mtimeHallDictTS) {
            Map map = new HashMap();
            map.put("active", false);
            if (halltypeId == mtimeHallDictT.getUuid()) {
                map.put("active", true);
            }
            map.put("halltypeId", mtimeHallDictT.getUuid());
            map.put("halltypeName", mtimeHallDictT.getShowName());
            halltypeList.add(map);
        }
        currentConditionMap.put("halltypeList", halltypeList);
        //如何查询一次终生受益,
        // 所以查询语句和封装语句要进行分离  等下再改进
        conditionsMap = currentConditionMap;
        return currentConditionMap;
    }

    /**
     * 查询所有影院信息
     * 查询信息利用缓存,一次查找终生受益
     *
     * @param map
     * @return
     */

    @Override
    public List getCinemas(Map map) {
        Map parseParam = parseParam(map);
        //获取参数
        List<CinemaItem> cinemaItems = getCinemaItems(parseParam);

        List mapList = parseMapList(cinemaItems);

        return mapList;
    }

    private List parseMapList(List<CinemaItem> cinemaItems) {
        List resultList = new ArrayList();
        for (CinemaItem cinemaItem : cinemaItems) {
            Map map = new HashMap();
            map.put("cinemaAddress", cinemaItem.getCinemaAddress());
            map.put("cinemaName", cinemaItem.getCinemaName());
            map.put("minimumPrice", cinemaItem.getMinimumPrice());
            map.put("uuid", cinemaItem.getUuid());
            resultList.add(map);
        }
        return resultList;
    }

    private List<CinemaItem> getCinemaItems(Map parseParam) {
        Integer areaId = (Integer) parseParam.get("areaId");
        Integer brandId = (Integer) parseParam.get("brandId");
        Integer halltypeId = (Integer) parseParam.get("hallType");
        EntityWrapper<MtimeCinemaT> mtimeCinemaTEntityWrapper = new EntityWrapper<>();
        mtimeCinemaTEntityWrapper.ne("UUID", -1);
        //先查出所有影院再进行封装
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(mtimeCinemaTEntityWrapper);
        List<CinemaItem> allCinemaList = new ArrayList<>();
        for (MtimeCinemaT mtimeCinemaT : mtimeCinemaTS) {
            CinemaItem cinemaItem = new CinemaItem();
            cinemaItem.setUuid(mtimeCinemaT.getUuid());
            cinemaItem.setCinemaName(mtimeCinemaT.getCinemaName());
            cinemaItem.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
            cinemaItem.setMinimumPrice(mtimeCinemaT.getMinimumPrice());
            allCinemaList.add(cinemaItem);
        }
        //这里获取了所有影院列表
        cinemaItemList = allCinemaList;
        if (areaId == 99 && brandId == 99 && halltypeId == 99) {
            return allCinemaList;
        }
        //事实证明针对areaId和brandId,查询语句比较好用
        //这是aredId和brandId都不为99的情况  这是什么shit
        List<MtimeCinemaT> cinemaListByareaIdAndBrandId = null;
        if (areaId != 99 && brandId != 99) {
            EntityWrapper<MtimeCinemaT> cinemaTEntityWrapper = new EntityWrapper<>();
            cinemaTEntityWrapper.eq("brand_id", brandId).eq("area_id", areaId);
            cinemaListByareaIdAndBrandId = mtimeCinemaTMapper.selectList(cinemaTEntityWrapper);
        } else if (areaId != 99 && brandId == 99) {
            EntityWrapper<MtimeCinemaT> cinemaTEntityWrapper = new EntityWrapper<>();
            cinemaTEntityWrapper.eq("area_id", areaId);
            cinemaListByareaIdAndBrandId = mtimeCinemaTMapper.selectList(cinemaTEntityWrapper);
        } else if (areaId == 99 && brandId != 99) {
            EntityWrapper<MtimeCinemaT> cinemaTEntityWrapper = new EntityWrapper<>();
            cinemaTEntityWrapper.eq("brand_id", brandId);
            cinemaListByareaIdAndBrandId = mtimeCinemaTMapper.selectList(cinemaTEntityWrapper);
        }
        //拿到了areaId和brandId都符合要求的List
        List<CinemaItem> currentCinemaList = new ArrayList<>();
        if (halltypeId != 99) {
            for (MtimeCinemaT mtimeCinemaT : cinemaListByareaIdAndBrandId) {
                String hallIds = mtimeCinemaT.getHallIds();
                if (hallIds.contains(halltypeId.toString())) {
                    CinemaItem cinemaItem = new CinemaItem();
                    cinemaItem.setUuid(mtimeCinemaT.getUuid());
                    cinemaItem.setCinemaName(mtimeCinemaT.getCinemaName());
                    cinemaItem.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
                    cinemaItem.setMinimumPrice(mtimeCinemaT.getMinimumPrice());
                    currentCinemaList.add(cinemaItem);
                }
            }
        } else {
            for (MtimeCinemaT mtimeCinemaT : cinemaListByareaIdAndBrandId) {
                CinemaItem cinemaItem = new CinemaItem();
                cinemaItem.setUuid(mtimeCinemaT.getUuid());
                cinemaItem.setCinemaName(mtimeCinemaT.getCinemaName());
                cinemaItem.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
                cinemaItem.setMinimumPrice(mtimeCinemaT.getMinimumPrice());
                currentCinemaList.add(cinemaItem);
            }
        }
        //这里应该返回哪一个List
        return currentCinemaList;
    }


    /**
     * 这是个用于转换参数类型的憨憨方法
     *
     * @param map
     * @return
     */
    private Map parseParam(Map map) {
        Map paramMap = new HashMap();
        String brandID = (String) map.get("brandId");

        Integer brandId = Integer.parseInt(brandID);

        paramMap.put("brandId", brandId);

        String hallTypE = (String) map.get("hallType");

        Integer hallType = Integer.parseInt(hallTypE);

        paramMap.put("hallType", hallType);

        String areaID = (String) map.get("areaId");

        Integer areaId = Integer.parseInt(areaID);

        paramMap.put("areaId", areaId);

        String pageSizE = (String) map.get("pageSize");
        String nowPagE = (String) map.get("nowPage");
        //这两个参数是获取影院的api的   一般同时出现
        if (pageSizE != null && nowPagE != null) {
            Integer pageSize = Integer.parseInt(pageSizE);

            paramMap.put("pageSize", pageSize);

            Integer nowPage = Integer.parseInt(nowPagE);

            paramMap.put("nowPage", nowPage);
        }

        return paramMap;
    }

    /**
     * 获取场次详细信息接口
     *
     * @param cinemaId
     * @param fieldId
     * @return map
     */
    @Override
    public Map getFieldInfo(Integer cinemaId, Integer fieldId) {
        Map map = new HashMap();
        MtimeCinemaT cinema = mtimeCinemaTMapper.selectById(cinemaId);
        CinemaInfoVo cinemaInfo = new CinemaInfoVo(cinema.getAreaId(), cinema.getCinemaAddress(), cinema.getCinemaName(), cinema.getCinemaPhone(), cinema.getImgAddress());
        map.put("cinemaInfo", cinemaInfo);
        EntityWrapper<MtimeHallFilmInfoT> filmInfoWrapper = new EntityWrapper<>();
        List<MtimeHallFilmInfoT> hallFilmInfo = filmInfoTMapper.selectList(filmInfoWrapper);
        FilmInfoVo filmInfoVo = new FilmInfoVo();
        for (MtimeHallFilmInfoT filmInfo : hallFilmInfo) {
            filmInfoVo.setFilmId(filmInfo.getFilmId());
            filmInfoVo.setFilmName(filmInfo.getFilmName());
            filmInfoVo.setFilmType(filmInfo.getFilmLanguage());
            filmInfoVo.setImgAddress(filmInfo.getImgAddress());
            filmInfoVo.setFilmCats(filmInfo.getFilmCats());
            filmInfoVo.setFilmLength(filmInfo.getFilmLength());
        }
        map.put("filmINfo", filmInfoVo);
        MtimeFieldT mtimeField = fieldMapper.selectById(fieldId);
        HallInfoVo hallInfoVo = new HallInfoVo();
        hallInfoVo.setHallFieldId(fieldId);
        hallInfoVo.setHallName(mtimeField.getHallName());
        hallInfoVo.setPrice(mtimeField.getPrice());
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("UUID", mtimeField.getHallId());
        List<MtimeHallDictT> list = hallDictMapper.selectList(entityWrapper);
        for (MtimeHallDictT mtimeHallDictT : list) {
            hallInfoVo.setSeatFile(mtimeHallDictT.getSeatAddress());
        }
        //写死的数据，到时候跟订单一起查
        hallInfoVo.setSoldSeats("1,2,3,5,12");
        map.put("hallInfo", hallInfoVo);
        return map;
    }
}
