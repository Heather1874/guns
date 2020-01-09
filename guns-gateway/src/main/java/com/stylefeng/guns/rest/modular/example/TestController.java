/*
package com.stylefeng.guns.rest.modular.example;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.user.MtimeUserTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestController {
    @Reference(interfaceClass = MtimeUserTService.class)
    MtimeUserTService mtimeUserTService;

    @RequestMapping("name")
    @ResponseBody
    public String name(int id) {
        String nameById = mtimeUserTService.getNameById(id);

        return nameById;
    }
}
*/
