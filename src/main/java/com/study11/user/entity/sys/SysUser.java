package com.study11.user.entity.sys;



import com.study.common.core.mybaties.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "", description = "")
public  class  SysUser  extends  BaseEntity<String>{


private static final long serialVersionUID=1L;



/**
* 用户信息表
*/
@ApiModelProperty(value = "用户信息表")
private   String  id;

/**
* 用户名
*/
@ApiModelProperty(value = "用户名")
private   String  username;

/**
* 用户编 码
*/
@ApiModelProperty(value = "用户编 码")
private   String  userCode;

/**
* 密码
*/
@ApiModelProperty(value = "密码")
private   String  password;

/**
* 加密盐值
*/
@ApiModelProperty(value = "加密盐值")
private   String  salt;

}
