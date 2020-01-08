package com.stylefeng.guns.rest.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.cinema.model.CinemaInfoVo;
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
    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;
    @Autowired
    MtimeFieldTMapper fieldMapper;
    @Autowired
    MtimeHallFilmInfoTMapper filmInfoTMapper;
    @Autowired
    MtimeHallDictTMapper hallDictMapper;
    /**
     * 获取播放场次
     * @param cinemaId
     * @return
     */

    @Override
    public Map getFields(Integer cinemaId) {
        //返回map
        Map map = new HashMap();
        //先查出影院信息
        MtimeCinemaT cinema = mtimeCinemaTMapper.selectById(cinemaId);
        CinemaInfoVo cinemaInfo = new CinemaInfoVo(cinema.getAreaId(),cinema.getCinemaAddress(),cinema.getCinemaName(),cinema.getCinemaPhone(),cinema.getImgAddress());
        map.put("cinemaInfo",cinemaInfo);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("cinema_id",cinemaId);
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
        fielmEntityWrapper.in("film_id",hashSet);
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
                if (mtimeField.getFilmId()==filmInfo.getFilmId()){
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
        map.put("filmList",filmInfoVoList);
        return map;
    }

    /**
     * 获取场次详细信息接口
     * @param cinemaId
     * @param fieldId
     * @return map
     */
    @Override
    public Map getFieldInfo(Integer cinemaId, Integer fieldId) {
        Map map = new HashMap();
        MtimeCinemaT cinema = mtimeCinemaTMapper.selectById(cinemaId);
        CinemaInfoVo cinemaInfo = new CinemaInfoVo(cinema.getAreaId(),cinema.getCinemaAddress(),cinema.getCinemaName(),cinema.getCinemaPhone(),cinema.getImgAddress());
        map.put("cinemaInfo",cinemaInfo);
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
        map.put("filmINfo",filmInfoVo);
        MtimeFieldT mtimeField = fieldMapper.selectById(fieldId);
        HallInfoVo hallInfoVo = new HallInfoVo();
        hallInfoVo.setHallFieldId(fieldId);
        hallInfoVo.setHallName(mtimeField.getHallName());
        hallInfoVo.setPrice(mtimeField.getPrice());
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("UUID",mtimeField.getHallId());
        List<MtimeHallDictT> list = hallDictMapper.selectList(entityWrapper);
        for (MtimeHallDictT mtimeHallDictT : list) {
            hallInfoVo.setSeatFile(mtimeHallDictT.getSeatAddress());
        }
        //写死的数据，到时候跟订单一起查
        hallInfoVo.setSoldSeats("1,2,3,5,12");
        map.put("hallInfo",hallInfoVo);
        return map;
    }
}
