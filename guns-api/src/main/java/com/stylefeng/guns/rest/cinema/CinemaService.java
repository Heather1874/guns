package com.stylefeng.guns.rest.cinema;

import java.util.Map;

public interface CinemaService {

    Map getFields(Integer cinemaId);

    Map getFieldInfo(Integer cinemaId, Integer fieldId);
}
