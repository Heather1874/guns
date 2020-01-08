package com.stylefeng.guns.rest.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UpdateUserVo implements Serializable {
    private static final long SerialVersionUID = 1000001L;

    private Integer uuid;
    private Integer id;
    private String nickname;
    private String username;
    private String email;
    private String phone;
    private Integer sex;
    private String birthday;
    private Integer lifeState;
    private String biography;
    private String address;
    private Date createTime;
    private Date updateTime;
    private String headAddress;
}
