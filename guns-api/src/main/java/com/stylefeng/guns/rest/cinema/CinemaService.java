package com.stylefeng.guns.rest.cinema;

import java.util.List;
import java.util.Map;

public interface CinemaService {

    List getCinemas(Map map);

    Map getFields(Integer cinemaId);

    Map getConditions(Map map);
}
