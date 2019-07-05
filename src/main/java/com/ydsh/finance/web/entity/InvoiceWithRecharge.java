/**
 * @filename:InvoiceWithRecharge 2019-07-01 03:21:04
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd.
 * All right reserved.
 */
package com.ydsh.finance.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>代码自动生成，请勿修改</p>
 *
 * <p>说明： 表注释实体类</P>
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvoiceWithRecharge extends Detail implements Serializable {

	private static final long serialVersionUID = 1561965664606L;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "rechargeId" , value = "充值记录表ID")
	private Long rechargeId;
	@ApiModelProperty(name = "invoiceId" , value = "发票主键ID")
	private Long invoiceId;
	@ApiModelProperty(name = "businessSerialNo" , value = "交易流水号")
	private Long businessSerialNo;
	@ApiModelProperty(name = "businessAccount" , value = "交易账本")
	private String businessAccount;
	@ApiModelProperty(name = "rechargeAmount" , value = "充值金额")
	private Long rechargeAmount;
	@ApiModelProperty(name = "rechargeWay" , value = "充值方式")
	private String rechargeWay;
	@ApiModelProperty(name = "businessNo" , value = "交易单号")
	private Long businessNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "提交时间")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "receivePaymentTime" , value = "到款时间")
	private Date receivePaymentTime;
	/**
	 * 新增的属性或方法  不映射数据库字段
	 */
	//标记这是发票对应的充值明细表
	public static String rechargeDetail = "recharge";
	@Override
	public String whichDetail() {
		return rechargeDetail;
	}
}
