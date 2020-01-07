package com.stylefeng.guns.rest.service;

import com.stylefeng.guns.rest.common.persistence.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    UserMapper userMapper;
}
