package com.stylefeng.guns.rest.common.resultenum;

public  enum CinemaEnums {

    CINEMA_SUCCESS(0,"成功"),

    BIZ_EXCEPTION(1,"影院信息查询失败"),

    SYS_EXCEPTION(999,"系统出现异常,请联系管理员")
    ;

    Integer status;

    String msg;

    CinemaEnums(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    CinemaEnums() {
    }
}
