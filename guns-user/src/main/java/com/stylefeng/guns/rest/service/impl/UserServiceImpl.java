package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.RegisterReqVo;
import com.stylefeng.guns.rest.user.vo.UpdateUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    MtimeUserTMapper userTMapper;

    @Override
    public Boolean queryUserByName(String username) {
        EntityWrapper<MtimeUserT> userWrapper = new EntityWrapper<>();
        userWrapper.eq("user_name",username);
        Integer count = userTMapper.selectCount(userWrapper);
        return count == null || count <= 0;
    }

    @Override
    public Boolean addUser(RegisterReqVo registerReqVo) {
        MtimeUserT user = transferReqVoToUser(registerReqVo);
        Integer result = userTMapper.insert(user);
        if (result==1){
            return true;
        }
        return false;
    }

    @Override
    public UpdateUserVo updateUserInfo(UpdateUserVo updateUserVo) {
        MtimeUserT user = transferUpdateToUser(updateUserVo);
        userTMapper.updateById(user);
        MtimeUserT mtimeUserT = userTMapper.selectById(user.getUuid());
        UpdateUserVo updatedUser = transferUserToUpdate(updateUserVo,mtimeUserT);
        return updatedUser;
    }

    private UpdateUserVo transferUserToUpdate(UpdateUserVo updateUserVo, MtimeUserT user) {
        updateUserVo.setId(user.getUuid());
        updateUserVo.setUsername(user.getUserName());
        if (isNotNullOrEmpty(user.getNickName())){
            updateUserVo.setNickname(user.getNickName());
        }
        if (isNotNullOrEmpty(user.getEmail())){
            updateUserVo.setEmail(user.getEmail());
        }
        if (isNotNullOrEmpty(user.getUserPhone())){
            updateUserVo.setPhone(user.getUserPhone());
        }
        if (user.getUserSex()!=null){
            updateUserVo.setSex(user.getUserSex());
        }
        if (isNotNullOrEmpty(user.getBirthday())){
            updateUserVo.setBirthday(user.getBirthday());
        }
        if (user.getLifeState()!=null){
            updateUserVo.setLifeState(user.getLifeState());
        }
        if (isNotNullOrEmpty(user.getAddress())){
            updateUserVo.setAddress(user.getAddress());
        }
        if (isNotNullOrEmpty(user.getBiography())){
            updateUserVo.setBiography(user.getBiography());
        }
        if (isNotNullOrEmpty(user.getHeadUrl())){
            updateUserVo.setHeadAddress(user.getHeadUrl());
        }
        updateUserVo.setCreateTime(user.getBeginTime());
        updateUserVo.setUpdateTime(user.getUpdateTime());
        return updateUserVo;
    }

    private MtimeUserT transferUpdateToUser(UpdateUserVo updateUserVo) {
        MtimeUserT user = new MtimeUserT();
        user.setUuid(updateUserVo.getUuid());
        if (isNotNullOrEmpty(updateUserVo.getNickname())){
            user.setNickName(updateUserVo.getNickname());
        }
        if (isNotNullOrEmpty(updateUserVo.getEmail())){
            user.setEmail(updateUserVo.getEmail());
        }
        if (isNotNullOrEmpty(updateUserVo.getPhone())){
            user.setUserPhone(updateUserVo.getPhone());
        }
        if (updateUserVo.getSex()!=null){
            user.setUserSex(updateUserVo.getSex());
        }
        if (isNotNullOrEmpty(updateUserVo.getBirthday())){
            user.setBirthday(updateUserVo.getBirthday());
        }
        if (updateUserVo.getLifeState()!=null){
            user.setLifeState(updateUserVo.getLifeState());
        }
        if (isNotNullOrEmpty(updateUserVo.getBiography())){
            user.setBiography(updateUserVo.getBiography());
        }
        if (isNotNullOrEmpty(updateUserVo.getAddress())){
            user.setAddress(updateUserVo.getAddress());
        }
        if (isNotNullOrEmpty(updateUserVo.getHeadAddress())){
            user.setHeadUrl(updateUserVo.getHeadAddress());
        }
        user.setUpdateTime(new Date());
        return user;
    }

    private boolean isNotNullOrEmpty(String nickname) {
        return nickname != null && !"".equals(nickname);
    }

    private MtimeUserT transferReqVoToUser(RegisterReqVo registerReqVo) {
        String password = MD5Util.encrypt(registerReqVo.getPassword());
        MtimeUserT user = new MtimeUserT();
        user.setUserName(registerReqVo.getUsername());
        user.setUserPwd(password);
        if (registerReqVo.getEmail()!=null&&!"".equals(registerReqVo.getEmail())){
            user.setEmail(registerReqVo.getEmail());
        }
        if (registerReqVo.getMobile()!=null&&!"".equals(registerReqVo.getMobile())){
            user.setUserPhone(registerReqVo.getMobile());
        }
        if (registerReqVo.getAddress()!=null&&!"".equals(registerReqVo.getAddress())){
            user.setAddress(registerReqVo.getAddress());
        }
        user.setBeginTime(new Date());
        user.setUpdateTime(user.getBeginTime());
        return user;
    }

    @Override
    public boolean queryUserByNamePassword(String userName, String password) {
        EntityWrapper<MtimeUserT> userTEntityWrapper = new EntityWrapper<>();
        userTEntityWrapper.eq("user_name", userName);
        userTEntityWrapper.eq("user_pwd", MD5Util.encrypt(password));
        Integer count = userTMapper.selectCount(userTEntityWrapper);
        return count == 1;
    }
}
