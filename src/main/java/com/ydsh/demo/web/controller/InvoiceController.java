/**
 * @filename:InvoiceController 2019-06-12 06:54:59
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydsh.demo.common.db.DBKeyGenerator;
import com.ydsh.demo.common.enums.DBBusinessKeyTypeEnums;
import com.ydsh.demo.web.controller.base.AbstractController;
import com.ydsh.demo.web.entity.ConsumeTypeOrder;
import com.ydsh.demo.web.entity.Invoice;
import com.ydsh.demo.web.entity.PayApply;
import com.ydsh.demo.web.service.ConsumeTypeOrderService;
import com.ydsh.demo.web.service.InvoiceService;
import com.ydsh.generator.common.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
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
@RequestMapping("/invoice")
@Slf4j
public class InvoiceController extends AbstractController<InvoiceService,Invoice>{
    ConsumeTypeOrderService orderService;
    /**
     * @explain 模糊查询
     * @param   invoice
     * @return  JsonResult
     * @author  李锴
     * @time    2019-06-11 10:50:21
     */
    @RequestMapping(value = "/fuzzyQuery",method = RequestMethod.POST)
    @ApiOperation(value = "模糊查询", notes = "作者：李锴")
    public JsonResult<IPage<Invoice>> fuzzyQuery(@RequestBody Invoice invoice) {

        JsonResult<IPage<Invoice>> returnPage=new JsonResult<IPage<Invoice>>();
        if (invoice!=null) {
                IPage<Invoice> invoices = baseService.page(new Page<Invoice>(1,10),
                new QueryWrapper<Invoice>()
                        .like(!StringUtils.isEmpty(invoice.getInvoiceNo()),"invoice_no",invoice.getInvoiceNo())
                        .like(!StringUtils.isEmpty(invoice.getInvoiceStatus()),"invoice_status",invoice.getInvoiceStatus())
                        .like(!StringUtils.isEmpty(invoice.getInvoiceType()),"invoice_type",invoice.getInvoiceType())
                        .like(!StringUtils.isEmpty(invoice.getInvoiceKind()),"invoice_kind",invoice.getInvoiceKind())
                        .like(!StringUtils.isEmpty(invoice.getCreateId()),"create_id",invoice.getCreateId())
                        .between(!StringUtils.isEmpty(invoice.getCreateTime()), "create_time", invoice.getBeginTime(), invoice.getEndTime())
                        .like(!StringUtils.isEmpty(invoice.getPhone()),"phone",invoice.getPhone())
        );
            returnPage.success(invoices);
        } else {
            result.error("查询条件为空！");
        }
        return returnPage;
    }
    /**
     * @explain 添加发票信息
     * @param   invoice
     * @return  JsonResult
     */
    @PostMapping(value = "/insertInvoice")
    @ApiOperation(value = "添加", notes = "作者：李锴")
    public JsonResult<Invoice> insertInvoice(@Valid Invoice invoice, BindingResult verify){
        JsonResult<Invoice> result=new JsonResult<>();
        if (verify.hasErrors()) {
            result = getVerifyInfo( verify );
        }else if (null != invoice) {
            String DBKey = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.PR,null);
            invoice.setInvoiceNo(DBKey);
            Integer successId = baseService.getBaseMapper().insert(invoice);
            if (successId > 0 && successId!=null) {
                //添加此发票对应的多张订单
                List<ConsumeTypeOrder> orders = invoice.getOrders();
                for (ConsumeTypeOrder order : orders) {
                    order.setInvoiceId(invoice.getId().longValue());
                }
                if(orderService.saveBatch(orders)){
                    result.success("添加成功");
                }else{
                    result.error("订单明细添加失败！");
                }
            } else {
                result.error("发票信息添加失败！");
            }
        } else {
            result.error("请传入正确参数！");
        }
        return result;
    }
    /**
     * @explain 查询发票  <swagger GET请求>
     * @param   invoiceId
     * @return  JsonResult
     * @author  李锴
     */
    @GetMapping(value = "/getInvoiceById/{id}")
    @ApiOperation(value = "根据id获取发票信息", notes = "作者：李锴")
    public JsonResult<Invoice> getInvoiceById(@PathVariable("id")Long invoiceId){
        Invoice invoice=baseService.getById(invoiceId);
        if (null!=invoice ) {
            List<ConsumeTypeOrder> consumeTypeOrders = orderService.getBaseMapper().selectList(new QueryWrapper<ConsumeTypeOrder>()
                    .eq("invoice_id", invoiceId )
            );
            if(consumeTypeOrders.size() > 0){
                invoice.setOrders(consumeTypeOrders);
                result.success(invoice);
            }else{
                result.error("订单明细查询失败！");
            }
        }else {
            result.error("查询对象不存在！");
        }
        return result;
    }
    /**
     * @explain 修改发票
     * @param   invoice
     * @return  JsonResult
     */
    @PostMapping(value = "/updateInvoiceById")
    @ApiOperation(value = "修改发票", notes = "作者：李锴")
    public JsonResult<Invoice> updateInvoiceById(@Valid @RequestBody Invoice invoice,BindingResult verify){
        JsonResult<Invoice> result=new JsonResult<>();
        if (verify.hasErrors()) {
            result = getVerifyInfo( verify );
        }else if (null != invoice) {
            Integer successId = baseService.getBaseMapper().updateById(invoice);
            if (successId > 0 && successId!=null) {
                //添加此发票对应的多张订单
                List<ConsumeTypeOrder> orders = invoice.getOrders();
                for (ConsumeTypeOrder order : orders) {
                    order.setInvoiceId(invoice.getId().longValue());
                }
                int successLine = orderService.getBaseMapper().delete(new QueryWrapper<ConsumeTypeOrder>()
                        .eq("invoice_id", invoice.getId() ));
                if(successLine > 0){
                    result.success("添加成功");
                }else{
                    result.error("订单明细更新失败！");
                }
            } else {
                result.error("发票信息添加失败！");
            }
        } else {
            result.error("请传入正确参数！");
        }
        return result;
    }
}