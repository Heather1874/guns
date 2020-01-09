package com.stylefeng.guns.rest.user.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class RegisterReqVo implements Serializable {
    private static final long SerialVersionUID = 1000000L;
    @NotBlank(message = "用户名不能为空")
    @NotNull
    private String username;
    @NotBlank(message = "密码不能为空")
    @NotNull
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$" ,message = "密码格式不正确")
    private String password;
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "邮箱格式不正确")
    private String email;
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "请输入正确的手机号")
    private String mobile;
    private String address;
}
