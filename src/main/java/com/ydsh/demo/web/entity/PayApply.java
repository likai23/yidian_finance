/**
 * @filename:PayApply 2019-06-13 10:50:24
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 付款申请表实体类</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayApply implements Serializable {

	private static final long serialVersionUID = 1560394224396L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "payApplyNo" , value = "付款申请单号")
	private String payApplyNo;
	@ApiModelProperty(name = "payType" , value = "付款类型")
	private String payType;
	@ApiModelProperty(name = "sellerNo" , value = "付款方编号")
	private String sellerNo;
	@ApiModelProperty(name = "sellerName" , value = "付款方名称")
	private String sellerName;
	@ApiModelProperty(name = "relevanceNo" , value = "关联单号")
	private String relevanceNo;
	@ApiModelProperty(name = "payAmount" , value = "付款金额")
	private Long payAmount;
	@ApiModelProperty(name = "verifyStatus" , value = "状态")
	private String verifyStatus;
	@ApiModelProperty(name = "remarks" , value = "备注")
	private String remarks;
	@ApiModelProperty(name = "createId" , value = "提交人")
	private Long createId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "申请时间")
	private Date createTime;
	@ApiModelProperty(name = "reviewId" , value = "审核人")
	private Long reviewId;
}
