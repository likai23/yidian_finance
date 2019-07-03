/**
 * @filename:InvoiceWithOrderService 2019-07-01 03:23:55
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.entity.InvoiceWithOrder;
import com.ydsh.generator.common.JsonResult;

/**
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 表注释服务层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
public interface InvoiceWithOrderService extends IService<InvoiceWithOrder> {
    //初始化订单明细
    public void initDetail(Invoice invoice);
    //保存订单明细
    public JsonResult<Invoice> saveInvoiceOrders(Invoice invoice);
    //更新订单明细
    public JsonResult<Invoice> updateInvoiceOrders(Invoice invoice);
    //检查订单明细
    public JsonResult<Invoice> checkInvoiceOrders(Invoice invoice);

}