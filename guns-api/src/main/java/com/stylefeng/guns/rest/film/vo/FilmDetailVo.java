package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * Created by IntelliJ IDEA.
 * User: ZhouMingZhuang
 * Date: 2020/1/8
 * Time: 22:51
 */
@Data
public class FilmDetailVo implements Serializable {

    /**
     * totalBox : 309600
     * score : 9.7
     * filmEnName : Dying To Survive
     * info01 : 喜剧,剧情,短片
     * filmId : 2
     * info04 : {"actors":{"actors":[{"directorName":"徐峥","roleName":"演员1","imgAddress":"xuzheng.png"},{"directorName":"王传君","roleName":"演员2","imgAddress":"wangchuanjun.png"},{"directorName":"谭卓","roleName":"演员3","imgAddress":"tanzhuo.png"},{"directorName":"黄渤","roleName":"演员4","imgAddress":"huangbo.png"}],"director":{"directorName":"徐峥","roleName":"","imgAddress":"xuzheng.png"}},"biopgraphy":"一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药\u201c格列宁\u201d的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以\u201c药神\u201d的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......","imgVO":{"mainImg":"yaoshen1.jpeg","img03":"yaoshen4.png","img04":"yaoshen5.jpeg","img01":"yaoshen2.jpeg","img02":"yaoshen3.jpeg"},"filmId":"2"}
     * filmName : 我不是药神
     * imgAddress : 238e2dc36beae55a71cabfc14069fe78236351.jpg
     * info02 : 美国 / 117分钟
     * info03 : 2018-07-05 00:00:00美国上映
     * scoreNum : 225
     */
    private String totalBox;
    private String score;
    private String filmEnName;
    private String info01;
    private String filmId;
    private Object info04;
    private String filmName;
    private String imgAddress;
    private String info02;
    private String info03;
    private String scoreNum;

}
