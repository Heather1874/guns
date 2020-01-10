package com.stylefeng.guns.rest.film.vo;

import com.stylefeng.guns.rest.film.vo.index.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NewIndexInfo implements Serializable {
    private List<BannersBean> banners;

    private HotFilmsBean hotFilms;

    private SoonFilmsBean soonFilms;

    private List<BoxRankingBean> boxRanking;

    private List<ExpectRankingBean> expectRanking;

    private List<Top100Bean> top100;
}


