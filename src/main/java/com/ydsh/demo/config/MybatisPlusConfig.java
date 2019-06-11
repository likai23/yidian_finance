package com.ydsh.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.baomidou.mybatisplus.spring.*;


/**
 * @author <a href="mailto:likai@yidianlife.com">likai</a>
 * @version V0.0.1
 * @date 2019年6月10日
 */

@Configuration
//@ImportResource(locations = {"classpath:/mybatis/spring-mybatis.xml"})
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 生成业务主键
     * @param A 必须对应存在参数
     * @param B 必须对应存在参数
     * @return
     */
}
