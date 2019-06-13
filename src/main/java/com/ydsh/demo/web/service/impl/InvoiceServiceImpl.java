/**
 * @filename:InvoiceServiceImpl 2019-06-12 06:54:59
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2018 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.service.impl;

import com.ydsh.demo.web.entity.Invoice;
import com.ydsh.demo.web.dao.InvoiceDao;
import com.ydsh.demo.web.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 表注释服务实现层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Service
public class InvoiceServiceImpl  extends ServiceImpl<InvoiceDao, Invoice> implements InvoiceService  {
    @Override
    public List<Invoice> fuzzyQuery(Invoice invoice) {
        return baseMapper.fuzzyQuery(invoice);
    }
//    @Autowired
//    protected InvoiceDao invoiceDao;

}