/**
 * @filename:Invoice 2019-06-13 10:50:23
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
 * <p>说明： 发票管理表实体类</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1560394223771L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "invoiceNo" , value = "发票单号")
	private String invoiceNo;
	@ApiModelProperty(name = "invoiceKind" , value = "开票种类")
	private String invoiceKind;
	@ApiModelProperty(name = "invoiceType" , value = "开票类型")
	private String invoiceType;
	@ApiModelProperty(name = "invoiceAmount" , value = "开票金额")
	private Long invoiceAmount;
	@ApiModelProperty(name = "invoiceWay" , value = "开票方式")
	private String invoiceWay;
	@ApiModelProperty(name = "invoiceStatus" , value = "发票状态")
	private String invoiceStatus;
	@ApiModelProperty(name = "clientNo" , value = "客户编号")
	private String clientNo;
	@ApiModelProperty(name = "clientName" , value = "客户名称")
	private String clientName;
	@ApiModelProperty(name = "clientType" , value = "客户认证类型")
	private String clientType;
	@ApiModelProperty(name = "taxpayerNo" , value = "纳税人识别号")
	private String taxpayerNo;
	@ApiModelProperty(name = "phone" , value = "联系电话")
	private String phone;
	@ApiModelProperty(name = "mail" , value = "邮箱")
	private String mail;
	@ApiModelProperty(name = "address" , value = "地址")
	private String address;
	@ApiModelProperty(name = "openingBank" , value = "开户行 ")
	private String openingBank;
	@ApiModelProperty(name = "bankAccount" , value = "银行账号 ")
	private String bankAccount;
	@ApiModelProperty(name = "createId" , value = "提交人 ")
	private String createId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "提交时间 ")
	private Date createTime;
	@ApiModelProperty(name = "examine" , value = "审批")
	private String examine;
	@ApiModelProperty(name = "examineOpinion" , value = "审批意见")
	private String examineOpinion;
	@ApiModelProperty(name = "expressCompany" , value = "快递公司")
	private String expressCompany;
	@ApiModelProperty(name = "expressNo" , value = "快递单号")
	private String expressNo;
}
