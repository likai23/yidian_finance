/**
 * @filename:InvoiceServiceImpl 2019-06-12 06:54:59
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2018 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydsh.finance.web.dao.InvoiceDao;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.service.InvoiceService;
import org.springframework.stereotype.Service;

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
//    @Override
//    public List<Invoice> fuzzyQuery(Invoice invoice) {
//        return baseMapper.fuzzyQuery(invoice);
//    }
//    @Autowired
//    protected InvoiceDao invoiceDao;

}