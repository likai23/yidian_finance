/**
 * @filename:InvoiceWithRechargeController 2019-07-01 03:21:04
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.controller;

import com.ydsh.finance.web.controller.base.AbstractController;
import com.ydsh.finance.web.entity.InvoiceWithRecharge;
import com.ydsh.finance.web.service.InvoiceWithRechargeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/invoiceWithRecharge")
@Slf4j
public class InvoiceWithRechargeController extends AbstractController<InvoiceWithRechargeService,InvoiceWithRecharge>{
	
}