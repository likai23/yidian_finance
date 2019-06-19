/**
 * @filename:ConsumeTypeOrderController 2019-06-18 08:21:22
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.controller;

import com.ydsh.demo.web.controller.base.AbstractController;
import com.ydsh.demo.web.entity.ConsumeTypeOrder;
import com.ydsh.demo.web.service.ConsumeTypeOrderService;
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
@RequestMapping("/consumeTypeOrder")
@Slf4j
public class ConsumeTypeOrderController extends AbstractController<ConsumeTypeOrderService,ConsumeTypeOrder>{
	
}