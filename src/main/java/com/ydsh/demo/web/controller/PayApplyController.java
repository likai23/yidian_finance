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
import com.ydsh.demo.web.controller.base.AbstractController;
import com.ydsh.demo.web.entity.PayApply;
import com.ydsh.demo.web.service.PayApplyService;
import com.ydsh.generator.common.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/payApply")
@Slf4j
public class PayApplyController extends AbstractController<PayApplyService,PayApply>{
    /**
     * @explain 模糊查询
     * @param   payApply
     * @return  JsonResult
     * @author  李锴
     * @time    2019-06-11 10:50:21
     */
    @RequestMapping(value = "/fuzzyQuery",method = RequestMethod.POST)
    @ApiOperation(value = "模糊查询", notes = "作者：李锴")
    public JsonResult<IPage<PayApply>> fuzzyQuery(PayApply payApply) {

        JsonResult<IPage<PayApply>> returnPage=new JsonResult<IPage<PayApply>>();

        IPage<PayApply> payApplys = baseService.page(new Page<PayApply>(1,10),
                new QueryWrapper<PayApply>()
                        .like(!StringUtils.isEmpty(payApply.getPayApplyNo()),"pay_apply_no",payApply.getPayApplyNo())
                        .like(!StringUtils.isEmpty(payApply.getSellerNo()),"seller_no",payApply.getSellerNo())
                        .like(!StringUtils.isEmpty(payApply.getSellerName()),"seller_name",payApply.getSellerName())
                        .like(!StringUtils.isEmpty(payApply.getPayStatus()),"pay_status",payApply.getPayStatus())
                        .like(!StringUtils.isEmpty(payApply.getCreateId()),"create_id",payApply.getCreateId())
                        .like(!StringUtils.isEmpty(payApply.getCreateTime()),"create_time",payApply.getCreateTime())
                        .like(!StringUtils.isEmpty(payApply.getPayType()),"pay_type",payApply.getPayType())
                        .like(!StringUtils.isEmpty(payApply.getToPurchaseNo()),"to_purchase_no",payApply.getToPurchaseNo())
        );
        returnPage.success(payApplys);
        return returnPage;
    }
    /**
     * @explain 根据关联单号查询对象  <swagger GET请求>
     * @param   no
     * @return  JsonResult
     * @author  姚仲杰
     * @time    2019-06-11 10:50:21
     */
    @RequestMapping(value = "/getById/{no}",method = RequestMethod.GET)
    @ApiOperation(value = "根据关联单号获取对象", notes = "作者：李锴")
    @ApiImplicitParam(paramType="path", name = "toPurchaseNo", value = "关联单号", required = true, dataType = "Long")
    public JsonResult<PayApply> getByNo(@PathVariable("no")String no){
        QueryWrapper<PayApply> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("to_purchase_no", no);
        PayApply payApply = baseService.getBaseMapper().selectOne(queryWrapper);
        if (null==payApply ) {
            result.success("该采购单没有关联的申请付款单，可以新增！");
        }else {
            result.error("该采购单已经存在关联的申请付款单！",payApply);
        }
        return result;
    }
    /**
     * @explain 根据id逻辑删除  <swagger GET请求>
     * @param   id
     * @return  JsonResult
     * @author  李锴
     * @time    2019-06-11 10:50:21
     */
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除付款申请单", notes = "作者：李锴")
    @ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
    public JsonResult<PayApply> deleteById(@PathVariable("id")Long id){
        int msg=baseService.getBaseMapper().deleteById(id);
        if (msg==0 ) {
            result.success("删除成功！");
        }else {
            result.error("要删除的对象不存在！");
        }
        return result;
    }
}