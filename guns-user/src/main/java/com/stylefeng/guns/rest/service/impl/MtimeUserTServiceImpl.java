package com.stylefeng.guns.rest.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.user.MtimeUserTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = MtimeUserTService.class)
public class MtimeUserTServiceImpl implements MtimeUserTService {
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Override
    public String getNameById(int id) {
        MtimeUserT mtimeUserT = mtimeUserTMapper.selectById(id);
        return mtimeUserT.getUserName();
    }
}
