package com.stylefeng.guns.rest.user;

import com.stylefeng.guns.rest.user.vo.RegisterReqVo;
import com.stylefeng.guns.rest.user.vo.UpdateUserVo;

public interface UserService {
    Boolean queryUserByName(String username);

    Boolean addUser(RegisterReqVo registerReqVo);

    UpdateUserVo updateUserInfo(UpdateUserVo updateUserVo);

    boolean queryUserByNamePassword(String userName, String password);
}
