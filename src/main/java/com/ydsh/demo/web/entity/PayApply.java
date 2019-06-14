/**
 * @filename:PayApply 2019-06-14 02:36:07
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PayApply implements Serializable {

	private static final long serialVersionUID = 1560494167370L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@TableLogic  //逻辑删除注解
	@JsonIgnore  //忽略
	@TableField(fill = FieldFill.INSERT)  //新增的时候自动插入，MetaObjectHandlerConfig配置文件
	@ApiModelProperty(name = "deleteStatus" , value = "逻辑删除字段")
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
	private Long payAmount;
	@ApiModelProperty(name = "createId" , value = "提交人")
	private Long createId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "purchaseTime" , value = "采购时间")
	private Date purchaseTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "提交时间")
	private Date createTime;
	@ApiModelProperty(name = "remarks" , value = "付款备注")
	private String remarks;
	@ApiModelProperty(name = "reviewStatus" , value = "审批状态")
	private String reviewStatus;
	@ApiModelProperty(name = "reviewId" , value = "审批人")
	private Long reviewId;
	@ApiModelProperty(name = "reviewRemarks" , value = "审核意见")
	private String reviewRemarks;

}
