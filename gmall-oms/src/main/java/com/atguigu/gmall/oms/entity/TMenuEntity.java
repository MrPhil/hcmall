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
 * @date 2020-03-15 12:35:40
 */
@ApiModel
@Data
@TableName("t_menu")
public class TMenuEntity implements Serializable {
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
	@ApiModelProperty(name = "name",value = "")
	private String name;
	/**
	 * 
	 */
	@ApiModelProperty(name = "price",value = "")
	private Double price;
	/**
	 * 
	 */
	@ApiModelProperty(name = "flavor",value = "")
	private String flavor;
	/**
	 * 
	 */
	@ApiModelProperty(name = "tid",value = "")
	private Integer tid;

}
