# 后端设计

## 参考学习网站

[全套完整版】2021最新SpringBoot+Vue前后端分离项目实战-在线智能办公系统，前后端分离项目](https://www.bilibili.com/video/BV1ar4y1K7qx)



## 数据库设计

[MySQL 日期类型及默认设置](https://blog.csdn.net/gxy_2016/article/details/53436865)

[Mysql中设置默认时间为当前值](https://blog.csdn.net/cherry_xiu/article/details/80235042)

[开发日志：mySQL创建表时添加表和列的](https://blog.csdn.net/kaidishi/article/details/19068043)

### 创建 users 表

```mysql
USE dream_tree_sharer;
CREATE TABLE users(
user_id 	 VARCHAR(255) 	NOT NULL 	COMMENT '用户id',
user_username    VARCHAR(30) 	NOT NULL 	COMMENT '用户名',
user_password    VARCHAR(30) 	NOT NULL 	COMMENT '用户登录密码',
user_sex     	 VARCHAR(10) 	NULL		COMMENT '用户性别',
user_birthday 	 DATE 		NULL		COMMENT '用户生日',
user_description VARCHAR(255) 	NULL 		COMMENT '用户自定义描述',
user_avatar_url  VARCHAR(255)  	NULL 		DEFAULT 'http://90sheji.com/?m=Activity&a=doubleTenthNineteen&srctype=1031&newSrctype=413' COMMENT '用户头像地址',
user_phone  	 VARCHAR(11) 	NULL 		COMMENT '用户手机号',
user_email 	 VARCHAR(255) 	NULL 		COMMENT '用户邮箱',
user_create_time TIMESTAMP 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
user_login_time  TIMESTAMP 	NULL 		DEFAULT CURRENT_TIMESTAMP COMMENT '用户最新登录时间',
PRIMARY KEY (user_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户表';
```

### 创建 admins 表

```mysql
USE dream_tree_sharer;
CREATE TABLE admins(
admin_id 	  VARCHAR(255) 	NOT NULL 	COMMENT '管理员id',
admin_name  	  VARCHAR(30) 	NOT NULL 	COMMENT '管理员名',
admin_password    VARCHAR(30) 	NOT NULL 	COMMENT '管理员登录密码',
admin_create_time TIMESTAMP 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
admin_login_time  TIMESTAMP 	NULL 		DEFAULT CURRENT_TIMESTAMP COMMENT '管理员最新登录时间',
PRIMARY KEY (admin_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='管理员表';
```

### 创建 pinboards 表

```mysql
USE dream_tree_sharer;
CREATE TABLE pinboards(
pinboard_id 	  	  VARCHAR(255) 	NOT NULL 	COMMENT '愿望板id',
pinboard_title  	  VARCHAR(50) 	NOT NULL 	COMMENT '愿望板名',
pinboard_content	  VARCHAR(500)	NOT NULL        COMMENT '愿望板内容',
pinboard_create_time 	  TIMESTAMP 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
pinboard_bgimg_url	  VARCHAR(200)  NULL            COMMENT '愿望板背景图地址',
pinboard_type		  VARCHAR(20)   NOT NULL   	COMMENT '愿望板类型',
PRIMARY KEY (pinboard_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='愿望板表';
```

[SQL FOREIGN KEY 约束]: https://www.runoob.com/sql/sql-foreignkey.html

### 创建 users_pinboards 表

```mysql
USE dream_tree_sharer;
CREATE TABLE users_pinboards(
up_id 	  	  INT(11) 	NOT NULL 	COMMENT '用户-愿望板id',
user_id	  	  VARCHAR(255)  NOT NULL  	COMMENT '用户id',
pinboard_id 	  VARCHAR(255) 	NOT NULL 	COMMENT '愿望板id',	
up_create_time 	  TIMESTAMP 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (up_id),
FOREIGN KEY(user_id) REFERENCES users(user_id),
FOREIGN KEY(pinboard_id) REFERENCES pinboards(pinboard_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户对愿望板的创建记录表';
```

### 创建 users_pinboards_comments 表

```mysql
CREATE TABLE users_pinboards_comments (
  upc_id 		INT (11) 	NOT NULL COMMENT '用户-愿望板-评论id',
  user_id 		VARCHAR (255) 	NOT NULL COMMENT '用户id',
  pinboard_id 		VARCHAR (255) 	NOT NULL COMMENT '愿望板id',
  up_create_time 	TIMESTAMP 	NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (upc_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (pinboard_id) REFERENCES pinboards (pinboard_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '用户对愿望板的评论记录表' ;
```



### 创建 users_pinboards_favorites 表

```mysql
USE dream_tree_sharer;
CREATE TABLE users_pinboards_favorites(
upf_id 	  	  INT(11) 	NOT NULL 	COMMENT '用户-愿望板-收藏id',
user_id	  	  VARCHAR(255)  NOT NULL  	COMMENT '用户id',
pinboard_id 	  VARCHAR(255) 	NOT NULL 	COMMENT '愿望板id',	
up_create_time 	  TIMESTAMP 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (upf_id),
FOREIGN KEY(user_id) REFERENCES users(user_id),
FOREIGN KEY(pinboard_id) REFERENCES pinboards(pinboard_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户对愿望板的收藏记录表';
```

### 创建 users_pinboards_likes 表

```mysql
USE dream_tree_sharer;
CREATE TABLE users_pinboards_likes(
upl_id 	  	  INT(11) 	NOT NULL 	COMMENT '用户-愿望板-点赞id',
user_id	  	  VARCHAR(255)  NOT NULL  	COMMENT '用户id',
pinboard_id 	  VARCHAR(255) 	NOT NULL 	COMMENT '愿望板id',	
up_create_time 	  TIMESTAMP 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (upl_id),
FOREIGN KEY(user_id) REFERENCES users(user_id),
FOREIGN KEY(pinboard_id) REFERENCES pinboards(pinboard_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户对愿望板的点赞记录表';
```

## 搭建 springboot web 项目

[搭建参考](https://www.bilibili.com/video/BV1XK4y1W7jB?p=73&spm_id_from=pageDriver)

### 导入项目所需依赖

```xml
<!-- 子项目中导入 -->
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.1</version>
</dependency>

<!--父项目中导入公共依赖-->
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

```

### 配置 application.yml 文件

```yaml
# 默认总配置文件
# 激活配置
spring:
  profiles:
    active: dev
---
# 指定该环境下的端口
server:
  port: 8080
# 指定环境为：开发环境
spring:
  profiles: dev
  #配置应用程序名称
  application:
    name: DreamTreeSharer
  #配置数据源
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/dream_tree_sharer?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8
    username: root
    password: root
    #配置数据库连接池
    hikari: #springboot内嵌的连接池，速度快
      pool-name: DreamTreeSharerDataPool
      #最小空闲连接数
      minimum-idle: 5
      #空闲连接存活最大时间，默认600000ms(10分钟)
      maximum-pool-size: 180000
      #从连接池返回的连接自动提交
      auto-commit: true
      #连接最大存活时间，0表示永久存活，默认1800000(30分钟)
      max-lifetime: 1800000
      #连接超时时，默认是30000(30秒)
      connection-timeout: 30000
      #测试连接是否可用的查询语句
#日志级别配置
logging:
  level:
    # spring框架中的日志级别
    org.springframwork.web: info
    # 配置自己项目包下的日志级别
    com.ITIS: info
  #指定 D盘 生成日志文件 spring.log 为默认输出文件
  file:
    path: D:/DreamTreeSharer
    # 指定日志输出格式
  #  pattern:
  #    console: %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{50} %msg%n
  #    file: %d{yyyy-MM-dd HH:mm:ss} [%thread] --- %-5level %logger{50} --- %msg%n

#  #清除模板引擎缓存 - 然后 ctrl+f9 重新编译页面
#  thymeleaf:
#    cache: false

  #日期格式配置
  mvc:
    date-format: yyyy-MM-dd HH:mm:ss

# mybatis-plus 配置
mybatis-plus:
  # 配置 mapper 映射文件
  mapper-locations: classpath*:/mapper/*Mapper.xml
  # 配置mybatis数据返回各实体存放路径
  type-aliases-package: com.ITIS.DreamTreeSharer.entity
  configuration:
    # 开启自动驼峰命名
    map-underscore-to-camel-case: true
---
# 指定环境为：生产环境
spring:
  profiles: prod
# 指定该环境下的端口
server:
  port: 80
```

### 建立 springboot 项目入口类

```java
package com.ITIS.DreamTreeSharer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName DreamTreeSharerApp
 * @Author LCX
 * @Date 2021 2021-03-26 2:15 p.m.
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan("com.ITIS.DreamTreeSharer.dao")
public class DreamTreeSharerApp {
    public static void main(String[] args) {
        SpringApplication.run(DreamTreeSharerApp.class,args);
    }
}
```

### 建立mybatis-代码生成器子项目 DreamTreeSharer-generator

#### 导入依赖

[代码生成器-mybatis官网参考](https://baomidou.com/guide/generator.html#%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B)



```xml
<dependencies>
        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--        代码逆向生成-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.1</version>
        </dependency>
        <!--        添加 代码生成器 依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.4.1</version>
        </dependency>
        <!--        a driver that implements the Java Database Connectivity (JDBC) API -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
<!--        模板引擎-->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.31</version>
        </dependency>
    </dependencies>
```

#### 配置 CodeGenerator 并运行

```java
package com.ITIS.DreamTreeSharer;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 按配置生成文件
     * @param args
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //----------------------全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/DreamTreeSharer-generator/src/main/java");
        gc.setAuthor("SummerLv");
        //打开输出目录
        gc.setOpen(true);
        //xml 开启 BaseResultMap
        gc.setBaseResultMap(true);
        //xml 开启 BaseColumnList
        gc.setBaseColumnList(true);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        //添加到 mpg 中
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        //----------------------数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/dream_tree_sharer?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
        //数据库 schema name 例如 PostgreSQL 可指定为 public
        dsc.setSchemaName("MySQL");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // ----------------------包配置
        PackageConfig pc = new PackageConfig();
        //DreamTreeSharer-generator
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.ITIS.DreamTreeSharer");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
        pc.setEntity("entity");
        mpg.setPackageInfo(pc);

        // ----------------------自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // ----------------------自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/DreamTreeSharer-generator/src/main/resources/mapper" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //配置驼峰命名自动转换
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        //如果数据库有表前缀，而自动生成时不需要则添加此项
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
```

#### 将生成的 entity,service 等各层的文件移入到 DreamTreeSharer-backend 中

#### 导入剩下的依赖

```xml
<!--        swagger2 依赖-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.7.0</version>
        </dependency>
<!--        Swagger第三方Ui依赖-->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
            <version>1.9.6</version>
        </dependency>
<!--        security依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
<!--        JWT 依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>
```

#### 添加 JWT 配置

```yaml
#jwt配置
jwt:
  # JWT存储的请求头
  tokenHeader: Authorization
  #JWT 加解密使用的密钥
  secret: ITIS.com
  #JWT的超期限时间
  expiration: 6048000
  #JWT 负载中拿到开头
  tokenHead: ITIS
```

### 创建 JwtTokenUntil 用于用户名验证

```java
package com.ITIS.DreamTreeSharer.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtTokenUtil
 * @Author LCX
 * @Date 2021 2021-03-26 6:00 p.m.
 * @Version 1.0
 **/
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "SummerLv";  //荷载
    private static final String CLAIM_KEY_CREATED = "CreateTime"; //创建时间

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;  //token 过期时间

    /**
     * 根据用户信息生成加密Token - 加密
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据荷载生成JWT Token - 加密
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)  //添加荷载
                .setExpiration(generateExpirationDate())  //设置失效时间
                .signWith(SignatureAlgorithm.HS512, secret) //设置加密算法
                .compact(); //使用序列化压缩为 url 安全的字符串
    }

    /**
     * 生成token失效时间 - 设置 token 存活时间
     * @return
     */
    private Date generateExpirationDate() {
        //失效时间 =  当前系统的时间 + 配置的有效时间 24h
        return new Date(System.currentTimeMillis() + expiration*1000);
    }

    /**
     * 从token中获取用户名 - 解密
     * @param token
     * @return
     */
    private String getUserNameFromToken(String token) {
        String username;
        //需要先获取荷载，因为用户名存放在荷载中
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            //如果获取用户名出现异常，username = null
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取荷载 - 解密
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        /**
         * 判断token是否有效主要做两个判断
         * 1.判断token中username是否等于UserDetails中的 username
         * 2.判断token是否已经过期
         */
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        /**
         * 1.获取token的过期时间
         * 2.判断token的过期时间是否大于在当前时间的前面
         */
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }


    /**
     * 从token里面获取失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        //如果token已经过期则可以刷新
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        /**
         * 刷新token只需要将token的生成时间改成当前时间，然后再重新生成token即可。
         */
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
```

### swagger2 注解使用

[swagger 注解使用说明]: https://blog.csdn.net/u014231523/article/details/76522486

### 引入验证码功能

#### 导入依赖

```xml
<!-- https://mvnrepository.com/artifact/com.github.axet/kaptcha -->
<dependency>
    <groupId>com.github.axet</groupId>
    <artifactId>kaptcha</artifactId>
    <version>0.0.9</version>
</dependency>
```

#### 验证码配置

```java
package com.ITIS.DreamTreeSharer.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @ClassName CaptchaConfig
 * @Author LCX
 * @Date 2021 2021-03-31 1:30 p.m.
 * @Version 1.0
 **/
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        //验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //配置
        Properties properties = new Properties();
        //是否有边框
        properties.setProperty("kaptcha.border", "yes");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color",  "105,179,90");
        //边框粗细度，默认为1
        //properties.setProperty("kaptcha.border.thickness", "1");
        //验证码
        properties.setProperty(" kaptcha.session.key", "code");
        //验证码文本字符颜色，默认黑色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        //字体大小，默认40
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        //验证码文本字符内容范围，默认为abcdfadfa....
        properties.setProperty("kaptcha.textproducer.char.string","");
        //字符长度，默认5
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字符间距
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        //验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width", "100");
        //验证码高度图片
        properties.setProperty("kaptcha.image.height", "40");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
```

#### 验证码处理 controller

```java
package com.ITIS.DreamTreeSharer.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName CaptchaController
 * @Author LCX
 * @Date 2021 2021-03-31 1:35 p.m.
 * @Version 1.0
 * 获得 captcha 存入 session 中
 **/
@RestController
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        //Set standard Http/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        //Set IE extended Http/1.1 no-cache headers(use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        //Set standard Http/1.0 no-cache header
        response.setHeader("Pragma", "no-cache");
        //return a jpeg
        response.setContentType("image/jpeg");

        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        logger.info("验证码:" + text);
        //将验证码放入到session中
        request.getSession().setAttribute("captcha", text);
        //根据验证码文本生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(text);
        //获取响应输出流，输出给用户
        ServletOutputStream outputStream = null;
        try{
            outputStream = response.getOutputStream();
            //输出流输出图片
            ImageIO.write(image, "jpg", outputStream);
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try{
                    outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
```

## 添加七牛云对象存储功能（图片存储）- 获取token

[七牛云上传下载操作指南](https://developer.qiniu.com/kodo/kb/1336/upload-download-instructions)

[Java SDK](https://developer.qiniu.com/linking/6279/linking-java-sdk#install-by-maven)

### 生成一个 bucket

[创建 Bucket](https://developer.qiniu.com/kodo/1382/mkbucketv3)

创建前需要进行实名认证

创建的bucket名字：`itisummer-huanan-bucket`

<img src="C:\Users\LCX\AppData\Roaming\Typora\typora-user-images\image-20210416152912631.png" alt="image-20210416152912631" style="zoom:80%;" />

创建 bucket 成功

<img src="C:\Users\LCX\AppData\Roaming\Typora\typora-user-images\image-20210416164640694.png" alt="image-20210416164640694" style="zoom:80%;" />

### 添加 maven 依赖

```xml
<!-- 七牛云 Java sdk-->
<dependency>
    <groupId>com.qiniu</groupId>
    <artifactId>qiniu-java-sdk</artifactId>
    <version>[7.2.0, 7.2.99]</version>
</dependency>
```

### 编写获取 token

[java调用qiniu七牛云空间](https://blog.csdn.net/visket2008/article/details/77164233)

<img src="qrne6et6u.hn-bkt.clouddn.com/test.jpg"/>

## 2021年4月23日 遇到错误 

```java
Cannot construct instance of `com.ITIS.DreamTreeSharer.model.UsersModel` (no Creators, like default constructor, exist): cannot deserialize from Object value
```

本来想将 LoginModel 里面的内容全部添加到 UsersModel 里面，因为感觉这样整体性更强一点，但是这时候遇到以上错误，我分析了下，可能是我的 LoginModel 里面没有其他比较咋的数据，只是定义了字段，然后使用了 lombook，这样它能够让开发者不用手动写 getter，setter啥的！但是我的 UsersModel 里面实现了 UserDetails（spring security 相关） 然后里面写了个带参构造函数！我想就是因为手动写了构造函数造成的这个错误吧！具体看以下链接！

[No Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator](https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu)

我看这里面的解决办法是：手动加个无参构造函数就好了！**但是我这里不行！**

## 配置 springboot 热部署

[SpringBoot配置devtools实现热部署](https://www.cnblogs.com/lspz/p/6832358.html)

### 引入依赖

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<optional>true</optional>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
</dependencies>

<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<configuration>
				<fork>true</fork>
			</configuration>
		</plugin>
	</plugins>
</build>
```

### 配置项目文件

```properties
#热部署生效
spring.devtools.restart.enabled: true
#设置重启的目录
spring.devtools.restart.additional-paths: src/main/java
#classpath目录下的WEB-INF文件夹内容修改不重启
spring.devtools.restart.exclude: WEB-INF/**
```

### idea 设置保存自动编译

1. File-Settings-Compiler-Build Project automatically

2. ctrl + shift + alt + /,选择Registry,勾上 Compiler autoMake allow when app running

## 2021年4月24日 错误：javax.net.ssl.SSLHandshakeException: PKIX path building failed

情况是这样的，我想给我的项目弄一个注册时短信发送功能，于是我想到了我前面大创项目中也有用到过，我就去 copy 了里面的短信发送开发包！在我适配好我的项目后，我就开始测试，但是这时候给我报了个以上错误，我搜了下是跟 网站 ssl 证书有关！我使用的短信发送接口平台是 [https://www.kewail.com/](https://www.kewail.com/) 我尝试了这两个解决办法 

[1. 解决Java在请求某些不受信任的https网站时会报：PKIX path building failed](https://blog.csdn.net/qq_34836433/article/details/78539009) 

[2. 解决 javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path buildin](https://blog.csdn.net/chaishen10000/article/details/82992291)

我推荐第二个解决办法，操作不复杂，同时也会解决报错问题！然后我的程序正常向我使用的短信平台发送短信请求要求了，但是我该平台给我反馈的信息是：我没进行实名认证，所以发送不了！我天，我测试下，还要实名认证，现在都弄得太严了吧！于是我就放弃了短信发送功能，转而从后台生成六位数验证码后，返回给前端，以达到测试的目的！

于是我移除了这个短信接口平台的开发包，转而使用了另一个，另一个定制性没那么强，但是对于开发测试还是够用了！

[容联通讯-模板短信业务接口](https://doc.yuntongxun.com/p/5a533de33b8496dd00dce07c#h3-5-2.2%20%E8%AF%B7%E6%B1%82%E5%8C%85%E4%BD%93)

[免费测试](http://doc.yuntongxun.com/space/5a5098313b8496dd00dcdd7f)

[cloopen -  java-sms-sdk - github](https://github.com/cloopen/java-sms-sdk)

[尚硅谷Vue项目(vue实战谷粒外卖)-发送短信验证码](https://www.bilibili.com/video/BV1hs411E7cB?p=38&spm_id_from=pageDriver)

以上是我的参考

另外，我把我的适配好我的项目的代码给贴下：

先 maven 导入开发依赖：

```xml
<dependency>
    <groupId>com.cloopen</groupId>
    <artifactId>java-sms-sdk</artifactId>
    <version>1.0.4</version>
</dependency>
```

然后编写适配自己项目的接口：

```java
/**
 * @ClassName SmsSDKDemo1
 * @Author LCX
 * @Date 2021 2021-04-26 12:17 p.m.
 * @Version 1.0
 * [模板短信业务接口](https://doc.yuntongxun.com/p/5a533de33b8496dd00dce07c)
 **/
public class SmsSDKDemo1 {
 public static boolean sendSms(String phone, String code) {
   //生产环境请求地址：app.cloopen.com
    String serverIp = "app.cloopen.com";
    //请求端口
    String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    String accountSId = "填写你的账户 id";
    String accountToken = "填写你的账户 token";
    //请使用管理控制台中已创建应用的APPID
    String appId = "填写你的 appid，默认该平台会分配一个测试的 id";
    CCPRestSmsSDK sdk = new CCPRestSmsSDK();
    sdk.init(serverIp, serverPort);
    sdk.setAccount(accountSId, accountToken);
    sdk.setAppId(appId);
    sdk.setBodyType(BodyType.Type_JSON);
    // 测试的 templateId 是 1
    String templateId= "1";
    // 用于替换模板中的{序号}
    // 短信验证码 code
    String[] datas = {code,"1"};
    // 正式发送
    HashMap<String, Object> result = sdk.sendTemplateSMS(phone,templateId,datas);
    if("000000".equals(result.get("statusCode"))){
        //正常返回输出data包体信息（map）
        System.out.println("短信发送成功！");
        return true;
    }else{
    	//异常返回输出错误码和错误信息
    	System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
    	return false;
    }
 }
}
```

