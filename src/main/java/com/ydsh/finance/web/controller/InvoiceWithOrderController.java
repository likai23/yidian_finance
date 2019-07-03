/**
 * @filename:InvoiceWithOrderController 2019-07-01 03:23:55
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydsh.finance.common.enums.DBDictionaryEnumManager;
import com.ydsh.finance.common.util.TextUtils;
import com.ydsh.finance.web.controller.base.AbstractController;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.entity.InvoiceWithOrder;
import com.ydsh.finance.web.service.InvoiceService;
import com.ydsh.finance.web.service.InvoiceWithOrderService;
import com.ydsh.generator.common.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>自定义方法写在这里</p>
 * 
 * <p>说明： 表注释API接口层</P>
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
@Api(description = "表注释",value="表注释" )
@RestController
@RequestMapping("/invoiceWithOrder")
@Slf4j
public class InvoiceWithOrderController extends AbstractController<InvoiceWithOrderService,InvoiceWithOrder>{
    @Autowired
    InvoiceService invoiceService;
    /**
     * 查询已经开过发票的订单信息
     * @param order
     * @return
     */
    @RequestMapping(value = "/queryOrdersWithInvoice", method = RequestMethod.POST)
    @ApiOperation(value = "查询已经开过发票的订单信息", notes = "作者：李锴")
    public JsonResult<List<InvoiceWithOrder>> queryOrdersWithInvoice(@RequestBody InvoiceWithOrder order) {
        JsonResult<List<InvoiceWithOrder>> resultOrders = new JsonResult<>();

        List<Invoice> invoices = invoiceService.list( new QueryWrapper<Invoice>()
                .eq("invoice_status", DBDictionaryEnumManager.invoice_status_0.getkey()));
        if (TextUtils.isEmptys( invoices)) {
            return resultOrders.error("该付款申请单状态不能为空！");
        }
        List<InvoiceWithOrder> orders = baseService.list( new QueryWrapper<InvoiceWithOrder>()
                .in("invoice_id", invoices));
        if (TextUtils.isEmptys( invoices)) {
            return resultOrders.error("该付款申请单状态不能为空！");
        }
        return resultOrders.success(orders);
    }
}