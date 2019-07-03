/**
 * @filename:Invoice 2019-06-14 04:12:45
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

	private static final long serialVersionUID = 1560499965399L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "invoiceNo" , value = "发票单号")
	private String invoiceNo;
	@ApiModelProperty(name = "whichDetail" , value = "对应的明细表 0对应订单表、1对应充值表")
	private Integer whichDetail;
	@ApiModelProperty(name = "invoiceKind" , value = "开票种类")
	@NotNull(message = "开票种类不允许为空")
	private String invoiceKind;
	@ApiModelProperty(name = "invoiceType" , value = "开票类型")
	@NotNull(message = "开票类型不允许为空")
	private String invoiceType;
	@ApiModelProperty(name = "invoiceAmount" , value = "开票金额")
	private Long invoiceAmount;
	@ApiModelProperty(name = "invoiceWay" , value = "开票方式")
	@NotNull(message = "开票方式不允许为空")
	private String invoiceWay;
	@ApiModelProperty(name = "invoiceStatus" , value = "发票状态")
	private String invoiceStatus;
	@ApiModelProperty(name = "clientNo" , value = "客户编号")
	private String clientNo;
	@ApiModelProperty(name = "clientName" , value = "客户名称")
	@NotNull(message = "客户名称不允许为空")
	private String clientName;
	@ApiModelProperty(name = "clientType" , value = "客户认证类型")
	private String clientType;
	@ApiModelProperty(name = "taxpayerNo" , value = "纳税人识别号")
	private String taxpayerNo;
	@ApiModelProperty(name = "phone" , value = "联系电话")
	@NotNull(message = "联系电话不允许为空")
	private String phone;
	@ApiModelProperty(name = "mail" , value = "邮箱")
	private String mail;
	@ApiModelProperty(name = "address" , value = "地址")
	@NotNull(message = "地址不允许为空")
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
	@TableField(fill= FieldFill.INSERT_UPDATE)
	private Date createTime;
	@ApiModelProperty(name = "examine" , value = "审批")
	private String examine;
	@ApiModelProperty(name = "examineOpinion" , value = "审批意见")
	private String examineOpinion;
	@ApiModelProperty(name = "expressCompany" , value = "快递公司")
	private String expressCompany;
	@ApiModelProperty(name = "expressNo" , value = "快递单号")
	private String expressNo;
	/**
	 * 新增的属性  不映射数据库字段
	 */
	@ApiModelProperty(name = "details" , value = "开票明细")
	@TableField(exist = false)
	@NotNull(message = "开票明细不允许为空")
	protected List<Detail> details;
	@ApiModelProperty(name = "ids" , value = "批量操作的id集合")
	@TableField(exist = false)
	protected List<Integer> ids;
//	@ApiModelProperty(name = "orders" , value = "开票金额明细")
//	@TableField(exist = false)
//	@NotNull(message = "开票金额明细不允许为空")
//	private List<InvoiceWithRecharge> recharges;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "beginTime" , value = "开始时间")
	@TableField(exist = false)
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "endTime" , value = "结束时间")
	@TableField(exist = false)
	private Date endTime;
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "handleType", value = "查询：0、修改：1、审核查看：2、标记已开票：3")
	private Integer queryType;
	@ApiModelProperty(name = "updateSign" , value = "作废为 abolish，审核值为 reviewInvoice，已开票值为 makeInvoice")
	@TableField(exist = false)
	private String updateSign;
}
