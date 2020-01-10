package com.stylefeng.guns.rest.cinema.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CinemaItem {
    String cinemaAddress;

    String cinemaName;

    Integer minimumPrice;

    Integer uuid;

}
