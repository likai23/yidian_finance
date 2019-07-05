/**
 * @filename:PayApplyController 2019-06-11 10:50:21
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
import com.ydsh.finance.web.entity.PayApply;
import com.ydsh.finance.web.service.PayApplyService;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Api(description = "付款申请单管理",value="表注释" )
@RestController
@RequestMapping("/payApply")
@Slf4j
public class PayApplyController extends AbstractController<PayApplyService,PayApply> {

    /**
     * @param pageParam
     * @return JsonResult
     * @explain 分页查询
     * @author 李锴
     */
    @PostMapping(value = "/pagePayApply")
    @ApiOperation(value = "分页查询", notes = "作者：李锴")
    public JsonResult<IPage<PayApply>> pagePayApply(@RequestBody PageParam<PayApply> pageParam) {
        JsonResult<IPage<PayApply>> resultPage = new JsonResult<>();
        if (TextUtils.isEmpty( pageParam)) {
            return resultPage.error("请传入正确参数！");
        }
        PayApply payApply =  pageParam.getParam();
        Integer pageSize = pageParam.getPageSize();
        Integer pageNum = pageParam.getPageNum();
        if ( pageSize > CommonConstans.MAX_PAGESIZE ) {
            return resultPage.error("每页数据的数量不允许超过500个，请正确操作！");
        }
        if ( pageNum <= CommonConstans.LIMIT_PAGENUM ) {
            pageParam.setPageNum(CommonConstans.LIMIT_PAGENUM +1);
        }
        Map<String, Object> map = new HashMap<>();
        if (payApply != null) {
            map = MapBeanUtil.objectCamel2MapUnderline(payApply);
        }
        QueryWrapper<PayApply> queryWrapper = new QueryWrapper<>();
        //循环调用
        for(Map.Entry<String, Object> entry : map.entrySet()){
//            log.info(entry.getKey()+":"+entry.getValue());
            if(entry.getValue() instanceof Date){
                queryWrapper.between(!TextUtils.isEmpty(entry.getValue()), entry.getKey(), payApply.getBeginTime(), payApply.getEndTime());
            }else{
                queryWrapper.likeRight(!TextUtils.isEmpty(entry.getValue()), entry.getKey(), entry.getValue());
            }
        }
        queryWrapper.lambda().groupBy(PayApply::getCreateTime);
        IPage<PayApply> payApplys = baseService.page(new Page<>( pageNum, pageSize),
                queryWrapper
        );
//        IPage<PayApply> payApplys = baseService.page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()),
//                new QueryWrapper<PayApply>()
//                        .like(!TextUtils.isEmpty(payApply.getPayApplyNo()), "pay_apply_no", payApply.getPayApplyNo())
//                        .like(!TextUtils.isEmpty(payApply.getSellerNo()), "seller_no", payApply.getSellerNo())
//                        .like(!TextUtils.isEmpty(payApply.getSellerName()), "seller_name", payApply.getSellerName())
//                        .like(!TextUtils.isEmpty(payApply.getPayStatus()), "pay_status", payApply.getPayStatus())
//                        .like(!TextUtils.isEmpty(payApply.getCreateId()), "create_id", payApply.getCreateId())
//                        .between(!TextUtils.isEmpty(payApply.getCreateTime()), "create_time", payApply.getBeginTime(), payApply.getEndTime())
//                        .like(!TextUtils.isEmpty(payApply.getPayType()), "pay_type", payApply.getPayType())
//                        .like(!TextUtils.isEmpty(payApply.getToPurchaseNo()), "to_purchase_no", payApply.getToPurchaseNo())
//        );
        resultPage.success(payApplys);
        return resultPage;
    }
    /**
     * @param payApply
     * @return JsonResult
     * @explain 添加付款申请单
     * @author 李锴
     */
    @PostMapping(value = "/insertPayApply")
    @ApiOperation(value = "添加付款申请单", notes = "作者：李锴")
    public JsonResult<PayApply> insertPayApply(@Valid @RequestBody PayApply payApply, BindingResult verify) {
        JsonResult<PayApply> result = new JsonResult<>();
        if (verify.hasErrors()) {
            result = getVerifyInfo(verify);
            return result;
        }
        if (TextUtils.isEmpty( payApply.getPayType())) {
            return result.error("付款类型不能为空！");
        }
        if ( !DBDictionaryEnumManager.payapply_type_0.getkey().equals(payApply.getPayType())) {
            return result.error("新建付款单时，只能选择采购付款！");
        }
        if (TextUtils.isEmpty( payApply.getPayStatus())) {
            return result.error("该付款申请单状态不能为空！");
        }
        String DBKey = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.PR, null);
        payApply.setPayApplyNo(DBKey)
                .setPayStatus(DBDictionaryEnumManager.review_0.getkey())
                .setStatus(Integer.parseInt(DBDictionaryEnumManager.valid.getkey()));
        boolean rsg = baseService.save(payApply);
        if (rsg) {
            result.success("添加成功");
        } else {
            result.error("添加失败！");
        }
        return result;
    }
    /**
     * @param payApply
     * @return JsonResult
     * @explain 根据id，1-查看付款申请单 2-修改时进入查看付款申请单、3-审核时进入查看付款申请单、4-标记已开票时进入查看付款申请单
     * @author 李锴
     */
    @PostMapping(value = "/getPayApplyById")
    @ApiOperation(value = "根据id，1-查看付款申请单 2-修改时进入查看付款申请单、3-审核时进入查看付款申请单、4-标记已开票时进入查看付款申请单", notes = "作者：李锴")
    public JsonResult<PayApply> getPayApplyById(@RequestBody PayApply payApply) {
        Integer queryType = payApply.getQueryType();
        if (TextUtils.isEmpty( queryType)) {
            return result.error("请传入对应的操作选项！");
        }
        Long payApplyId = payApply.getId();
        if (TextUtils.isEmptys( payApplyId)) {
            return result.error("请传入id！");
        }
        PayApply payApplyDb;
        if (1 == queryType) {//判断类型，如果是修改的话
            payApplyDb = baseService.getById(payApplyId);
            if (TextUtils.isEmptys( payApplyDb)) {
                return result.error("该付款申请单不存在！");
            }
            if (TextUtils.isEmptys( payApplyDb.getPayStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            //判断前置状态
            if (!(DBDictionaryEnumManager.review_0.getkey().equals(payApplyDb.getPayStatus()) ||
                    DBDictionaryEnumManager.review_2.getkey().equals(payApplyDb.getPayStatus()))
                    ) {
                return result.error("该付款申请订单无法修改！");
            }
        } else if(0 == queryType) {// 判断类型，如果是查看的话
            payApplyDb = baseService.getById(payApplyId);
            if (null == payApplyDb) {
                return result.error("该付款申请单不存在！");
            }
            if (TextUtils.isEmptys(payApplyDb.getPayStatus())) {
                return result.error("该付款申请单状态异常！");
            }
        }else if (2 == queryType) {//  如果是审核查看的话
            payApplyDb = baseService.getById(payApplyId);
            if (TextUtils.isEmptys( payApplyDb)) {
                return result.error("该付款申请单不存在！");
            }
            if (TextUtils.isEmptys( payApplyDb.getPayStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            //判断前置状态
            if (!(DBDictionaryEnumManager.review_0.getkey().equals(payApplyDb.getPayStatus()))
                    ) {
                return result.error("该付款申请订单无法审核！");
            }
        }else if (3 == queryType) {//  如果是付款查看的话
            payApplyDb = baseService.getById(payApplyId);
            if (TextUtils.isEmpty( payApplyDb)) {
                return result.error("该付款申请单不存在！");
            }
            if (TextUtils.isEmpty( payApplyDb.getPayStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            //判断前置状态
            if (!(DBDictionaryEnumManager.review_1.getkey().equals(payApplyDb.getPayStatus()))
                    ) {
                return result.error("该付款申请订单无法付款！");
            }
        }else{
            result.error("输入的操作选项数有误");
            return result;
        }
        result.success("查询成功",payApplyDb);
        return result;
    }

    /**
     * @param payApply
     * @return JsonResult
     * @explain 修改申请付款单
     * @author 李锴
     */
    @RequestMapping(value = "/updatePayApplyById", method = RequestMethod.POST)
    @ApiOperation(value = "修改申请付款单", notes = "作者：李锴")
    public JsonResult<PayApply> updatePayApplyById( @RequestBody @Valid PayApply payApply, BindingResult verify) {
        JsonResult<PayApply> result = new JsonResult<>();
        Long payApplyId = payApply.getId();
        if (TextUtils.isEmptys(payApplyId)) {
            return result.error("请传入id！");
        }
        //校验前端必填属性不为空
        if (verify.hasErrors()) {
            result = getVerifyInfo(verify);
            return result;
        }
        if (TextUtils.isEmpty(payApply.getPayStatus())) {
            return result.error("该付款申请订单状态不能为空！");
        }
        //要更新的对象是否存在
        PayApply payApplyDb = baseService.getById(payApply);
        if (TextUtils.isEmpty(payApplyDb)) {
            result.error("该付款申请单不存在！");
        }
        if (TextUtils.isEmpty( payApplyDb.getPayStatus())) {
            return result.error("该付款申请单状态异常！");
        }
        //判断前置状态
        if (!DBDictionaryEnumManager.review_0.getkey().equals(payApplyDb.getPayStatus()) &&
                !DBDictionaryEnumManager.review_2.getkey().equals(payApplyDb.getPayStatus())) {
            return result.error("该付款申请订单无法修改！");
        }
        boolean rsg = baseService.updateById(payApply);
        if (rsg) {
            result.success("修改成功");
        } else {
            result.error("修改失败！");
        }
        return result;
    }

    /**
     * 付款申请单：1-修改审核状态 2-修改删除状态 3-审核付款状态
     * @param payApply
     * @return JsonResult
     * @author 李锴
     */
    @RequestMapping(value = "/updateSomeFieldToPayApply", method = RequestMethod.POST)
    @ApiOperation(value = "1-修改审核状态 2-修改删除状态 3-审核付款状态", notes = "作者：李锴")
    public JsonResult<PayApply> updateSomeFieldToPayApply(@RequestBody PayApply payApply ) {
        if (TextUtils.isEmpty(payApply)) {
            return result.error("请传入正确的参数！");
        }
        String updateSign = payApply.getUpdateSign();
        if (TextUtils.isEmpty(updateSign)) {
            return result.error("请传入对应的操作选项！");
        }
        //审核、付款
        Long payApplyId = payApply.getId();
        if (TextUtils.isEmptys(payApplyId)) {
            return result.error("请传入正确的id");
        }
        //只有删除需要批量操作
        List<Integer> ids = payApply.getIds();
        if (TextUtils.isEmpty( ids)) {
            return result.error("请传入id！");
        }
        if (updateSign.equals("removePayApply")) { // 删除付款申请单
            List<PayApply> payApplyDbs= (List<PayApply>) baseService.listByIds(ids);
            if (TextUtils.isEmpty( payApplyDbs) ) {
                return result.error("付款申请单不存在！");
            }
            //批量更新数据库数据
            List<PayApply> payApplyNews = new ArrayList<>();
            for(PayApply payApplyDb : payApplyDbs) {
                if (TextUtils.isEmpty( payApplyDb)) {
                    return result.error("付款申请单不存在！");
                }
                //检查数据库查询出的数据
                Long payApplyIdDb = payApplyDb.getId();
                String payStatusDb = payApplyDb.getPayStatus();
                Integer deleteStatusDb = payApplyDb.getStatus();
                if (TextUtils.isEmptys(payStatusDb,deleteStatusDb )) {
                    return result.error("该付款申请单状态异常！");
                }
                if (!(DBDictionaryEnumManager.review_0.getkey().equals(payStatusDb) || DBDictionaryEnumManager.review_2.getkey().equals(payStatusDb))) {
                    return result.error("id为"+ payApplyIdDb +"号的付款申请单不是符合的状态，无法删除！");
                }
                //更新操作
                PayApply payApplyNew = new PayApply();
                payApplyNew.setId(payApplyDb.getId())
                        .setStatus(Integer.parseInt(DBDictionaryEnumManager.invalid.getkey()));
                payApplyNews.add(payApplyNew);
            }
            if ( baseService.updateBatchById(payApplyNews)) {
                return result.success("付款申请单删除成功！");
            }else{
                return result.error("付款申请单删除失败！");
            }
        } else if (updateSign.equals("reviewPayApply")) {// 审核
            //检验传入的id是否存在数据
            PayApply payApplyDb = baseService.getById( payApplyId );
            if (payApplyDb == null) {
                return result.error("不存在此付款申请单！");
            }
            //检验传入的审核值
            String reviewStatus = payApply.getReviewStatus();
            if (TextUtils.isEmpty( reviewStatus)) {
                return result.error("审核值不允许为空！");
            }
            if ( !reviewStatus.equals(DBDictionaryEnumManager.review_1.getkey()) &&
                    !reviewStatus.equals(DBDictionaryEnumManager.review_2.getkey())) {
                return result.error("传入的审核值有误！");
            }
            //检查数据库查询出的数据
            Integer deleteStatusDb = payApplyDb.getStatus();
            String payStatusDb = payApplyDb.getPayStatus();
            if (TextUtils.isEmptys( payStatusDb,deleteStatusDb)) {
                return result.error("该付款申请单状态异常！");
            }
            if (!DBDictionaryEnumManager.review_0.getkey().equals(payStatusDb)) {
                result.error("不是待审核状态！");
                return result;
            }
            // 是否删除
            if (DBDictionaryEnumManager.invalid.getkey().equals(deleteStatusDb)) {
                return result.error("该付款申请单已删除！");
            }
            PayApply payApplyNew = new PayApply();
            payApplyNew.setId(payApply.getId())
                    .setPayStatus(payApply.getReviewStatus())
                    .setReviewRemarks( payApply.getReviewRemarks() );
            if (baseService.updateById(payApplyNew) ) {
                return result.success("审核成功！");
            }else{
                return result.error("审核失败！");
            }
        } else if (updateSign.equals("paid")) { // 付款
            //检验传入的id是否存在数据
            PayApply payApplyDb = baseService.getById( payApplyId );
            if ( payApplyDb == null ) {
                return result.error("不存在此付款申请单！");
            }
            if ( TextUtils.isEmptys( payApply.getReviewAmount())) {
                return result.error("审批付款金额不允许为空！");
            }
            if ( payApply.getReviewAmount() < 0) {
                return result.error("审批付款金额不可为负数！");
            }
            //查询数据库的付款申请单状态
            Integer deleteStatusDb = payApplyDb.getStatus();
            String payStatusDb = payApplyDb.getPayStatus();
            if (TextUtils.isEmptys( payStatusDb,deleteStatusDb)) {
                return result.error("该付款申请单状态异常！");
            }
            if (!DBDictionaryEnumManager.review_1.getkey().equals(payStatusDb)) {
                result.error("不是审核通过状态！");
                return result;
            }
            // 是否删除
            if ((DBDictionaryEnumManager.invalid.getkey().equals(deleteStatusDb))) {
                return result.error("该付款申请单已删除！");
            }
            //更新相应字段
            PayApply payApplyNew = new PayApply();
            payApplyNew.setId(payApply.getId())
                    .setPayStatus(DBDictionaryEnumManager.review_6.getkey())
                    .setReviewAmount( payApply.getReviewAmount() );
            if (baseService.updateById(payApplyNew) ) {
                return result.success("付款成功！");
            }else{
                return result.error("付款失败！");
            }
        }else{
            result.error("输入的操作选项有误");
            return result;
        }
//TODO 付款之后判断付款类型，付款调用别的接口
    }
}
