package com.ydsh.finance;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.finance.common.bean.Result;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.service.InvoiceService;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceDemoApplicationTests {

    @Autowired
    protected InvoiceService baseService;

    @Test
    public void contextLoads() {
        int a = "".length();
        log.info(a+"");
    }

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
//    @Autowired
//    ConsumeTypeOrderService s;
//    @Test
//    public void mybatisPlusTest() {
//        List<InvoiceWithOrder> orders = new ArrayList<>();
//        for (int i = 5; i < 9; i++) {
//            InvoiceWithOrder order = new InvoiceWithOrder();
//            order.setId(new Long(i));
//            order.setGoodsName("aaaaaaaa");
//            orders.add(order);
//        }
//        s.saveOrUpdateBatch(orders);
//    }
@Test
public void forMap(){
    Map<String, String> map = new HashMap<String, String>();
    map.put("xxx", "000");
    map.put("aaa", "111");
    map.put("bbb", "222");
    map.put("ccc", "333");
    map.put("ddd", "444");
    map.put("eee", "555");

    Iterator<Map.Entry<String, String>> iterator  = map.entrySet().iterator();

    while (iterator.hasNext()) {

        Map.Entry<String, String> entry = iterator.next();

        System.out.println(entry.getKey()+":"+entry.getValue());

    }

}
    @Test
    public void t1(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("xxx", "000");
        map.put("aaa", "111");
        map.put("bbb", 2);
        map.put("ccc", "333");
        map.put("ddd", "444");
        map.put("eee", 3);
        System.out.println(map);

        //map的遍历
        for(Map.Entry<String, Object> entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
            if(entry.getValue() instanceof String){
                System.out.println("Value是String类型");
            }
            if(entry.getValue() instanceof Integer){
                System.out.println("Value是Integer类型");
            }

        }

    }
    public static void main(String[] args) {
        Result<String> result=new Result<String>(0,"success","");
        System.out.println(JSON.toJSONString(result));
    }
}
