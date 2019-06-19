/**
 * @filename:PayApplyController 2019-06-11 10:50:21
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.controller.base;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.ydsh.demo.common.db.DBKeyGenerator;
import com.ydsh.demo.common.enums.DBBusinessKeyTypeEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 表注释API接口层</P>
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
public class AbstractController<S extends IService<T>,T>{
	
	@Autowired
    protected S baseService;

	protected JsonResult<T> result = new JsonResult<T>();
	/**
	 * @explain 查询对象  <swagger GET请求>
	 * @param   id
	 * @return  JsonResult
	 * @author  姚仲杰
	 * @time    2019-06-11 10:50:21
	 */
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
	@ApiOperation(value = "根据id获取对象", notes = "作者：姚仲杰")
	@ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
	public JsonResult<T> getById(@PathVariable("id")Long id){
		T obj=baseService.getById(id);
		if (null!=obj ) {
			 result.success(obj);
		}else {
			 result.error("查询对象不存在！");
		}
		return result;
	}
	
	/**
	 * @explain 添加
	 * @param   entity
	 * @return  JsonResult
	 * @author  姚仲杰
	 * @time    2019-06-11 10:50:21
	 */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
	@ApiOperation(value = "添加", notes = "作者：姚仲杰")
	public JsonResult<T> insert( @Valid T entity, BindingResult verify){
		JsonResult<T> result=new JsonResult<T>();
        if (verify.hasErrors()) {
            result = getVerifyInfo( verify );
        }else if (null != entity) {
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
    //校验有错时，获取错误信息
    public JsonResult<T> getVerifyInfo( BindingResult verify) {
        JsonResult<T> result=new JsonResult<T>();
        Map<String, String> messages = new HashMap<>();
        verify.getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            messages.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        result.error(messages.toString());
        System.out.println(messages.toString());
        return result;
    }

	/**
	* @explain 批量添加
	* @param   entityList
	* @return  Boolean
	* @author  姚仲杰
	* @time    2019-06-11 10:50:21
	*/
	@RequestMapping(value = "/insertBatch",method = RequestMethod.POST)
	@ApiOperation(value = "添加", notes = "作者：姚仲杰")
	public JsonResult<T> insertBatch(@RequestBody List<T> entityList){
		JsonResult<T> result=new JsonResult<T>();
		List<T> list=entityList;
		if (null!=entityList) {
			boolean rsg = baseService.saveBatch(entityList);
			if (rsg) {
				result.success("添加成功");
			}else {
				result.error("添加失败！");
			}
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	
	/**
	 * @explain 修改
	 * @param   entity
	 * @return  JsonResult
	 * @author  姚仲杰
	 * @time    2019-06-11 10:50:21
	 */
	@RequestMapping(value = "/updateById",method = RequestMethod.POST)
	@ApiOperation(value = "修改", notes = "作者：姚仲杰")
	public JsonResult<T> updateById(@Valid @RequestBody T entity,BindingResult verify){
		JsonResult<T> result=new JsonResult<T>();
        if (verify.hasErrors()) {
            result = getVerifyInfo( verify );
        }else if (null!=entity) {
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
	/**
	* @explain 批量修改
	* @param   entityList
	* @return  JsonResult
	* @author  姚仲杰
	* @time    2019-06-11 10:50:21
	*/
    @RequestMapping(value = "/updateBatchById",method = RequestMethod.POST)
	@ApiOperation(value = "修改", notes = "作者：姚仲杰")
	public JsonResult<T> updateBatchById(@RequestBody List<T> entityList) {
		JsonResult<T> result = new JsonResult<T>();
		if (null != entityList) {
			boolean rsg = baseService.updateBatchById(entityList);
			if (rsg) {
				result.success("修改成功");
			} else {
				result.error("修改失败！");
			}
		} else {
			result.error("请传入正确参数！");
		}
		return result;
	}

	/**
	 * @explain 分页条件查询用户   
	 * @param   param
	 * @return  JsonResult<IPage<T>>
	 * @author  姚仲杰
	 * @time    2019-06-11 10:50:21
	 */
    @RequestMapping(value = "/getPages",method = RequestMethod.GET)
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：姚仲杰")
	public JsonResult<IPage<T>> getUserPages(PageParam<T> param){
		JsonResult<IPage<T>> returnPage=new JsonResult<IPage<T>>();
		Page<T> page=new Page<T>(param.getPageNum(),param.getPageSize());
		QueryWrapper<T> queryWrapper =new QueryWrapper<T>();
		queryWrapper.setEntity(param.getParam());
		//分页数据
		IPage<T> pageData=baseService.page(page, queryWrapper);
		returnPage.success(pageData);
		
		return returnPage;
	}

}
