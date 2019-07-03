package com.ydsh.finance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2Config {
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//                // 通过.select()方法，去配置扫描接口
//                .select()
//                // RequestHandlerSelectors配置如何扫描接口
//                .apis(RequestHandlerSelectors.basePackage("com.example.swaggerexample.controller")).build();
//    }
//
//
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
//        // public ApiInfo(String title, String description, String version, String termsOfServiceUrl, Contact contact, String ", String licenseUrl, Collection<VendorExtension> vendorExtensions) {
//        return new ApiInfo("Swagger学习", // 标题
//                "学习演示如何配置Swagger", // 描述
//                "v1.0", // 版本
//                "http://terms.service.url/组织链接", // 组织链接
//                contact, // 联系人信息
//                "Apach 2.0 许可", // 许可
//                "许可链接", // 许可连接
//                new ArrayList<>()); // 扩展
//    }

    public static final String BASE_PACKAGE = "com.ydsh.finance.web.controller";
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enableSwagger)// 生产环境的时候关闭 swagger 比较安全
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger RESTful APIs")
                .description("Swagger API 服务")
                .termsOfServiceUrl("http://www.yidianlife.com")
                .contact(new Contact("Swagger", "127.0.0.1", "yaozhongjie@yidianlife"))
                .version("1.0")
                .build();
    }
}
