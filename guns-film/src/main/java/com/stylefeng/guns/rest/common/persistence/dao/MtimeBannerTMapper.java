package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.film.vo.NewBannerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * banner信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2020-01-07
 */
public interface MtimeBannerTMapper extends BaseMapper<MtimeBannerT> {

    List<NewBannerInfo> selectListByStatus(@Param("isValid") int isValid);
}
