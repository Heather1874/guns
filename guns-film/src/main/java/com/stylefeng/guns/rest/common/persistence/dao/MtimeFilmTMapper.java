package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.film.vo.index.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2020-01-07
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {

    List<FilmInfoBean> selectHotFilmInfoListByStatus(@Param("filmStatus") int filmStatus, @Param("current") int current, @Param("size") int size);

    List<FilmInfoBeanX> selectSoonFilmInfoListByStatus(@Param("filmStatus") int filmStatus, @Param("current") int current, @Param("size") int size);

    List<BoxRankingBean> selectBoxRandingList(@Param("filmStatus") int filmStatus, @Param("current") int current, @Param("size") int size);

    List<ExpectRankingBean> selectExpectRankingList(@Param("filmStatus") int filmStatus, @Param("current") int current, @Param("size") int size);

    List<Top100Bean> selectTop100List(@Param("current") int current, @Param("size") int size);
}
