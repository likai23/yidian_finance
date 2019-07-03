/**
 * @filename:InvoiceWithRechargeService 2019-07-01 03:21:04
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.entity.InvoiceWithRecharge;
import com.ydsh.generator.common.JsonResult;

/**
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 表注释服务层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
public interface InvoiceWithRechargeService extends IService<InvoiceWithRecharge> {
    //初始化明细
    public void initDetail(Invoice invoice);
    //保存明细
    public JsonResult<Invoice> saveInvoiceRecharges(Invoice invoice);
    //更新明细
    public JsonResult<Invoice> updateInvoiceRecharges(Invoice invoice);
    //检查明细
    public JsonResult<Invoice> checkInvoiceRecharges(Invoice invoice);

}