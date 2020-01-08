package com.stylefeng.guns;

import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


public class CinemaTest extends GunsRestApplicationTests {

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;
    @Test
    public void test(){
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(1);
        System.out.println(mtimeCinemaT);
    }
}
