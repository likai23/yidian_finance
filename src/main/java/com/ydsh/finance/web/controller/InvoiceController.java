/**
 * @filename:InvoiceController 2019-06-12 06:54:59
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.finance.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.finance.common.constans.CommonConstans;
import com.ydsh.finance.common.db.DBKeyGenerator;
import com.ydsh.finance.common.enums.DBBusinessKeyTypeEnums;
import com.ydsh.finance.common.enums.DBDictionaryEnumManager;
import com.ydsh.finance.common.util.MapBeanUtil;
import com.ydsh.finance.common.util.TextUtils;
import com.ydsh.finance.web.controller.base.AbstractController;
import com.ydsh.finance.web.entity.Detail;
import com.ydsh.finance.web.entity.Invoice;
import com.ydsh.finance.web.entity.InvoiceWithOrder;
import com.ydsh.finance.web.entity.InvoiceWithRecharge;
import com.ydsh.finance.web.service.InvoiceService;
import com.ydsh.finance.web.service.InvoiceWithOrderService;
import com.ydsh.finance.web.service.InvoiceWithRechargeService;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


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
    @Autowired
    InvoiceWithOrderService orderService;
    @Autowired
    InvoiceWithRechargeService rechargeService;
    /**
     * @param pageParam
     * @return JsonResult
     * @explain 分页查询
     * @author 李锴
     */
    @PostMapping(value = "/pageInvoice")
    @ApiOperation(value = "分页查询", notes = "作者：李锴")
    public JsonResult<IPage<Invoice>> pageInvoice( @RequestBody PageParam<Invoice> pageParam ) {
        JsonResult<IPage<Invoice>> resultPage = new JsonResult<>();
        Invoice invoice =  pageParam.getParam();
        Integer pageSize = pageParam.getPageSize();
        Integer pageNum = pageParam.getPageNum();
        if ( pageSize > CommonConstans.MAX_PAGESIZE ) {
            return resultPage.error("每页数据的数量不允许超过500个，请正确操作！");
        }
        if ( pageNum <= CommonConstans.LIMIT_PAGENUM ) {
            pageParam.setPageNum(1);
        }
        Map<String, Object> map = new HashMap<>();
        if (invoice != null) {
            map = MapBeanUtil.objectCamel2MapUnderline(invoice);
        }
        QueryWrapper<Invoice> queryWrapper = new QueryWrapper<>();
        //循环调用
        for(Map.Entry<String, Object> entry : map.entrySet()){
//            log.info(entry.getKey()+":"+entry.getValue());
            if(entry.getValue() instanceof Date){
                queryWrapper.between(!TextUtils.isEmpty(entry.getValue()), entry.getKey(), invoice.getBeginTime(), invoice.getEndTime());
            }else{
                queryWrapper.likeRight(!TextUtils.isEmpty(entry.getValue()), entry.getKey(), entry.getValue());
            }
        }
        IPage<Invoice> invoices = baseService.page(new Page<>(pageSize, pageNum),
                queryWrapper
        );
//        IPage<Invoice> invoices = baseService.page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()),
//        new QueryWrapper<Invoice>()
//                .like(!TextUtils.isEmpty(invoice.getInvoiceNo()),"invoice_no",invoice.getInvoiceNo())
//                .like(!TextUtils.isEmpty(invoice.getInvoiceStatus()),"invoice_status",invoice.getInvoiceStatus())
//                .like(!TextUtils.isEmpty(invoice.getInvoiceType()),"invoice_type",invoice.getInvoiceType())
//                .like(!TextUtils.isEmpty(invoice.getInvoiceKind()),"invoice_kind",invoice.getInvoiceKind())
//                .like(!TextUtils.isEmpty(invoice.getCreateId()),"create_id",invoice.getCreateId())
//                .between(!TextUtils.isEmpty(invoice.getCreateTime()), "create_time", invoice.getBeginTime(), invoice.getEndTime())
//                .like(!TextUtils.isEmpty(invoice.getPhone()),"phone",invoice.getPhone())
//        );
        resultPage.success(invoices);
        return resultPage;
    }
    /**
     * 分页查询发票信息 没有模糊查询
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "/pageInvoiceNoLike", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询发票信息 没有模糊查询", notes = "作者：李锴")
    public JsonResult<IPage<Invoice>> pageInvoiceNoLike(@RequestBody PageParam<Invoice> pageParam) {
        log.info("【分页查询发票信息】{},请求参数：{}", "接口请求", pageParam);
        JsonResult<IPage<Invoice>> resultPage = new JsonResult<IPage<Invoice>>();
        if (pageParam.getPageSize() > 500) {
            log.error("【分页查询发票信息】{},请求参数：{}", "分页大小超出限制", pageParam);
            return resultPage.error("每页数据的数量超过500个，请正确操作！");
        }
        if (pageParam.getPageNum() <= 0) {
            pageParam.setPageNum(1);
        }
        Page<Invoice> page = new Page<Invoice>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Invoice> queryWrapper = new QueryWrapper<Invoice>();
        if (pageParam.getParam() != null) {
            Map map = MapBeanUtil.objectCamel2MapUnderline(pageParam.getParam());
            queryWrapper.allEq(map);
        }
        queryWrapper.lambda().groupBy(Invoice::getCreateTime);
        //提交开始时间
        if (pageParam.getParam().getEndTime() != null) {
            queryWrapper.lambda().ge(Invoice::getCreateTime, pageParam.getParam().getEndTime());
        }
        //提交结束时间
        if (pageParam.getParam().getBeginTime() != null) {
            queryWrapper.lambda().le(Invoice::getCreateTime, pageParam.getParam().getBeginTime());
        }
        IPage<Invoice> pageData = baseService.page(page, queryWrapper);
        return resultPage.success(pageData);
    }
    /**
     * @explain 添加发票信息
     * @param   invoice
     * @return  JsonResult
     */
    @PostMapping(value = "/insertInvoice")
    @ApiOperation(value = "添加发票信息", notes = "作者：李锴")
    @Transactional
    public JsonResult<Invoice> insertInvoice(@Valid @RequestBody Invoice invoice, BindingResult verify){
            JsonResult<Invoice> result=new JsonResult<>();
            if (verify.hasErrors()) {
                result = getVerifyInfo( verify );
                return result;
            }
            if (TextUtils.isEmpty(invoice.getInvoiceStatus())) {
                return result.error("发票状态不允许为空！");
            }
            if (!TextUtils.isEmpty(invoice.getInvoiceNo())) {
                return result.error("不需要传入申请单编号，会自动编号！");
            }
            if (TextUtils.isEmpty(invoice.getDetails())) {
                return result.error("该发票没有对应的明细！");
            }
            //检查传入的明细
            if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithOrder.orderDetail) ) {
                //多态向下转型为具体明细
                orderService.initDetail(invoice);
                result = orderService.checkInvoiceOrders(invoice);
                if (TextUtils.isEmpty(result.getMessage())) {
                    return result;
                }
                invoice.setWhichDetail(0);
            }else if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithRecharge.rechargeDetail) ){
                rechargeService.initDetail(invoice);
                result = rechargeService.checkInvoiceRecharges(invoice);
                if (TextUtils.isEmpty(result.getMessage())) {
                    return result;
                }
                invoice.setWhichDetail(1);
            }else{
                return result.error("不确定该发票对应的明细，请联系管理员！");
            }
            //保存发票
            String DBKey = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.PR,null);
            invoice.setInvoiceNo(DBKey);
            invoice.setInvoiceStatus(DBDictionaryEnumManager.invoice_status_0.getkey());
            if ( !baseService.save(invoice) ) {
                return result.error("添加失败！");
            }
            //根据不同的明细，存入不同的明细表
            if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithOrder.orderDetail) ) {
                result = orderService.saveInvoiceOrders(invoice);
            }else if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithRecharge.rechargeDetail)){
                result = rechargeService.saveInvoiceRecharges(invoice);
            }
            return result;
    }

    /**
     * @explain 根据id，1-查看发票、2-修改时进入查看发票、3-审核发票时进入查看发票、4-标记已开票时进入查看发票
     * @param   invoice
     * @return  JsonResult
     * @author  李锴
     */
    @PostMapping(value = "/getInvoiceById")
    @ApiOperation(value = "根据id，1-查看发票、2-修改时进入查看发票、3-审核发票时进入查看发票、4-标记已开票时进入查看发票", notes = "作者：李锴")
    public JsonResult<Invoice> getInvoiceById(@RequestBody Invoice invoice){
        Integer queryType = invoice.getQueryType();
        if (TextUtils.isEmpty(queryType)) {
            return result.error("请传入对应的操作选项！");
        }
        Long invoiceId = invoice.getId();
        if (TextUtils.isEmptys( invoiceId)) {
            return result.error("请传入id！");
        }
        // 判断类型， 如果是修改的话，需要判断是否是可修改的数据
        if (1 == queryType ) {
            Invoice invoiceDb = baseService.getById(invoiceId);
            if (TextUtils.isEmptys( invoiceDb)) {
                return result.error("该发票不存在！");
            }
            if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            if (!(DBDictionaryEnumManager.review_0.getkey().equals(invoiceDb.getInvoiceStatus()) ||
                    DBDictionaryEnumManager.review_2.getkey().equals(invoiceDb.getInvoiceStatus()))
                    ) {
                return result.error("该发票无法修改！");
            }
        }else if(0 == queryType){//  如果是查看详情的话
            Invoice invoiceDb = baseService.getById(invoiceId);
            if (TextUtils.isEmptys( invoiceDb)) {
                return result.error("该发票不存在！");
            }
            if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                return result.error("该付款申请单状态异常！");
            }

        }else if(2 == queryType){//  如果是审核查看的话
            Invoice invoiceDb = baseService.getById(invoiceId);
            if (TextUtils.isEmptys( invoiceDb)) {
                return result.error("该发票不存在！");
            }
            if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                return result.error("该发票状态异常！");
            }
            if (!(DBDictionaryEnumManager.review_0.getkey().equals(invoiceDb.getInvoiceStatus()))
                    ) {
                return result.error("该发票不能审核！");
            }
        }else if(3 == queryType){//  如果是标记已开票查看的话
            Invoice invoiceDb = baseService.getById(invoiceId);
            if (TextUtils.isEmptys( invoiceDb)) {
                return result.error("该发票不存在！");
            }
            if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                return result.error("该发票状态异常！");
            }
            if (!(DBDictionaryEnumManager.review_1.getkey().equals(invoiceDb.getInvoiceStatus()))
                    ) {
                return result.error("该发票不能标记已开票！");
            }
        }else{
            result.error("输入的操作选项数有误！");
            return result;
        }
        //查询主表数据
        Invoice invoiceDb = baseService.getById(invoiceId);
        //查询子表数据
        List<Detail> details = new ArrayList<>();
        if (invoiceDb.getWhichDetail()==0) {
            List<InvoiceWithOrder> invoiceWithOrders = orderService.list(new QueryWrapper<InvoiceWithOrder>()
                    .lambda().eq(InvoiceWithOrder::getInvoiceId, invoiceId)
            );
            if (TextUtils.isEmptys(invoiceWithOrders )) {
                return result.error("该发票没有对应的订单，请联系管理员！");
            }
            for (InvoiceWithOrder order:invoiceWithOrders
                 ) {
                details.add(order);
            }
        }else if (invoiceDb.getWhichDetail()==1) {
            List<InvoiceWithRecharge> invoiceWithRecharges = rechargeService.list(new QueryWrapper<InvoiceWithRecharge>()
                    .lambda().eq(InvoiceWithRecharge::getInvoiceId, invoiceId)
            );
            if (TextUtils.isEmptys(invoiceWithRecharges )) {
                return result.error("该发票没有对应的充值明细，请联系管理员！");
            }
            for (InvoiceWithRecharge obj:invoiceWithRecharges
                    ) {
                details.add(obj);
            }
        }else{
            return result.error("不确定该发票对应的明细表，请联系管理员！");
        }
        invoiceDb.setDetails(details);
        result.success("查询成功",invoiceDb);
        return result;
    }

    /**
     * @explain 1-修改发票
     * @param   invoice
     * @return  JsonResult
     */
    @PostMapping(value = "/updateInvoiceById")
    @ApiOperation(value = "修改发票", notes = "作者：李锴")
    @Transactional
    public JsonResult<Invoice> updateInvoiceById(@Valid @RequestBody Invoice invoice,BindingResult verify){
        JsonResult<Invoice> result=new JsonResult<>();
        Long invoiceId = invoice.getId();
        if (TextUtils.isEmptys(invoiceId)) {
            return result.error("请传入id！");
        }
        //校验修改操作必须传入的字段
        if (verify.hasErrors()) {
            result = getVerifyInfo(verify);
            return result;
        }
//        if (TextUtils.isEmpty(invoice.getInvoiceStatus())) {
//            return result.error("发票状态不允许为空！");
//        }
        //检查传入的明细
        if (TextUtils.isEmpty(invoice.getDetails())) {
            return result.error("该发票没有对应的明细！");
        }
        if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithOrder.orderDetail)  ) {
            orderService.initDetail(invoice);
            result = orderService.checkInvoiceOrders(invoice);
            if (TextUtils.isEmpty(result.getMessage())) {
                return result;
            }
            invoice.setWhichDetail(0);
        }else if (  invoice.getDetails().get(0).whichDetail().equals(InvoiceWithRecharge.rechargeDetail) ){
            rechargeService.initDetail(invoice);
            result = rechargeService.checkInvoiceRecharges(invoice);
            if (TextUtils.isEmpty(result.getMessage())) {
                return result;
            }
            invoice.setWhichDetail(1);
        }else{
            return result.error("不确定该发票对应的明细表，请联系管理员！");
        }
        //要更新的对象是否存在
        Invoice invoiceDb = baseService.getById(invoice);
        if (TextUtils.isEmptys(invoiceDb)) {
            return result.error("该付款申请单不存在！");
        }
        if (TextUtils.isEmptys(invoiceDb.getInvoiceStatus())) {
            return result.error("该付款申请单状态异常！");
        }
        //判断前置状态
        if (!(DBDictionaryEnumManager.review_0.getkey().equals(invoiceDb.getInvoiceStatus()) ||
                DBDictionaryEnumManager.review_2.getkey().equals(invoiceDb.getInvoiceStatus()))
                ) {
            return result.error("该发票无法修改！");
        }
        //检查数据库里面的数据完整性
        if (TextUtils.isEmptys(
                invoiceDb.getInvoiceNo(),
                invoiceDb.getInvoiceKind(),
                invoiceDb.getInvoiceType(),
                invoiceDb.getInvoiceWay(),
                invoiceDb.getClientName(),
                invoiceDb.getPhone(),
                invoiceDb.getAddress()
        )) {
            return result.error("该付款申请单数据不完整！");
        }
        //检查数据库中子表数据
        if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithOrder.orderDetail) ) {
            List<InvoiceWithOrder> invoiceWithOrders = orderService.list(new QueryWrapper<InvoiceWithOrder>()
                    .lambda().eq(InvoiceWithOrder::getInvoiceId, invoiceId)
            );
            if (TextUtils.isEmptys(invoiceWithOrders)) {
                return result.error("该发票没有对应订单明细，请联系管理员！");
            }
        }else if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithRecharge.rechargeDetail) ) {
            List<InvoiceWithRecharge> invoiceWithRecharge = rechargeService.list(new QueryWrapper<InvoiceWithRecharge>()
                    .lambda().eq(InvoiceWithRecharge::getInvoiceId, invoiceId)
            );
            if (TextUtils.isEmptys(invoiceWithRecharge)) {
                return result.error("该发票没有对应充值记录明细，请联系管理员！");
            }
        }
        //更新主表 发票表
        if (!baseService.updateById(invoice)) {
            return result.error("发票信息更新失败！");
        }
        //根据不同的明细，更新不同的明细表
        if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithOrder.orderDetail) ) {
            result = orderService.updateInvoiceOrders(invoice);
            if (TextUtils.isEmpty(result.getMessage())) {
                return result;
            }
        }else if ( invoice.getDetails().get(0).whichDetail().equals(InvoiceWithRecharge.rechargeDetail) ){
            result = rechargeService.updateInvoiceRecharges(invoice);
            if (TextUtils.isEmpty(result.getMessage())) {
                return result;
            }
        }else {
            return result.error("不确定发票对应的明细类型！");
        }
            return result;
    }
    /**
     * 发票：1-作废 2-审核 3-已开票
     * @param invoice
     * @return JsonResult
     * @author 李锴
     */
    @PostMapping(value = "/updateSomeFieldToInvoice")
    @ApiOperation(value = "发票管理：1-作废 2-审核 3-已开票", notes = "作者：李锴")
    public JsonResult<Invoice> updateSomeFieldToInvoice(@RequestBody Invoice invoice ) {
        String updateSign = invoice.getUpdateSign();
        if (TextUtils.isEmpty(updateSign)) {
            return result.error("请传入对应的操作选项！");
        }
        JsonResult<Invoice> result = new JsonResult<>();
        List<Integer> ids = invoice.getIds();
        if (TextUtils.isEmpty( ids)) {
            return result.error("请传入id！");
        }
        // 作废操作
        if (updateSign.equals("abolish") ) {
            List<Invoice> invoiceDbs = (List<Invoice>) baseService.listByIds(ids);
            if (TextUtils.isEmpty( invoiceDbs)) {
                return result.error("发票不存在！");
            }
            for(Invoice invoiceDb : invoiceDbs){
                if (TextUtils.isEmpty( invoiceDb)) {
                    return result.error("发票不存在！");
                }
                if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                    return result.error("该发票状态异常！");
                }
                if (!(DBDictionaryEnumManager.review_0.getkey().equals(invoiceDb.getInvoiceStatus()) ||
                        DBDictionaryEnumManager.review_1.getkey().equals(invoiceDb.getInvoiceStatus()))
                        ) {
                    return result.error("id为"+invoiceDb.getId()+"号的发票无法进行作废操作！");
                }
                invoiceDb.setInvoiceStatus(DBDictionaryEnumManager.invoice_status_1.getkey());
            }
            if ( baseService.updateBatchById(invoiceDbs)) {
                return result.success("发票作废操作成功！");
            }else{
                return result.error("发票作废操作失败！");
            }
        }else if(updateSign.equals("reviewInvoice")) {// 审核
           //检验传入的审核值
            String examine = invoice.getExamine();
            if (TextUtils.isEmpty( examine)) {
                return result.error("审核值不允许为空！");
            }
            if ( !examine.equals(DBDictionaryEnumManager.review_1.getkey()) &&
                    !examine.equals(DBDictionaryEnumManager.review_2.getkey())) {
                return result.error("传入的审核值有误！");
            }
            //检验传入的id
            Long invoiceId = invoice.getId();
            if (TextUtils.isEmpty( invoiceId)) {
                return result.error("请传入id！");
            }
            Invoice invoiceDb = baseService.getById(invoiceId);
            if (TextUtils.isEmptys( invoiceDb)) {
                return result.error("该发票不存在！");
            }
            if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            if (!(DBDictionaryEnumManager.review_0.getkey().equals(invoiceDb.getInvoiceStatus()) )
                    ) {
                return result.error("该发票不是待审核状态，无法审核！");
            }
            // 是否作废
            if (DBDictionaryEnumManager.invoice_status_1.getkey().equals(invoiceDb.getInvoiceStatus())) {
                return result.error("该发票已作废！");
            }
//            if (TextUtils.isEmpty( invoice.getExamineOpinion())) {
//                return result.error("请传入id！");
//            }
            Invoice invoiceNew = new Invoice();
            invoiceNew.setId(invoiceId);
            invoiceNew.setExamine(examine);
            invoiceNew.setExamineOpinion(invoice.getExamineOpinion());
            boolean rsg = baseService.updateById(invoiceNew);
            if (rsg) {
                result.success("审核成功");
            } else {
                result.error("审核失败！");
            }
        }else if(updateSign.equals("makeInvoice")){// 开票
            //检验传入的必填项
            String createId = invoice.getCreateId();
            Date createTime = invoice.getCreateTime();
            String invoiceNo = invoice.getInvoiceNo();
            String expressNo = invoice.getExpressNo();
            String expressCompany = invoice.getExpressCompany();
            if (TextUtils.isEmptys( createId,createTime,invoiceNo,expressNo,expressCompany )) {
                return result.error("必填项不允许为空！");
            }
            //检验传入的id
            Long invoiceId = invoice.getId();
            if (TextUtils.isEmpty( invoiceId)) {
                return result.error("请传入id！");
            }
            Invoice invoiceDb = baseService.getById(invoiceId);
            if (TextUtils.isEmptys( invoiceDb)) {
                return result.error("该发票不存在！");
            }
            if (TextUtils.isEmptys( invoiceDb.getInvoiceStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            if (!(DBDictionaryEnumManager.review_1.getkey().equals(invoiceDb.getInvoiceStatus()) )
                    ) {
                return result.error("该发票不是审核通过状态，无法开票！");
            }
            /* 执行更新开票操作*/
            Invoice invoiceNew = new Invoice();
            invoiceNew.setId(invoiceId)
                    .setCreateId(createId)
                    .setCreateTime(createTime)
                    .setInvoiceNo(invoiceNo)
                    .setExpressNo(expressNo)
                    .setExpressCompany(expressCompany);
            boolean rsg = baseService.updateById(invoiceNew);
            if (rsg) {
                result.success("开票成功");
            } else {
                result.error("开票失败！");
            }
        }else {// 错误
            result.error("输入的操作选项数有误");
            return result;
        }
        return result;
    }
}