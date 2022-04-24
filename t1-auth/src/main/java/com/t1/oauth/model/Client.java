package com.t1.oauth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.t1.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Bruce Lee(copy)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class Client extends SuperEntity {
   private static final long serialVersionUID = -8185413579135897885L;
   /**
    * 编号
    */
   private String clientId;

   /**
    * 应用名称
    */
   private String clientName;

   /**
    * 资源ids
    */
   private String resourceIds;

   /**
    * 密钥
    */
   private String clientSecret;

   /**
    * 密钥(明文)
    */
   private String clientSecretStr;

   /**
    * 域
    */
   private String scope = "all";

   /**
    * 授权模式
    */
   private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";

   /**
    * 重定向地址
    */
   private String webServerRedirectUri;
   /**
    * 授权
    */
   private String authorities;

   /**
    * 令牌时效
    */
   private Integer accessTokenValidity = 18000;
   /**
    * 刷新时效
    */
   private Integer refreshTokenValidity = 28800;

   /**
    * 附加信息
    */
   private String additionalInformation = "{}";
   /**
    * 自动放行
    */
   private String autoapprove;

   /**
    * 是否支持id_token
    */
   private String supportIdToken;
   /**
    * id_token有效时间(s)
    */
   private Integer idTokenValidity = 60;
}
