/**
 * @filename:ConsumeTypeOrderDao 2019-06-18 06:35:15
 * @project ydsh-saas-service-finance  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.demo.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ydsh.demo.web.entity.ConsumeTypeOrder;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 表注释数据访问层</p>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Mapper
public interface ConsumeTypeOrderDao extends BaseMapper<ConsumeTypeOrder> {
	
}
