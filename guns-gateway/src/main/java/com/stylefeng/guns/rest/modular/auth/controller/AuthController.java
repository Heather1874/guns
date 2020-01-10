package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.BaseResqVo;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @Reference
    UserService userService;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVo/*ResponseEntity<?>*/ createAuthenticationToken(AuthRequest authRequest) {

        boolean validate = userService.queryUserByNamePassword(authRequest.getUserName(), authRequest.getPassword());
        //boolean validate = reqValidator.validate(authRequest);

        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);

            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("randomkey", randomKey);
            hashMap.put("token", token);

            ResponseVo responseVo = new ResponseVo();
            responseVo.setData(hashMap);
            responseVo.setMsg("成功");
            //return ResponseEntity.ok(new AuthResponse(token, randomKey));
            return responseVo;
        } else {
            throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}
