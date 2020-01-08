package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.stylefeng.guns.rest.film.FilmServiceApi;
import com.stylefeng.guns.rest.film.vo.NewBannerInfo;
import com.stylefeng.guns.rest.film.vo.NewIndexInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class FlimServiceApiImpl implements FilmServiceApi {

    @Autowired
    MtimeBannerTMapper bannerTMapper;

    /**
     * 获取首页信息
     * @return
     */
    @Override
    public Map getIndex() {
        HashMap<Object, Object> hashMap = new HashMap<>();

        // 查询 banners
        NewIndexInfo newIndexInfo = new NewIndexInfo();
        int isValid = 0; // 有效
        /*List<> bannerList = bannerTMapper.selectListByStatus(isValid);
        //newIndexInfo.getData().setBanners(bannerTMapper.selectListByStatus(isValid));
        hashMap.put("banners", bannerList);

        List<NewIndexInfo.DataBean.HotFilmsBean.FilmInfoBean>
        hashMap.put("soonFilms", );
        hashMap.put("hotFilms", );
        hashMap.put("boxRanking", );
        hashMap.put("expectRanking", );
        hashMap.put("top100", );*/
        return null;
    }
}
