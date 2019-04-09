package com.ccnu.bbs.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ccnu.bbs.VO.ResultVO;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.service.Impl.UserServiceImpl;
import com.ccnu.bbs.service.UserService;
import com.ccnu.bbs.utils.KeyUtil;
import com.ccnu.bbs.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public ResultVO<String> WeChatLogin(@RequestParam String code){
        String sessionId;
        try{
            // 0.如果code为空，返回错误信息
            if (code==null||code.isEmpty()){
                return ResultVOUtil.error(ResultEnum.CODE_ERROR.getCode(), ResultEnum.CODE_ERROR.getMessage());
            }
            // 1.向微信服务器获取openid和sessionKey
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            if (session==null){
                return ResultVOUtil.error(ResultEnum.SESSION_ERROR.getCode(), ResultEnum.SESSION_ERROR.getMessage());
            }
            String sessionKey = session.getSessionKey();
            String openId = session.getOpenid();
            log.info("【sessionKey】: {}",sessionKey);
            log.info("【openid】: {}",openId);
            // 3.根据openid查询用户是否存在
            User user = userService.findUser(session.getOpenid());
            // 4.若用户不存在则创建用户
            if (user == null){
                user = userService.createUser(session.getOpenid());
            }
            // 5.生成加密的sessionId;
            sessionId = KeyUtil.getSessionId(sessionKey+openId+System.currentTimeMillis());
            // 6.存入redis中
            redisTemplate.opsForValue().set(sessionId, session,30, TimeUnit.MINUTES);
        }catch (WxErrorException e){
            log.error(e.getMessage(), e);
            return ResultVOUtil.error(e.getError().getErrorCode(), e.getError().getErrorMsg());
        }
        return ResultVOUtil.success(sessionId);
    }

}
