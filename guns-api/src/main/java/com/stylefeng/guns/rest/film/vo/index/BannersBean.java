package com.stylefeng.guns.rest.film.vo.index;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannersBean implements Serializable {
    private String bannerAddress;
    private String bannerId;
    private String bannerUrl;
}
