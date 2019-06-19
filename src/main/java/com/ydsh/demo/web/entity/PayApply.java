/**
 * @filename:PayApply 2019-06-14 04:12:46
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.FieldStrategy.IGNORED;

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
public class PayApply implements Serializable {

	private static final long serialVersionUID = 1560499966248L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "deleteStatus" , value = "删除逻辑字段")
	private Integer deleteStatus;
	@ApiModelProperty(name = "toPurchaseNo" , value = "关联单号")
	private String toPurchaseNo;
	@ApiModelProperty(name = "payApplyNo" , value = "付款申请单号")
	private String payApplyNo;
	@ApiModelProperty(name = "payStatus" , value = "付款单状态")
	private String payStatus;
	@ApiModelProperty(name = "payType" , value = "付款类型")
	private String payType;
	@ApiModelProperty(name = "sellerNo" , value = "收款方编号")
	private String sellerNo;
	@ApiModelProperty(name = "sellerName" , value = "收款方名称")
	@NotNull(message = "收款方名称不允许为空")
	private String sellerName;
	@ApiModelProperty(name = "principal" , value = "负责人")
	private String principal;
	@ApiModelProperty(name = "contactWay" , value = "联系方式")
	private String contactWay;
	@ApiModelProperty(name = "bankType" , value = "银行类型")
	private String bankType;
	@ApiModelProperty(name = "accountName" , value = "账户姓名")
	private String accountName;
	@ApiModelProperty(name = "bankAccount" , value = "银行账号")
	private String bankAccount;
	@ApiModelProperty(name = "payAmount" , value = "申请付款金额")
	@NotNull(message = "申请付款金额不允许为空")
	private Long payAmount;
	@ApiModelProperty(name = "createId" , value = "提交人")
	private Long createId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "purchaseTime" , value = "采购时间")
	@TableField(fill= FieldFill.INSERT_UPDATE)
	private Date purchaseTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "提交时间")
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private Date createTime;
	@ApiModelProperty(name = "remarks" , value = "付款备注")
	private String remarks;
	@ApiModelProperty(name = "reviewStatus" , value = "审批状态")
	private String reviewStatus;
	@ApiModelProperty(name = "reviewId" , value = "审批人")
	private Long reviewId;
	@ApiModelProperty(name = "reviewRemarks" , value = "审核意见")
	private String reviewRemarks;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "beginTime" , value = "开始时间")
	@TableField(strategy = IGNORED)
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "endTime" , value = "结束时间")
	@TableField(strategy = IGNORED)
	private Date endTime;
}
