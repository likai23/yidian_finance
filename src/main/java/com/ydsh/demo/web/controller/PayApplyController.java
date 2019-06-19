/**
 * @filename:PayApplyController 2019-06-11 10:50:21
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.demo.common.db.DBKeyGenerator;
import com.ydsh.demo.common.enums.DBBusinessKeyTypeEnums;
import com.ydsh.demo.web.controller.base.AbstractController;
import com.ydsh.demo.web.entity.PayApply;
import com.ydsh.demo.web.service.PayApplyService;
import com.ydsh.generator.common.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
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
@RequestMapping("/payApply")
@Slf4j
public class PayApplyController extends AbstractController<PayApplyService,PayApply>{
    /**
     * @explain 模糊查询
     * @param   payApply
     * @return  JsonResult
     * @author  李锴
     */
    @PostMapping(value = "/fuzzyQuery")
    @ApiOperation(value = "模糊查询", notes = "作者：李锴")
    public JsonResult<IPage<PayApply>> fuzzyQuery(@RequestBody PayApply payApply) {

        JsonResult<IPage<PayApply>> returnPage=new JsonResult<>();
        if (payApply!=null) {
            IPage<PayApply> payApplys = baseService.page(new Page<>(1,10),
                    new QueryWrapper<PayApply>()
                            .like(!StringUtils.isEmpty(payApply.getPayApplyNo()),"pay_apply_no",payApply.getPayApplyNo())
                            .like(!StringUtils.isEmpty(payApply.getSellerNo()),"seller_no",payApply.getSellerNo())
                            .like(!StringUtils.isEmpty(payApply.getSellerName()),"seller_name",payApply.getSellerName())
                            .like(!StringUtils.isEmpty(payApply.getPayStatus()),"pay_status",payApply.getPayStatus())
                            .like(!StringUtils.isEmpty(payApply.getCreateId()),"create_id",payApply.getCreateId())
                            .between(!StringUtils.isEmpty(payApply.getCreateTime()), "create_time", payApply.getBeginTime(), payApply.getEndTime())
                            .like(!StringUtils.isEmpty(payApply.getPayType()),"pay_type",payApply.getPayType())
                            .like(!StringUtils.isEmpty(payApply.getToPurchaseNo()),"to_purchase_no",payApply.getToPurchaseNo())
            );
            returnPage.success(payApplys);
        } else {
            result.error("查询条件为空！");
        }
        return returnPage;
    }

    /**
     * @explain 添加
     * @param   entity
     * @return  JsonResult
     * @author  姚仲杰
     * @time    2019-06-11 10:50:21
     */
    @PostMapping(value = "/insertPayApply")
    @ApiOperation(value = "添加", notes = "作者：李锴")
    public JsonResult<PayApply> insertPayApply( @Valid PayApply entity, BindingResult verify){
        JsonResult<PayApply> result=new JsonResult<>();
        if (verify.hasErrors()) {
            result = getVerifyInfo( verify );
        }else if (null != entity) {
            String DBKey = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.PR,null);
            entity.setPayApplyNo(DBKey);
            boolean rsg = baseService.save(entity);
            if (rsg) {
                result.success("添加成功");
            } else {
                result.error("添加失败！");
            }
        } else {
            result.error("请传入正确参数！");
        }
        return result;
    }
    /**
     * @explain 修改某个特殊字段
     * @param   entity
     * @return  JsonResult
     * @author  李锴
     */
    @PostMapping(value = "/updateFieldById")
    @ApiOperation(value = "修改某个特殊字段", notes = "作者：李锴")
    public JsonResult<PayApply> updateById( @RequestBody PayApply entity){
        JsonResult<PayApply> result=new JsonResult<>();
        if (null!=entity) {
            boolean rsg = baseService.updateById(entity);
            if (rsg) {
                result.success("修改成功");
            }else {
                result.error("修改失败！");
            }
        }else {
            result.error("请传入正确参数！");
        }
        return result;
    }
}