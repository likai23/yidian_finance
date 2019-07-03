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
import java.util.Map;

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
        PayApply payApply =  pageParam.getParam();
        Integer pageSize = pageParam.getPageSize();
        Integer pageNum = pageParam.getPageNum();
        if ( pageSize > 500) {
            return resultPage.error("每页数据的数量超过500个，请正确操作！");
        }
        if ( pageNum <= 0) {
            pageParam.setPageNum(1);
        }
        IPage<PayApply> payApplys = baseService.page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()),
                new QueryWrapper<PayApply>()
                        .like(!TextUtils.isEmpty(payApply.getPayApplyNo()), "pay_apply_no", payApply.getPayApplyNo())
                        .like(!TextUtils.isEmpty(payApply.getSellerNo()), "seller_no", payApply.getSellerNo())
                        .like(!TextUtils.isEmpty(payApply.getSellerName()), "seller_name", payApply.getSellerName())
                        .like(!TextUtils.isEmpty(payApply.getPayStatus()), "pay_status", payApply.getPayStatus())
                        .like(!TextUtils.isEmpty(payApply.getCreateId()), "create_id", payApply.getCreateId())
                        .between(!TextUtils.isEmpty(payApply.getCreateTime()), "create_time", payApply.getBeginTime(), payApply.getEndTime())
                        .like(!TextUtils.isEmpty(payApply.getPayType()), "pay_type", payApply.getPayType())
                        .like(!TextUtils.isEmpty(payApply.getToPurchaseNo()), "to_purchase_no", payApply.getToPurchaseNo())
        );
        resultPage.success(payApplys);
        return resultPage;
    }
    /**
     * 分页查询付款申请单
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "/pagePayApplyNoLike", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询付款申请单", notes = "作者：李锴")
    public JsonResult<IPage<PayApply>> pagePayApplyNoLike(@RequestBody PageParam<PayApply> pageParam) {
        JsonResult<IPage<PayApply>> resultPage = new JsonResult<IPage<PayApply>>();
        if (pageParam.getPageSize() > 500) {
            return resultPage.error("每页数据的数量超过500个，请正确操作！");
        }
        if (pageParam.getPageNum() <= 0) {
            pageParam.setPageNum(1);
        }
        Page<PayApply> page = new Page<PayApply>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<PayApply> queryWrapper = new QueryWrapper<PayApply>();
        if (pageParam.getParam() != null) {
            Map map = MapBeanUtil.objectCamel2MapUnderline(pageParam.getParam());
            queryWrapper.allEq(map);
        }
        queryWrapper.lambda().groupBy(PayApply::getCreateTime);
        //提交开始时间
        if (pageParam.getParam().getEndTime() != null) {
            queryWrapper.lambda().ge(PayApply::getCreateTime, pageParam.getParam().getEndTime());
        }
        //提交结束时间
        if (pageParam.getParam().getBeginTime() != null) {
            queryWrapper.lambda().le(PayApply::getCreateTime, pageParam.getParam().getBeginTime());
        }
        IPage<PayApply> pageData = baseService.page(page, queryWrapper);
        return resultPage.success(pageData);
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
        //TODO 新建付款单时，只能选择采购付款
        if ( payApply.getPayType().equals("")) {
            return result.error("付款类型不能为空！");
        }
        if (TextUtils.isEmpty( payApply.getPayStatus())) {
            return result.error("该付款申请单状态不能为空！");
        }
        String DBKey = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.PR, null);
        payApply.setPayApplyNo(DBKey);
        payApply.setPayStatus(DBDictionaryEnumManager.review_0.getkey());
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
    @RequestMapping(value = "/getPayApplyById", method = RequestMethod.POST)
    @ApiOperation(value = "1-查看付款申请单 2-修改时进入查看付款申请单 根据id", notes = "作者：李锴")
    public JsonResult<PayApply> getById(@RequestBody PayApply payApply) {
        Integer queryType = payApply.getQueryType();
        if (TextUtils.isEmpty( queryType)) {
            return result.error("请传入对应的操作选项！");
        }
        Long payApplyId = payApply.getId();
        if (TextUtils.isEmptys( payApplyId)) {
            return result.error("请传入id！");
        }
        // 判断类型，如果是修改的话，需要判断是否是可修改的数据
        if (1 == queryType) {
            PayApply payApplyDb = baseService.getById(payApplyId);
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
        } else if(0 == queryType) {// 判断类型 // 如果是查看的话
            PayApply payApplyDb = baseService.getById(payApplyId);
            if (null == payApplyDb) {
                return result.error("该付款申请单不存在！");
            }
            if (TextUtils.isEmptys(payApplyDb.getPayStatus())) {
                return result.error("该付款申请单状态异常！");
            }
        }else if (2 == queryType) {//  如果是审核查看的话
                PayApply payApplyDb = baseService.getById(payApplyId);
                if (TextUtils.isEmptys( payApplyDb)) {
                    return result.error("该付款申请单不存在！");
                }
                if (TextUtils.isEmptys( payApplyDb.getPayStatus())) {
                    return result.error("该付款申请单状态异常！");
                }
                //判断前置状态
                if (!(DBDictionaryEnumManager.review_0.getkey().equals(payApplyDb.getPayStatus()))
                        ) {
                    return result.error("该付款申请订单无法修改！");
                }
        }else if (3 == queryType) {//  如果是已付款查看的话
            PayApply payApplyDb = baseService.getById(payApplyId);
            if (TextUtils.isEmptys( payApplyDb)) {
                return result.error("该付款申请单不存在！");
            }
            if (TextUtils.isEmptys( payApplyDb.getPayStatus())) {
                return result.error("该付款申请单状态异常！");
            }
            //判断前置状态
            if (!(DBDictionaryEnumManager.review_1.getkey().equals(payApplyDb.getPayStatus()))
                    ) {
                return result.error("该付款申请订单无法修改！");
            }
        }else{
            result.error("输入的操作选项数有误");
            return result;
        }

        PayApply obj = baseService.getById(payApplyId);
        if (TextUtils.isEmptys( obj)) {
            result.success(obj);
        } else {
            result.error("查询对象不存在！");
        }
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
//        if (TextUtils.isEmpty(payApply.getPayStatus())) {
//            return result.error("该付款申请订单状态不能为空！");
//        }
        //要更新的对象是否存在
        PayApply payApplyDb = baseService.getById(payApply);
        if (null != payApplyDb) {
            result.success(payApplyDb);
        } else {
            result.error("该付款申请单不存在！");
        }
        if (TextUtils.isEmptys( payApplyDb.getPayStatus())) {
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
     * 1-修改审核状态 2-修改删除状态 3-审核付款状态
     * @param payApply
     * @return JsonResult
     * @author 李锴
     */
    @RequestMapping(value = "/updateSomeFieldToPayApply", method = RequestMethod.POST)
    @ApiOperation(value = "1-修改审核状态 2-修改删除状态 3-审核付款状态", notes = "作者：李锴")
    public JsonResult<PayApply> updateSomeFieldToPayApply(@RequestBody PayApply payApply ) {
        JsonResult<PayApply> result = new JsonResult<>();
        String updateSign = payApply.getUpdateSign();
        if (TextUtils.isEmpty(updateSign)) {
            return result.error("请传入对应的操作选项！");
        }
        Long payApplyId = payApply.getId();
        if (TextUtils.isEmptys(payApplyId)) {
            return result.error("请传入正确的id");
        }
            // 删除付款申请单
            if (updateSign.equals("removePayApply")) {
                PayApply payApplyDb = baseService.getById(payApplyId);
                if (null == payApplyDb) {
                    return result.error("该付款申请单不存在！");
                }
                if (TextUtils.isEmptys( payApplyDb.getPayStatus(),payApplyDb.getStatus())) {
                    return result.error("该付款申请单状态异常！");
                }
                //已删除的
                if (DBDictionaryEnumManager.invalid.getkey().equals(payApplyDb.getStatus())) {
                    return result.error("该付款申请单已删除，请勿重复操作！");
                }
                String payStatus = payApplyDb.getPayStatus();
                String review_0 = DBDictionaryEnumManager.review_0.getkey();
                String review_2 = DBDictionaryEnumManager.review_2.getkey();
                if (!(review_0.equals(payStatus) || review_2.equals(payStatus))) {
                    result.error("不是未审核或审核不通过状态！");
                    return result;
                }
                PayApply payApplyLast = new PayApply();
                payApplyLast.setId(payApply.getId());
                payApplyLast.setPayStatus(DBDictionaryEnumManager.invalid.getkey());
                baseService.updateById(payApplyLast);
                result.setMessage("付款申请单删除成功，待审核通过生效！");
            }
            // 审核付款申请单状态
            else if (updateSign.equals("reviewPayApply")) {
                //检验传入的审核值
                String review = payApply.getReviewStatus();
                if (TextUtils.isEmpty( review)) {
                    return result.error("审核值不允许为空！");
                }
                if ( !review.equals(DBDictionaryEnumManager.review_1.getkey()) &&
                        !review.equals(DBDictionaryEnumManager.review_2.getkey())) {
                    return result.error("传入的审核值有误！");
                }
                PayApply payApplyDb = baseService.getById(payApplyId );
                if (payApplyDb == null) {
                    return result.error("不存在此付款申请单！");
                }
                Integer deleteStatus = payApplyDb.getStatus();
                String payStatus = payApplyDb.getPayStatus();
                if (TextUtils.isEmptys( payStatus,deleteStatus)) {
                    return result.error("该付款申请单状态异常！");
                }
                String reviewStatus = payApplyDb.getReviewStatus();
                String review_0 = DBDictionaryEnumManager.review_0.getkey();
                String invalid = DBDictionaryEnumManager.invalid.getkey();
                if (!review_0.equals(reviewStatus)) {
                    result.error("不是待审核状态！");
                    return result;
                }
                // 是否删除
                if (invalid.equals(deleteStatus)) {
                    return result.error("该付款申请单已删除！");
                }
                PayApply payApplyLast = new PayApply();
                payApply.setId(payApply.getId());
                payApplyLast.setPayStatus(review);
                payApplyLast.setReviewRemarks(payApply.getReviewRemarks());
                baseService.updateById(payApplyLast);
                result.setMessage("更新审核状态成功！");
            }
            else if (updateSign.equals("paid")) { // 更新已付款
                PayApply payApplyDb = baseService.getById(payApplyId );
                if (payApplyDb == null) {
                    return result.error("不存在此付款申请单！");
                }
                Integer deleteStatus = payApplyDb.getStatus();
                String payStatus = payApplyDb.getPayStatus();
                if (TextUtils.isEmptys( payStatus,deleteStatus)) {
                    return result.error("该付款申请单状态异常！");
                }
                String reviewStatus = payApply.getReviewStatus();
                String review_0 = DBDictionaryEnumManager.review_0.getkey();
                if (!review_0.equals(reviewStatus)) {
                    result.error("不是审核通过状态！");
                    return result;
                }
                // 是否删除
                if (deleteStatus.equals(DBDictionaryEnumManager.invalid.getkey())) {
                    return result.error("该付款申请单已删除！");
                }
                PayApply payApplyLast = new PayApply();
                payApply.setId(payApply.getId());
                payApplyLast.setPayStatus(DBDictionaryEnumManager.review_6.getkey());
                baseService.updateById(payApplyLast);
                result.setMessage("更新已付款成功！");
            }else{
                result.error("输入的操作选项有误");
                return result;
            }
//TODO 付款之后判断付款类型，付款调用别的接口
        return result;
    }
}
