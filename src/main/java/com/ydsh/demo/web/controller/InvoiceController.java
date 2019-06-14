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
import com.ydsh.demo.web.controller.base.AbstractController;
import com.ydsh.demo.web.entity.Invoice;
import com.ydsh.demo.web.service.InvoiceService;
import com.ydsh.generator.common.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

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

    @RequestMapping(value = "/fuzzyQuery",method = RequestMethod.POST)
    @ApiOperation(value = "模糊查询", notes = "作者：李锴")
    public JsonResult<IPage<Invoice>> fuzzyQuery(Invoice invoice) {

        JsonResult<IPage<Invoice>> returnPage=new JsonResult<IPage<Invoice>>();

        IPage<Invoice> invoices = baseService.page(new Page<Invoice>(1,10),
                new QueryWrapper<Invoice>()
                        .like(!StringUtils.isEmpty(invoice.getInvoiceNo()),"invoice_no",invoice.getInvoiceNo())
                        .like(!StringUtils.isEmpty(invoice.getInvoiceStatus()),"invoice_status",invoice.getInvoiceStatus())
                        .like(!StringUtils.isEmpty(invoice.getInvoiceType()),"invoice_type",invoice.getInvoiceType())
                        .like(!StringUtils.isEmpty(invoice.getInvoiceKind()),"invoice_kind",invoice.getInvoiceKind())
                        .like(!StringUtils.isEmpty(invoice.getCreateId()),"create_id",invoice.getCreateId())
                        .like(!StringUtils.isEmpty(invoice.getCreateTime()),"create_time",invoice.getCreateTime())
                        .like(!StringUtils.isEmpty(invoice.getPhone()),"phone",invoice.getPhone())
        );
        returnPage.success(invoices);
        return returnPage;
    }


}