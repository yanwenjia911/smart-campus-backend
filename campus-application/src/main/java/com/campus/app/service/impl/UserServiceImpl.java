package com.campus.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.app.service.UserService;
import com.campus.common.exception.BusinessException;
//import com.campus.common.result.R;
import com.campus.domain.dto.LoginDTO;
import com.campus.domain.entity.User;
import com.campus.domain.mapper.UserMapper; // 需要先创建 Mapper
import com.campus.domain.vo.LoginVO;
import com.campus.domain.vo.UserInfoVO;
import com.campus.infra.security.JwtUtil;
import com.campus.infra.wechat.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private WeChatService weChatService;

    // @Override
    // public LoginVO login(LoginDTO loginDTO) {
    //     // 1. 通过 code 换取 openid
    //     String openid = weChatService.getOpenid(loginDTO.getCode());
    //     if (StrUtil.isBlank(openid)) {
    //         throw new BusinessException("微信登录失败，无效的登录凭证");
    //     }

    //     User user;
    //     // 2. 判断是否首次登录（未绑定学号）
    //     User existingUser = this.getByOpenid(openid);
    //     if (existingUser != null) {
    //         // 已存在，直接返回
    //         user = existingUser;
    //     } else {
    //         // 首次登录，需要绑定学号
    //         if (StrUtil.isAllBlank(loginDTO.getStudentId(), loginDTO.getName())) {
    //             throw new BusinessException("首次登录，请提供学号和姓名进行绑定");
    //         }
    //         // 验证学号姓名是否匹配（此处仅为示意，实际应调用教务系统接口或查询本地白名单）
    //         if (!validateStudentIdAndName(loginDTO.getStudentId(), loginDTO.getName())) {
    //             throw new BusinessException("学号或姓名验证失败");
    //         }
    //         // 创建新用户并绑定
    //         user = bindOrCreate(openid, loginDTO.getStudentId(), loginDTO.getName());
    //     }


    //     // 3. 生成 JWT Token
    //     String token = JwtUtil.generateToken(user.getId().toString());

    //     // 4. 返回登录信息
    //     return new LoginVO(token, user.getNickname(), user.getRole());
    // }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // ==========================================
        // 1. 获取 openid (离线模拟模式)
        // ==========================================
        /*
         * 【原来的真实代码，先注释掉】
         * String openid = weChatService.getOpenid(loginDTO.getCode());
         * if (StrUtil.isBlank(openid)) {
         *     throw new BusinessException("微信登录失败，无效的登录凭证");
         * }
         */
        
        // 【新增的模拟代码】强行指定一个固定的 openid 用于测试
        String openid = "mock_openid_test_student"; 

        User user;
        // ==========================================
        // 2. 判断是否首次登录（未绑定学号）
        // ==========================================
        User existingUser = this.getByOpenid(openid);
        if (existingUser != null) {
            // 已存在，直接返回
            user = existingUser;
        } else {
            // 首次登录，需要绑定学号
            if (StrUtil.isAllBlank(loginDTO.getStudentId(), loginDTO.getName())) {
                // 注意：前端发起请求时如果被这里拦截，会收到这个异常信息。
                // 你的前端 request.js 的 catch 里面需要根据这个信息来弹窗！
                throw new BusinessException("首次登录，请提供学号和姓名进行绑定");
            }
            // 验证学号姓名是否匹配（此处仅为示意，实际应调用教务系统接口或查询本地白名单）
            if (!validateStudentIdAndName(loginDTO.getStudentId(), loginDTO.getName())) {
                throw new BusinessException("学号或姓名验证失败");
            }
            // 创建新用户并绑定
            user = bindOrCreate(openid, loginDTO.getStudentId(), loginDTO.getName());
        }

        // ==========================================
        // 3. 生成 JWT Token
        // ==========================================
        String token = JwtUtil.generateToken(user.getId().toString());

        // ==========================================
        // 4. 返回登录信息
        // ==========================================
        return new LoginVO(token, user.getNickname(), user.getRole());
    }

    @Override
    public UserInfoVO getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return new UserInfoVO(user.getNickname(), user.getStudentId(), user.getRole());
    }

    @Override
    public User getByOpenid(String openid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenid, openid);
        return this.getOne(queryWrapper);
    }

    @Override
    public User bindOrCreate(String openid, String studentId, String name) {
        User user = new User();
        user.setOpenid(openid);
        user.setStudentId(studentId);
        user.setNickname(name); // 姓名暂作昵称
        user.setRole("STUDENT"); // 默认为学生角色，可扩展
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return user;
    }

     /* TODO: 实际项目中应对接教务系统或本地白名单验证 */
    private boolean validateStudentIdAndName(String studentId, String name) {
        // 模拟验证逻辑，实际应连接外部系统或查询预置数据
        System.out.println("验证学号: " + studentId + ", 姓名: " + name);
        return true; // 假设验证通过
    }
}