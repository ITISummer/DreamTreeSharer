package com.ITIS.DreamTreeSharer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SummerLv
 * @program: DreamTreeSharer
 * @description: Swagger2配置类
 * @create: 2021/2/28 16:22
 * 配置哪些包被扫描建立开发文档
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ITIS.DreamTreeSharer.controller"))
                .paths(PathSelectors.any()) // 所有路径
                .build()
                //添加全局认证
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    /**
     * 配置 api 信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DreamTreeSharer 开发文档")
                .description("DreamTreeSharer 开发文档")
//                .contact(new Contact("SummerLv","http://localhost:8080/dreamtreesharer/doc.html", "lv.summer@qq.com"))
                .contact(new Contact("SummerLv","http://localhost:8080/doc.html", "lv.summer@qq.com"))
                .version("1.0")
                .build();
    }


    private List<ApiKey> securitySchemes() {
        //设置请求头
        List<ApiKey> result = new ArrayList<>();
        //param1:apiKey的名字
        //param2-param3 = k - v形式
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //需要认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("^(?!auth).*$"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        //授权范围 global全局
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return  result;
    }


}
