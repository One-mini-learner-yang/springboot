一·springBoot入门
    1.springBoot简介
        springBoot是简化spring开发的框架，对于spring技术栈的整合
    2.单体应用（ALL IN ONE）
        将所有代码写在一个应用里（传统项目），打成war包放入服务器中运行
    3.微服务
        一种架构风格，一个应用应该是一组小型服务，可以通过HTTP进行互通，通过不同的功能元素进行组合实现需求，每一个功能元素都是可独立替换和独立升级的
    4.入门hello world
        进行配置（详见pom文件）
        使用@SpringBootApplication定义为springBoot应用
        SpringApplication.run（主类的字节码，参数）来执行应用
        使用springBoot的maven插件maven会打成一个可执行的jar包
    5.pom文件分析
         <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.9.RELEASE</version>
          </parent>
          这个父依赖依赖于
          <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>1.5.9.RELEASE</version>
            <relativePath>../../spring-boot-dependencies</relativePath>
         </parent>
         本依赖定义了相关spring的依赖版本（此处仅截取一部分举例）
         <!-- Dependency versions -->
         		<activemq.version>5.14.5</activemq.version>
         		<antlr2.version>2.7.7</antlr2.version>
         		<appengine-sdk.version>1.9.59</appengine-sdk.version>
         		<artemis.version>1.5.5</artemis.version>
         		<aspectj.version>1.8.13</aspectj.version>
         在此依赖定义的依赖引入不需写版本，但是没有定义的还是需要写版本的


          <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          spring-boot-starter：springBoot的环境启动器
          spring-boot-starter-web中引入了web模块所需的所有依赖（如spring mvc的相关依赖）
          springBoot中有许多环境启动器（详情看官方文档，https://docs.spring.io/spring-boot/docs/2.1.13.BUILD-SNAPSHOT/reference/html/using-boot-build-systems.html#using-boot-starter）
          springBoot将各个环境所需的依赖抽取出来，封装进启动器，需要什么场景引入什么启动器
    6.@springBootApplication注解分析
        @springBootApplication：说明所标注的类为springBoot的主配置类
        @springBootApplication内使用的注解
            @SpringBootConfiguration：底层有@Configuration
            @EnableAutoConfiguration：开启自动配置，让springBoot自动进行配置
                @EnableAutoConfiguration使用了@AutoConfigurationPackage：底层使用@Import注解，将主配置类所在的包及子包扫描进spring容器
                @EnableAutoConfiguration使用@Import，导入EnableAutoConfigurationImportSelector（继承AutoConfigurationImportSelector：将当前环境所需自动配置类扫描以字符集合返回，之后导入对应的自动配置类）
                    AutoConfigurationImportSelector调用getCandidateConfigurations获取所需的自动配置类
                        getCandidateConfigurations调用SpringFactoriesLoader.loadFactoryNames
                            loadFactoryNames去spring-boot-autoconfigure的META-INF/spring.factories查找指定的自动配置类
    7.使用spring向导快速创建
         在联网状态下，idea等编译器支持使用spring Initializr创建项目
    8.@RestController注解：底层有@Controller和@ResponseBody。当类上同时写有@ResponseBody和@Controller可用该注解代替（在类上写@ResponseBody目的为让类下所有方法的返回值变为json数据传回）
    9.分析利用spring Initializr创建的springBoot项目
        主程序以自动创建
        resources下的文件夹
            static：存放所有静态资源
            templates：存放模板页面（由于springBoot使用嵌入式的tomcat，默认不支持jsp，所以会需要模板引擎）
            application.properties：在该文件进行调整springBoot的配置
二·springBoot配置
    配置文件：修改springBoot默认配置
    yaml文件：以数据为中心，比xml，json更适合配置文件
    yaml与xml对比（以配置端口为例）：
        yaml：server：
                    port：8081
        xml：<server>
                <port>8081</port>
             </server>
    yaml语法：k:(空格) v来表示一对键值对（：和v之间的空格必须有）
    以空格的缩进来控制层级关系：只要左对齐的一列数据，都是一个层级的
    各种写法详见application.yaml
三·springBoot日志
四·springBoot的web开发
五·springBoot和docker
六·springBoot的数据访问
七·springBoot启动配置原理
八·springBoot自定义starts
九·springBoot和缓存
十·springBoot和消息
十一·springBoot和检索
十二·springBoot和任务
十三·springBoot和安全
十四·springBoot和分布式
十五·springBoot和开发热部署
十六·springBoot和监控管理