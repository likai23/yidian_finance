package com.ydsh.demo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.demo.common.bean.Result;
import com.ydsh.demo.web.entity.ConsumeTypeOrder;
import com.ydsh.demo.web.entity.Invoice;
import com.ydsh.demo.web.service.ConsumeTypeOrderService;
import com.ydsh.demo.web.service.InvoiceService;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceDemoApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Autowired
    protected InvoiceService baseService;
    @Test
    public void getUserPages(){
        PageParam<Invoice> param = new PageParam<Invoice>();
        Invoice invoice = new Invoice();
        invoice.setInvoiceNo("0");
        param.setParam(invoice);
        JsonResult<IPage<Invoice>> returnPage=new JsonResult<IPage<Invoice>>();
        Page<Invoice> page=new Page<Invoice>(param.getPageNum(),param.getPageSize());
        QueryWrapper<Invoice> queryWrapper =new QueryWrapper<Invoice>();
        queryWrapper.setEntity(param.getParam());
        //分页数据
        IPage<Invoice> pageData=baseService.page(page, queryWrapper);
        returnPage.success(pageData);


    }
    @Autowired
    ConsumeTypeOrderService s;
    @Test
    public void mybatisPlusTest() {
        List<ConsumeTypeOrder> orders = new ArrayList<>();
        for (int i = 5; i < 9; i++) {
            ConsumeTypeOrder order = new ConsumeTypeOrder();
            order.setId(new Long(i));
            order.setGoodsName("aaaaaaaa");
            orders.add(order);
        }
        s.saveOrUpdateBatch(orders);
    }

    public static void main(String[] args) {
        Result<String> result=new Result<String>(0,"success","");
        System.out.println(JSON.toJSONString(result));
    }
}
