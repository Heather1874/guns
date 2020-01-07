package com.stylefeng.guns.rest.modular.student;


import com.stylefeng.guns.rest.common.persistence.dao.MtimeSourceDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeSourceDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stylefeng
 * @since 2020-01-07
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;

    @RequestMapping("name")
    @ResponseBody
    public String name(int id) {
        MtimeSourceDictT mtimeSourceDictT = mtimeSourceDictTMapper.selectById(id);
        return mtimeSourceDictT.getShowName();

    }
}

