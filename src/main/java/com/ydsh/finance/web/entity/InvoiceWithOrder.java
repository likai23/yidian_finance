/**
 * @filename:ConsumeTypeOrder 2019-06-19 03:21:19
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
 * <p>发票对应的所有订单明细</p>
 * 
 * <p>说明： 表注释实体类</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvoiceWithOrder extends Detail implements Serializable {

	private static final long serialVersionUID = 1560928879232L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "orderId" , value = "订单明细表ID")
	private Long orderId;
	@ApiModelProperty(name = "invoiceId" , value = "发票主键ID")
	private Long invoiceId;
	@ApiModelProperty(name = "orderNo" , value = "订单编号")
	private String orderNo;
	@ApiModelProperty(name = "skuNo" , value = "SKU编号")
	private String skuNo;
	@ApiModelProperty(name = "goodsName" , value = "商品名称")
	private String goodsName;
	@ApiModelProperty(name = "denomination" , value = "面值")
	private Long denomination;
	@ApiModelProperty(name = "goodsType" , value = "商品类型")
	private String goodsType;
	@ApiModelProperty(name = "goodsCategory" , value = "商品类目")
	private String goodsCategory;
	@ApiModelProperty(name = "goodsCount" , value = "数量")
	private Long goodsCount;
	@ApiModelProperty(name = "revenuePrice" , value = "含税单价")
	private Long revenuePrice;
	@ApiModelProperty(name = "taxRate" , value = "税率")
	private Long taxRate;
	@ApiModelProperty(name = "totalAmount" , value = "总金额")
	private Long totalAmount;
	@ApiModelProperty(name = "invoiceType" , value = "发票类型")
	private String invoiceType;
	@ApiModelProperty(name = "invoiceSubject" , value = "开票科目")
	private String invoiceSubject;
	@ApiModelProperty(name = "createId" , value = "创建人")
	private Long createId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "publishTime" , value = "发布时间")
	private Date publishTime;
	/**
	 * 新增的属性或方法  不映射数据库字段
	 */
	@Override
	public String whichDetail() {
		return "order";
	}
}
