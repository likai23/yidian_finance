/**
 * @filename:InvoiceWithOrderServiceImpl 2019-07-01 03:23:55
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2018 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydsh.finance.common.util.TextUtils;
import com.ydsh.finance.web.dao.InvoiceWithOrderDao;
import com.ydsh.finance.web.entity.Detail;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.entity.InvoiceWithOrder;
import com.ydsh.finance.web.service.InvoiceWithOrderService;
import com.ydsh.generator.common.JsonResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 表注释服务实现层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Service
@Data
@Slf4j
public class InvoiceWithOrderServiceImpl  extends ServiceImpl<InvoiceWithOrderDao, InvoiceWithOrder> implements InvoiceWithOrderService  {
    protected JsonResult<Invoice> result = new JsonResult<>();
    private List<InvoiceWithOrder> orders;
    @Override
    public void initDetail(Invoice invoice) {
        if (TextUtils.isEmpty( invoice.getDetails())) {
            log.error("传入的明细不允许为空！");
            return;
        }
        for (Detail detail : invoice.getDetails()) {
            InvoiceWithOrder recharge = (InvoiceWithOrder) detail;
            this.orders.add(recharge);
        }
    }
    @Override
    public JsonResult<Invoice> saveInvoiceOrders(Invoice invoice){
        if (TextUtils.isEmpty( this.orders)) {
            return result.error("没有初始化明细！");
        }
        JsonResult<Invoice> result=new JsonResult<>();
        //添加此发票对应的多张订单
        List<InvoiceWithOrder> orders = this.orders;
        for (InvoiceWithOrder order : orders) {
            order.setInvoiceId(invoice.getId().longValue());
        }
        if(saveBatch(orders)){
            result.success("添加成功");
        }else{
            result.error("添加失败！");
        }
        return result;
    }
    @Override
    public JsonResult<Invoice> updateInvoiceOrders(Invoice invoice) {
        if (TextUtils.isEmpty( this.orders)) {
            return result.error("没有初始化明细！");
        }
        JsonResult<Invoice> result=new JsonResult<>();
        //把更新之前的订单明细删除
        if (!remove(
                new QueryWrapper<InvoiceWithOrder>()
                        .eq("invoice_id", invoice.getId()))
                ) {
            return result.error("发票对应的订单更新失败！");
        }
        //添加更新之后的订单明细
        List<InvoiceWithOrder> orders = this.orders;
        for (InvoiceWithOrder order : orders) {
            order.setInvoiceId(invoice.getId().longValue());
        }
        if (!saveBatch(orders)) {
            return result.error("发票对应的订单更新失败");
        }
        result.success("发票更新成功");
        return result;
    }
    @Override
    public JsonResult<Invoice> checkInvoiceOrders(Invoice invoice) {
        if (TextUtils.isEmpty( this.orders)) {
            return result.error("没有初始化明细！");
        }
        JsonResult<Invoice> result=new JsonResult<>();

        Set<Object> idIsRepeat = new HashSet<>();
        for (InvoiceWithOrder order : this.orders) {
            //TODO  添加订单明细的必填项
            if (TextUtils.isEmptys(order.getOrderId(), order.getId())) {
                return result.error("请完善 订单号为" + order.getOrderId() + "的订单明细的必填项！");
            }
            //检查发票对应的订单是否有重复
            if (!idIsRepeat.add(order.getOrderId())) {
                return result.error("该发票包含相同的订单明细！");
            }
        }
        return result;
    }
}