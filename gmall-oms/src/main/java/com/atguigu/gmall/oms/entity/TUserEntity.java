package com.atguigu.gmall.oms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author renzhonghao
 * @email teat@test.com
 * @date 2020-03-15 12:35:39
 */
@ApiModel
@Data
@TableName("t_user")
public class TUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "")
	private Integer id;
	/**
	 * 
	 */
	@ApiModelProperty(name = "username",value = "")
	private String username;
	/**
	 * 
	 */
	@ApiModelProperty(name = "password",value = "")
	private String password;
	/**
	 * 
	 */
	@ApiModelProperty(name = "nickname",value = "")
	private String nickname;
	/**
	 * 
	 */
	@ApiModelProperty(name = "gender",value = "")
	private String gender;
	/**
	 * 
	 */
	@ApiModelProperty(name = "telephone",value = "")
	private String telephone;
	/**
	 * 
	 */
	@ApiModelProperty(name = "registerdate",value = "")
	private Date registerdate;
	/**
	 * 
	 */
	@ApiModelProperty(name = "address",value = "")
	private String address;

}
