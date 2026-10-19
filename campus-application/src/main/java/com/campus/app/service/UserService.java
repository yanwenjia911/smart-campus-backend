package com.campus.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.domain.dto.LoginDTO;
import com.campus.domain.entity.User;
import com.campus.domain.vo.LoginVO;
import com.campus.domain.vo.UserInfoVO;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录/绑定
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 根据 ID 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoVO getUserInfo(Long userId);

    /**
     * 根据 OpenId 查询用户
     *
     * @param openid 微信 OpenId
     * @return 用户实体
     */
    User getByOpenid(String openid);

    /**
     * 创建或更新用户（绑定学号）
     *
     * @param openid     微信 OpenId
     * @param studentId  学号
     * @param name       姓名（用于验证）
     * @return 用户实体
     */
    User bindOrCreate(String openid, String studentId, String name);
}