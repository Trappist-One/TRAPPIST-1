package com.t1.common.constant;

/**
 * 参考 errorCode.js
 */
public interface HttpErrorCode {
    // 操作太频繁，请勿重复请求
    String ERROR_CODE_000 = "000";

    // 授权已过期，请重新登录
    String ERROR_CODE_401 = "401";

    // 当前操作没有权限
    String ERROR_CODE_403 = "403";

    // 资源不存在
    String ERROR_CODE_404 = "404";

    //未绑定登录账号，请使用密码登录后绑定
    String ERROR_CODE_417 = "417";

    // 演示环境不能操作，如需了解联系t1
    String ERROR_CODE_423 = "423";

    // 用户名不存在或密码错误
    String ERROR_CODE_426 = "426";

    // 验证码错误,请重新输入
    String ERROR_CODE_428 = "428";

    // 请求过频繁
    String ERROR_CODE_429 = "429";

    // 演示环境，没有权限操作
    String ERROR_CODE_479 = "479";

    // 系统未知错误,请反馈给管理员
    String ERROR_CODE_DEFAULT = "default";

}
