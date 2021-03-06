package com.t1.oauth.service;

import com.t1.common.model.R;

/**
 * @author Bruce Lee(copy)
 * @date 2018/12/10
 * <p>
 */
public interface IValidateCodeService {
    /**
     * 保存图形验证码
     * @param deviceId 前端唯一标识
     * @param imageCode 验证码
     */
    void saveImageCode(String deviceId, String imageCode);

    R sendSmsCode(String mobile);

    /**
     * 获取验证码
     * @param deviceId 前端唯一标识/手机号
     */
    String getCode(String deviceId);

    /**
     * 删除验证码
     * @param deviceId 前端唯一标识/手机号
     */
    void remove(String deviceId);

    /**
     * 验证验证码
     */
    void validate(String deviceId, String validCode);
}
