package com.stylefeng.guns.rest.usercontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.RegisterReqVo;
import com.stylefeng.guns.rest.user.vo.ResponseVo;
import com.stylefeng.guns.rest.user.vo.UpdateUserVo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    UserService userService;

    /**
     * 用户注册
     * @param registerReqVo
     * @return
     */
    @RequestMapping("register")
    public ResponseVo register(@RequestBody @Valid RegisterReqVo registerReqVo, BindingResult bindingResult){
        ResponseVo responseVo = new ResponseVo();
        if (bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                FieldError fieldError = (FieldError) objectError;
                stringBuilder.append(objectError.getDefaultMessage()+"/");
            }
            responseVo.setStatus(999);
            responseVo.setMsg(stringBuilder.toString());
            return responseVo;
        }

        if (!userService.queryUserByName(registerReqVo.getUsername())){
            responseVo.setStatus(1);
            responseVo.setMsg("用户已注册");
            return responseVo;
        }
        Boolean flag = userService.addUser(registerReqVo);
        if (flag){
            responseVo.setMsg("注册成功");
        }else {
            responseVo.setStatus(999);
            responseVo.setMsg("系统异常，请联系管理员");
        }
        return responseVo;
    }

    /**
     * 用户名校验
     * @param username
     * @return
     */
    @RequestMapping("check")
    public ResponseVo checkUserName(@RequestBody String username){
        ResponseVo responseVo = new ResponseVo();
        Boolean flag = userService.queryUserByName(username);
        if (flag){
            responseVo.setMsg("用户名不存在");
        }else {
            responseVo.setStatus(1);
            responseVo.setMsg("用户已注册");
        }
        return responseVo;
    }

    /**
     * 更新用户信息
     * @return
     */
    @RequestMapping("updateUserInfo")
    public ResponseVo updateUserInfo(@RequestBody UpdateUserVo updateUserVo){
        ResponseVo responseVo = new ResponseVo();
        UpdateUserVo updatedUser = userService.updateUserInfo(updateUserVo);
        responseVo.setData(updatedUser);
        return responseVo;
    }


}
