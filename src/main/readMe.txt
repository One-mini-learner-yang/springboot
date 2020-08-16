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
    使用@ConfigurationProperties可以将在配置文件配置该类的属性加进容器中
    @ConfigurationProperties的属性prefix，告诉需要识别配置文件的那一部分（以属性值为开头的）
    @ConfigurationProperties和@Value的区别
        1.@ConfigurationProperties多个值注入，@Value为单个值注入
        2.@ConfigurationProperties支持松散绑定，@Value对与绑定名严格要求为配置文件中的名
        3.@ConfigurationProperties不支持el表达式，@Value支持
        4.@ConfigurationProperties支持数据校验（@Validated注解进行数据校验），@Value不支持
        5.@ConfigurationProperties支持复杂数据的注入（map，list），@Value不支持
    @ImportResource(locations = 配置文件名)：将自己创建的配置类能被识别使用
    但springBoot不推荐使用配置文件
    推荐使用配置类：
        @Configuration
        public class config {
           @Bean  //将该注解下的方法返回值加进容器中，名为方法名
            public String  hello(){
               return "";
           }
        }
    在主配置文件（包括yaml文件）支持使用占位符${}
        随机数：${random.xxx}
        引用前面配置的值：${引用的值的名称}
    profile多环境配置
        创建文件为application-环境名.properties
        由于默认是读取application.properties中的环境，所以切换环境时，要在application.properties中激活配置文件：spring.profiles.active=环境名
        （与.properties文件不同，yaml文件支持多文档块，所以不用创建多个配置文件，在一个文件配置多个环境（用文档块分开即可））
    配置文件自动识别的以下位置
        file:/config/
        file:/.
        classpath:/config/
        classpath:/
    从上到下是由优先级的，高优先级会覆盖低优先级的内容，对于不同内容，不同优先级之间会形成互补配置
    @ConditionalOnxx：满足条件则当前配置类生效（例：@ConditionalOnWebApplication：若为web项目，则该配置类生效）
    使用配置的总结，对于一些组件，springBoot会自动配置自动配置类，想要在properties/yaml配置相关属性，看相关自动配置类找属性即可
    在配置文件配置debug=true，可以在运行时日志上写那些自动配置类生效了
三·springBoot日志
    springBoot日志的使用：slf4j+logback
    导入jar包：slf4j-api.jar  logback-classic.jar logback-core.jar
    由于各种框架拥有自己的框架，若想要整合所有日志框架变成slf4j，先将框架内使用的日志框架剔除出去，官方文档提供相对应的整合jar包（详情查看官方文档http://www.slf4j.org/legacy.html）
    springBoot底层也是使用slf4j+logback日志：spring-boot-starter-web--->spring-boot-starter--->spring-boot-starter-logging下进行上述的操作，将日志框架整合成slf4j+logback
    日志的使用：Logger logger=LoggerFactory.getLogger(实例所在类的类名)
               logger.trace/debug/info/warn/error(级别：trace<debug<info<warn<error)
    springBoot默认级别为info及以上
    在配置文件处可配置级别：logging.level.root=
    在配置文件处可配置日志所在文件：logging.file=
四·springBoot的web开发
-----------------------------------------------------------------------------------------------------
if (!registry.hasMappingForPattern("/webjars/**")) {
				customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/")
						.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
			}
			-----------------------------------------------------------------------------------------
    springBoot引入静态依赖的方法是用webjars（也就是引入jar包的方式）
    网站webjars（类似maven网站）将一些流行的前端静态资源写成依赖，引用时就如源码一般，引用classpath:/META-INF/resources/webjars/（在jar包的/META-INF/resources/webjars/）
    对于自定义的静态资源文件（比如css，页面，图片）（被/**映射）
    springBoot默认的静态文件夹：
    -------------------------------------------------------------------------------------------------
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
    			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
    			-------------------------------------------------------------------------------------
        classpath:/MEB-INF/resource/
        classpath:/resource/
        classpath:/static/
        classpath:/public/
    也可使用spring.resources.static-locations=进行配置静态资源
    对于欢迎页来说（路径为项目根目录），在访问任何路径的（静态资源）的index.html
    模板引擎：静态html+动态数据（传统为jsp）
    springBoot不支持jsp，官方建议thymeleaf
    在thymeleaf自动配置类中，定义了视图解析器的前后缀（前缀：classpath:/templates/，后缀：.html）
    springMvc的自动配置原理
        @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
        而注解@EnableWebMvc中@Import({DelegatingWebMvcConfiguration.class})，所以当配置类注解了@EnableWebMvc，相当于全面接管了Mvc的配置，即自动配置类失效（实际开发不建议）
        ----------------------------------------------------------------------------------------------------------------
        public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
        			ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        			resolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
        			}


        public static void addBeans(FormatterRegistry registry, ListableBeanFactory beanFactory) {
            Set<Object> beans = new LinkedHashSet();
            beans.addAll(beanFactory.getBeansOfType(GenericConverter.class).values());
            beans.addAll(beanFactory.getBeansOfType(Converter.class).values());
            beans.addAll(beanFactory.getBeansOfType(Printer.class).values());
            beans.addAll(beanFactory.getBeansOfType(Parser.class).values());
        	------------------------------------------------------------------------------------------------------------
        以上代码说明在springBoot加载组件的时候（如解析器（比如视图解析器），转换器（格式转换器））是去找容器中所有的，也就是说，我们可以自定义这些组件放入容器中，让springBoot加载
    在springMvc时，我们可以通过xml形式扩展mvc（比如配置拦截器），在springBoot，我们可以使用相关配置类实现WebMvcConfigurationAdapter（现在过期了，建议实现WebMvcConfigurationSupport）
    案例见mvcConfiguration.java
    --------------------------------------------------------------------------------------------------------------------
    @Bean
    		@ConditionalOnMissingBean
    		@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
    		public LocaleResolver localeResolver() {
    			if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
    				return new FixedLocaleResolver(this.mvcProperties.getLocale());
    			}
    			AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    			localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
    			return localeResolver;
    		}
            ------------------------------------------------------------------------------------------------------------
            @ConditionalOnMissingBean说明springBoot的国际化支持会在容器中没有该类时进行自动配置(自动配置只会定义一种)，所以做国际化切换时要自定义国际化，需要将自定义的类放入容器中
    springBoot的错误流程
        产生错误，errorMvcAutoConfiguration中的ErrorPageCustomizer就会生效（定制错误的响应规则）；就会来到/error请求
        ----------------------------------------------------------------------------------------------------------------
        @Override
        	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        		Map<String, Object> errorAttributes = new LinkedHashMap<>();
        		errorAttributes.put("timestamp", new Date());
        		addStatus(errorAttributes, webRequest);
        		addErrorDetails(errorAttributes, webRequest, includeStackTrace);
        		addPath(errorAttributes, webRequest);
        		return errorAttributes;
        	}
        	------------------------------------------------------------------------------------------------------------
            errorMvcAutoConfiguration中的defaultErrorAttributes会添加错误信息,所以，当我们自定义错误信息，继承defaultErrorAttribute即可
            ------------------------------------------------------------------------------------------------------------
            @Bean
            	@ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
            	public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,
            			ObjectProvider<ErrorViewResolver> errorViewResolvers) {
            		return new BasicErrorController(errorAttributes, this.serverProperties.getError(),
            				errorViewResolvers.orderedStream().collect(Collectors.toList()));
            	}
            	--------------------------------------------------------------------------------------------------------
            	并交给basicErrorController（在方法的形参中）
            	--------------------------------------------------------------------------------------------------------
            	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
                	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
                		HttpStatus status = getStatus(request);
                		Map<String, Object> model = Collections
                				.unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
                		response.setStatus(status.value());
                		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
                		return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
                	}

                	@RequestMapping
                	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
                		HttpStatus status = getStatus(request);
                		if (status == HttpStatus.NO_CONTENT) {
                			return new ResponseEntity<>(status);
                		}
                		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
                		return new ResponseEntity<>(body, status);
                	}
                	----------------------------------------------------------------------------------------------------
                	存在两种处理方式：1.html（页面形式） 2.json（数据的形式）
                	----------------------------------------------------------------------------------------------------
                	return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
                	----------------------------------------------------------------------------------------------------
                	而在页面形式方法返回的视图解析器处，判断了是否存在错误的视图解析器，若不存在，则使用springBoot默认的
                	----------------------------------------------------------------------------------------------------
                	@Override
                    	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
                    		ModelAndView modelAndView = resolve(String.valueOf(status.value()), model);
                    		if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
                    			modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
                    		}
                    		return modelAndView;
                    	}
                	private ModelAndView resolve(String viewName, Map<String, Object> model) {
                    		String errorViewName = "error/" + viewName;
                    		TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName,
                    				this.applicationContext);
                    		if (provider != null) {
                    			return new ModelAndView(errorViewName, model);
                    		}
                    		return resolveResource(errorViewName, model);
                    	}

                    	private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
                    		for (String location : this.resourceProperties.getStaticLocations()) {
                    			try {
                    				Resource resource = this.applicationContext.getResource(location);
                    				resource = resource.createRelative(viewName + ".html");
                    				if (resource.exists()) {
                    					return new ModelAndView(new HtmlResourceView(resource), model);
                    				}
                    			}
                    			catch (Exception ex) {
                    			}
                    		}
                    		return null;
                    	}
                    	------------------------------------------------------------------------------------------------
                    	resolveErrorView方法调用resolve判断是否存在为error/状态码的自定义的页面。如果页面为模板引擎的，创建视图解
                    	析器，若没找到模板引擎，会在静态资源处找是否存在为error/状态码的html页面，若存在，创建视图解析器。若都没有，
                    	则会使用springBoot默认的视图解析器
     springBoot添加servlet，Filter，listener三大组件（以Filter为例）
     1.创建Filter类（实现Filter接口）
     2.自定义FilterRegistrationBean(xxxRegisterBean,这仅是一个注册器，在注册器中需要加入对应的组件类)，并将其放入容器中
     -------------------------------------------------------------------------------------------------------------------
         @Bean
         public FilterRegistrationBean filterRegistrationBean()
         {
             FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
             filterRegistrationBean.setFilter(new Myfilter());
             filterRegistrationBean.setUrlPatterns(Arrays.asList("/*","/jsp/login.jsp"));
             return filterRegistrationBean;
         }
         ---------------------------------------------------------------------------------------------------------------
     springBoot的嵌入式servlet
        springBoot使用的嵌入式servlet容器支持tomcat，jetty，underTow，由于web的starter（依赖）默认使用tomcat，再切换时需要将tomcat
        相关依赖忽略，再加入其他jar包
        ----------------------------------------------------------------------------------------------------------------
        @Configuration(proxyBeanMethods = false)
        	@ConditionalOnClass({ Server.class, Loader.class, WebAppContext.class })
        	public static class JettyWebServerFactoryCustomizerConfiguration {

        		@Bean
        		public JettyWebServerFactoryCustomizer jettyWebServerFactoryCustomizer(Environment environment,
        				ServerProperties serverProperties) {
        			return new JettyWebServerFactoryCustomizer(environment, serverProperties);
        		}

        	}
        	------------------------------------------------------------------------------------------------------------
            （以jetty为例）
            由于springBoot底层通过判断是否存在相关servlet容器的类来进行创建serverFactory，所以引入相对应的jar包即可使用
     springBoot使用外置servlet
        对于springBoot的内置servlet容器优点很明显，轻松使用，不需繁重的配置
        但是，由于springBoot默认不支持jsp，所以再某些情况会使用外置的servlet容器
        步骤如下：
            在创建项目时，将打包方式从jar变成war（因为外置的servlet容器通过运行war包的方式进行部署）
            进行设置外置的servlet容器，并添加相应的文件列表（webapp）
            ------------------------------------------------------------------------------------------------------------
            // 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
            public class SpringBootStartApplication extends SpringBootServletInitializer {

                @Override
                protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
                    // 注意这里一定要指向原先用main方法执行的Application启动类
                    return builder.sources(Application.class);
                }
            }
            ------------------------------------------------------------------------------------------------------------
            在SpringBootApplication的同级创建一个类继承SpringBootServerInitializer，重写configure方法，将springBoot启动类传进去
            即可使用外置的servlet容器
五·springBoot和docker
    什么是docker：docker是一种应用容器引擎，将安装配置好的软件编译成一个镜像，在另一个服务器上使用docker可以使用这个镜像，实现将编译
    的软件快速的应用在该服务器上
    常用命令
    启动docker
    systemctl start docker
    查看版本
    docker --version
    开机自启
    systemctl enable docker
    从docker的docker hub上搜索对应软件
    docker search 软件名
    从docker的docker hub上拉取对应软件（类似maven）
    docker pull 软件名：版本号（若不写版本号，默认为最新版本）
    查看本地的docker镜像
    docker images
    删除本地的docker镜像
    docker rmi 镜像id（可在上条语句中查到）
    运行docker镜像
    dockers run --name 自己定义容器名 -d（后台运行） 运行的镜像名 （若有其他配置，该语句的书写请参考官方书写）
    （例如tomcat的启动 docker run --name mytomcat -p 8080:8080(将主机的端口映射给容器端口，即将容器中定义的端口和主机端口绑定，冒号前为主机端口) -d tomcat）
    （例如mysql的启动 docker run -p 3306:3306 --name mysql -v /my/custom:/etc/mysql/conf.d（将主机上的文件夹和容器中的文件相映射） -e MYSQL_ROOT_PASSWORD=root(mysql密码) -d mysql:tag）
    查看运行中的容器
    docker ps（若想查看全部容器 docker ps -a）
    结束运行的的容器
    docker stop 容器名（运行时自己定义的）
    开启容器
    docker start 容器名（运行时自己定义的）
    删除容器
    docker rm 容器名（运行时自己定义的）
    查看容器日志
    docker logs 容器id
    进入容器的内部文件结构（容器得事先运行）
    docker exe -it 容器id（容器名） /bin/bash
    退出bash模式
    ctrl+p+q：不结束容器运行
    ctrl+d：结束容器运行
    从容器内部文件加拷贝文件到宿主电脑
    docker cp 容器名:文件路径  想要复制到的宿主电脑位置（同样，将宿主电脑文件复制到容器文件夹中，将二者位置调换即可）
    在docker容器内部使用vim应先下载vim
    apt-get update、apt-get install vim
    使用阿里容器管理进行加速，将加速的配置内容加入/etc/docker/daemon.json//使用docker中国镜像加速进行docker下载速度更快
六·springBoot的数据访问
------------------------------------------------------------------------------------------------------------------------
    @ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)


    @ConditionalOnMissingBean(DataSource.class)
    	@ConditionalOnProperty(name = "spring.datasource.type")
    --------------------------------------------------------------------------------------------------------------------
    springBoot默认支持的数据源：org.apache.tomcat.jdbc.pool.DataSource、HikariDataSource、BasicDataSource（引入jar包即可用）
    也可使用自定义，在配置文件中定义即可
    Druid的整合和应用
        1.引入Druid的jar包
        2.在配置文件进行数据源的配置
    Druid的监控
        1.在配置类中新建数据源（使用@ConfigurationProperties注解将相关配置注入（因为springBoot不默认支持Druid数据源））
        2.在配置类中配置监控的servlet（在注册器中添加StatViewServlet）
        3.在配置类中配置拦截器（关于的监控的拦截器）
     mybatis整合
        1.引入mybatis-springBoot-starter（mybatis官方写的）
        2.按照mybatis的使用方式（注解版不用配置数据源了，因为在之前配置过了）
     mybatis的缓存
        首先介绍mapper的运行机制
            mapper是由sqlSession.getMapper获取的，再根据mapper中的方法的参数值，返回值，调用sqlSession不同的方法，对于同一sqlSession中的同一数据访问会将第一次写如缓存，之后读取缓
        由于spring对于处理一次数据访问时都去申请一个sqlSession（访问结束释放资源），所以在spring环境下mybatis的一级缓存失效了
七·springBoot启动配置原理
    在springBoot项目启动过程中注意一下四个事件的回调机制：
        配置在/META-INF:ApplicationContextInitializer     SpringApplicationRunListener
        放在IOC中的：ApplicationRunner     CommandLineRunner
    启动过程：
        创建SpringApplication对象：保存主配置类，判断是否是一个web应用，读取在类路径下的/META-INF/spring.factories中配置的ApplicationContextInitializer  ApplicationListener并保存，在多个配置类中找到带main方法的主配置类
        执行run方法：在/META-INF/spring.factories中获取SpringApplicationRunListener，执行SpringApplicationRunListener.starting()，准备环境，环境准备完成之后，执行SpringApplicationRunListener.environmentPrepared()，创建ApplicationContext，决定创建普通ioc还是web的ioc，
        将准备好的上下文环境放进ioc，回调ApplicationContextInitializer.initializer()，执行SpringApplicationRunListener.contextLoaded()，刷新ioc，初始化ioc，在ioc中获取全部ApplicationRunner     CommandLineRunner并回调其方法，回调SpringApplicationRunListener.finished()。
八·springBoot自定义starts
    自定义starter模式：启动器仅作为依赖引入，独自创建专门写自动配置的模块，由使用方引入启动器，启动器引入自动配置模块
    步骤：启动器引入自动配置依赖模块pom文件
    编写自动配置模块
      @Configuration  //指定这个类是一个配置类
      @ConditionalOnXXX  //在指定条件成立的情况下自动配置类生效
      @AutoConfigureAfter  //指定自动配置类的顺序
      @Bean  //给容器中添加组件

      @ConfigurationPropertie结合相关xxxProperties类来绑定相关的配置
      @EnableConfigurationProperties //让xxxProperties生效加入到容器中

      自动配置类要能加载
      将需要启动就加载的自动配置类，配置在META-INF/spring.factories
      org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
      配置类根路径
九·springBoot和缓存
    1.JSR107
        java定义的规范，定义了5个接口，分别是CachingProvider, CacheManager, Cache, Entry和 Expiry。
        为了简化开发，spring支持注解开发，每次调用需要缓存功能的方法时，Spring会检查检查指定参数的指定的目标方法是否已经被调用过；如果有就直接从缓存中获取方法调用后的结果，如果没有就调用方法并缓存结果后返回给用户。下次调用直接从缓存中获取。
    2.缓存注解（重要概念）以及其注解中主要的参数
        Cache：缓存接口，定义缓存操作，子接口有：RedisCache、EhCacheCache、ConcurrentMapCache
        CacheManager：缓存管理器，管理各种缓存组件
        @Cacheable：主要针对方法的配置，能根据方法请求的参数对其结果进行缓存
        @CacheEvict：清空缓存（用于删除操作）
        @CachePut：保证方法被调用，并把结果放入缓存（用于更新操作）
        @EnableCaching：开启注解缓存开发
        注解参数
            @Cacheable/@CacheEvict/@CachePut
                value：缓存的名称，在spring的配置文件中定义，必须指定至少一个
                例如：
                @Cacheable(value=”mycache”) 或者
                @Cacheable(value={”cache1”,”cache2”}
                key：缓存的key，可以为空，为空情况按照方法的所有参数进行组合
                例如：
                @Cacheable(value=”testcache”,key=”#userName”)
                condition：缓存条件，可以为空，当该参数内的条件为true时才进行缓存/清除缓存，在调用前后都能判断
                例如：
                @Cacheable(value=”testcache”,condition=”#userName.length()>2”)
                sync：异步模式缓存，默认为false，当为true时unless不可用
            @CacheEvict
                allEntries：是否清空所有缓存内容，true为清空所有，默认false
                例如：
                @CachEvict(value=”testcache”,allEntries=true)
                beforeInvocation：是否在执行方法前执行，为true则在方法执行前执行，默认false，默认情况下，方法执行抛出异常不会清空缓存
                例如：
                @CachEvict(value=”testcache”，beforeInvocation=true)
            @CachePut/@Cacheable
                unless：缓存条件，与condition不同的是，只在方法执行后判断该参数内的条件（故常用在判断结果的条件），且只有参数内条件为false才会缓存
                例如：
                @Cacheable(value=”testcache”,unless=”#result == null”)
    3.整合redis
        1.环境整合
            在linux系统上的调整，关闭防火墙
            调整redis.config，注释bind 127.0.0.1（redis默认开启这条配置，仅允许本机访问），将protected-mode yes改为no（保护机制，默认为yes）
        2.相关类使用
            springBoot自动注入了RedisTemplate和StringRedisTemplate
            RedisTemplate：对于k-v都是对象的，采用的序列化为jdk序列化cel
            StringRedisTemplate：对于k-v是string-对象的，采用的序列化为string序列化策略
            二者都提供一下方法：
                opsForValue();//操作字符串
                opsForHash();//操作hash
                opsForList();//操作list
                opsForSet();//操作set
                opsForZSet();//操作有序set
           面对不同数据结构，选择相应方法下的操作方法即可。
        3.问题出现：当我们在用RedisTemplate缓存实体类对象时，由于jdk序列化策略保存的数据格式不是我们想要的。
        4.解决：自定义RedisTemplate<Object,实体类对象中>放入容器中，在配置中设置序列化策略为jackson2JsonRedisSerializer（json序列化策略）
    4.redis做缓存原理
        ----------------------------------------------------------------------------------------------------------------
        mappings.put(CacheType.GENERIC, GenericCacheConfiguration.class);
        mappings.put(CacheType.EHCACHE, EhCacheCacheConfiguration.class);
        mappings.put(CacheType.HAZELCAST, HazelcastCacheConfiguration.class);
        mappings.put(CacheType.INFINISPAN, InfinispanCacheConfiguration.class);
        mappings.put(CacheType.JCACHE, JCacheCacheConfiguration.class);
        mappings.put(CacheType.COUCHBASE, CouchbaseCacheConfiguration.class);
        mappings.put(CacheType.REDIS, RedisCacheConfiguration.class);
        mappings.put(CacheType.CAFFEINE, CaffeineCacheConfiguration.class);
        mappings.put(CacheType.SIMPLE, SimpleCacheConfiguration.class);
        mappings.put(CacheType.NONE, NoOpCacheConfiguration.class);
        ----------------------------------------------------------------------------------------------------------------
        springBoot的CacheConfiguration会根据环境引入以上的类，当没有引入redis环境时，springBoot用的是SimpleCacheConfiguration.class
        将缓存数据加进ConcurrentMap中，加载SimpleCacheConfiguration.class进容器条件@ConditionalOnMissingBean(CacheManager.class)
        当加入redis环境后，会先加载 RedisCacheConfiguration.class，该类会在容器中加进RedisCacheManager，之后就不会加载SimpleCacheConfiguration.class
        实现redis做缓存的目的。
    5.自动配置cacheManager
        使用注解方式进行redis缓存，需要改变RedisCacheManager默认配置一些属性（比如序列化策略）
        在springBoot1.x版本中，对于redisCacheManager的自定义方式：
            //缓存管理器
            @Bean
            public CacheManager cacheManager(形参处引入对应处理的redisTemplate) {
                RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
                //设置缓存过期时间
                cacheManager.setDefaultExpiration(10000);
                return cacheManager;
            }    //缓存管理器
        在springBoot2.x版本后，RedisCacheManager取消了1.0版本中的public RedisCacheManager(RedisOperations redisOperations)的这个构造方法，所以我们无法再用。
        提供的构造器为：
             public RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
                    this(cacheWriter, defaultCacheConfiguration, true);
                }
        故，自定义的RedisCacheManager配置为：
             @Bean
                public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
                    //初始化一个RedisCacheWriter
                    RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
                    //设置CacheManager的值序列化方式为json序列化
                    RedisSerializer redisSerializer=new GenericFastJsonRedisSerializer();
                    RedisSerializationContext redisSerializationContext=RedisSerializationContext.fromSerializer(redisSerializer);
                    RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(redisSerializationContext.getValueSerializationPair());
                    //设置默认超过期时间是30秒
                    redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
                    RedisCacheManager redisCacheManager=new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
                    return redisCacheManager;
                }
十·springBoot和消息
    消息中间件用处：
        1.将数据写进消息队列中，想进行什么操作从消息队列中获取数据，实现异步处理
        2.使用消息队列作为各个组件的联系，实现应用的解耦
        3.由于消息队列的存储量有限，面对高访问量时（例如秒杀），可以作为访问的筛减，实现流量削峰
    消息中间件模式类型：
        点对点式：消息的发送者和消费者只有唯一一个（消息的一对一）
        发布订阅式：多个消费者同时订阅一个主题，发送者发送消息到主题时，这多个消费者会同时受到消息（消息的一对多）
    消息中间件的实现协议：
        1.JMS：是基于JVM消息代理的规范（ActiveMQ，HornetMQ是对JMS的实现）
            提供的消息模型：Peer-2-Peer和Pub/sub（点对点和发布订阅）
        2.AMQP：高级消息队列协议，兼容JMS（该协议是所有编程语言通用的协议）（RabbitMQ是对AMQP的实现）
            提供的消息模型：direct exchange，fanout exchange，topic change，headers exchange，system exchange（除了第一个是一对一的以外，后四个均为一对多的消息模型，仅在路由机制上更详细的划分）
    RabbitMQ：
        核心概念：
            Message
            消息，消息是不具名的，它由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组
            成，这些属性包括routing-key（路由键）、priority（相对于其他消息的优先权）、delivery-mode（指出
            该消息可能需要持久性存储）等。
            Publisher
            消息的生产者，也是一个向交换器发布消息的客户端应用程序。
            Exchange
            交换器，用来接收生产者发送的消息并将这些消息路由给服务器中的队列。
            Exchange有4种类型：direct(默认)，fanout, topic, 和headers，不同类型的Exchange转发消息的策略有
            所区别
            Queue
            消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息
            可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走。
            Binding
            绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连
            接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。
            Exchange 和Queue的绑定可以是多对多的关系。
            Connection
            网络连接，比如一个TCP连接。
            Channel
            信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚
            拟连接，AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这
            些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所
            以引入了信道的概念，以复用一条 TCP 连接。
            Consumer
            消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。
            Virtual Host
            虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享相同的身份认证和加
            密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的 RabbitMQ 服务器，拥有
            自己的队列、交换器、绑定和权限机制。vhost 是 AMQP 概念的基础，必须在连接时指定，
            RabbitMQ 默认的 vhost 是 / 。
            Broker
            表示消息队列服务器实体
        运行机制：
            发送者将消息发给交换器，交换器根据绑定的路由机制（路由键）将消息传给对应的消息队列，再由消费者从消息队列中取走消息
        交换机的类型：
            RabbitMQ提供了四种交换机类型：direct、fanout、topic、headers（其中。headers 匹配 AMQP 消息的 header 而不是路由键， headers 交换器和 direct 交换器完全一致，但性能差很多，目前几乎用不到了）
            direct：严格根据路由键和消息队列绑定的路由键完全匹配
            fanout：广播模式，将消息传给绑定在该交换器的所有消息队列上
            topic：支持类似模糊查询一样的机制进行一对多的消息传送，例如，路由键为yang.news的消息会发送到绑定的路由键为*。news和yang.#的消息队列中
            （注：其中*代表一个单词，#代表没有单词，一个单词或多个单词）
        springBoot的整合
            引入spring-boot-starter-amqp依赖
            RabbitAutoConfiguration中通过ConnectFactory实现读取配置来进行连接消息代理
            springBoot已经将rabbitTemplate注入，该类进行消息的传送与取出
                传送：rabbitTemplate.convertAndSend（可将任意类型的对象传入消息队列）
                取出：rabbitTemplate.receiveAndConvert（可将消息从队列中取出）
                （注：在传入消息时，由于默认使用的时java序列化策略，导致传入内容不是我们平常需要的json数据，可自定义MessageConvert，返回Jackson2JsonMessageConverter）
            使用@EnableRabbit开启rabbit注解开发
            使用@RabbitListener(queues = "yang.news")进行监控（监控也是消费者行为）
            （注；该注解的queues属性是数组类型，即可监控多个队列）
            在使用AmqpAdmin创建队列以及交换器时，将这个过程放在bean初始化中，及将该方法加@Bean注解（否则报错）
        rabbitMQ的特性
            1.消息确认机制（消费者）
                问题出现：如果在消费者代码实现过程中产生异常，则想要消息不会从队列中被消费
                解决：将默认的自动确认改为手动确认
                具体解决：
                    1.在配置文件中对rabbitMQ进行配置
                        # 开启发送确认
                        spring.rabbitmq.publisher-confirms=true
                        # 开启发送失败退回
                        spring.rabbitmq.publisher-returns=true
                        #开启ACK
                        spring.rabbitmq.listener.direct.acknowledge-mode=manual
                        spring.rabbitmq.listener.simple.acknowledge-mode=manual
                    2.在消费者接收消息业务代码结束后手动确认
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            2.消息的确认机制（生产者）
                问题出现：在生产者到交换器，交换器到队列的过程中，消息的传递准确性不是百分之百的
                解决：在消息传到交换器，队列时进行一个确认信息的发送
                具体实现：
                    一个类放入仓库，实现RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback，重写对应的confirm，returnedMessage方法
                    再使用@PostConstruct注解（该注解会在容器实例化之前进行），对rabbitTemplate进行设置，设置其confirm与return的回滚
            3.消息持久化
                问题出现：如果rabbit服务器宕机，需要将相关的交换器，队列，消息持久化
                解决：在交换器，队列的创建以及消息的发送时设置为持久化
                具体实现：
                    原生环境下：
                        在创建交换器，队列将durable（是否持久化）属性设为true
                        在消息发送时，将消息做持久化处理
                            channel.basicPublish("", queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                            //MessageProperties.PERSISTENT_TEXT_PLAIN表示持久化
                    在spring环境下：
                        在创建交换器，队列将durable（是否持久化）属性设为true
                        在消息发送时，不需要做处理
                            原因：rabbitTemplate.convertAndSend(exchange, routeKey, message);底层引用的 MessageProperties的默认持久化策略为MessageDeliveryMode.PERSISTENT（默认持久化）
            4.限流
                有时候业务要求会有限流要求
                实现：配置文件中配置
                        #在单个请求中处理的消息个数，他应该大于等于事务数量(unack的最大数量)
                        spring.rabbitmq.listener.simple.prefetch=2
            5.延时
                死信：一个消息超过了规定时间，无法被消费，就称这条消息为死信
                死信队列：一个死信被死信交换器转到死信队列
                延时的原理：设置一个消息的时间，时间到了，通过死信交换器转入死信队列，再从死信队列中取走消息
                实现：死信交换器和死信队列都是普通的交换器和队列，在创建消息经过的第一个队列时，设置死信交换器的名字以及转入死信队列的路由键
                            Map map=new HashMap();
                            map.put("x-dead-letter-exchange","dead_Exchange");
                            map.put("x-dead-letter-routing-key","receive_key");
                            amqpAdmin.declareQueue(new Queue("dead_Queue",true,false,false,map));
                     之后发送消息时，设置消息的ttl
                         rabbitTemplate.convertAndSend("dead_Exchange","dead_Message",map,message -> {
                                    message.getMessageProperties().setExpiration("10000");
                                    return message;
                                });
                     之后消费死信队列中消息即可。
十一·springBoot和检索
    ElasticSearch：是基于restful设计模式的搜索引擎
    restful：使用了restUrl代替了传统url
        传统url：localhost:8080/项目名/user?id=1    ====>参数采用问号连接
        restUrl：localhost:8080/项目名/user/1/      ====>参数作为url的一部分
    restful模式下，采用四种访问方式对应CRUD的四种操作，GET(查询) PUT(添加) POST(更新) DELETE(删除)
    springBoot对于restful的支持
        @GetMapping @PutMapping @PostMapping @DeleteMapping 四种注解对应四种请求方式
        对于参数的获取
            例：/search/12/
                @GetMapping("/search/{id}/")
                public void search（@PathVariable("id") int id）
    全文检索：以文本作为检索对象，找出相关词语的文本信息
    ElasticSearch的结构
        索引：过去版本官方比喻成数据库（后来修改，官方认为是个糟糕的比喻），一个索引是相似文档的集合
        类型：过去版本官方比喻成表（因此5.x版本前支持一个索引下多个类型，但后逐渐修改，7.x后完全不支持，目前一个索引下仅可有一个类型）
        映射：定义一个类型的约束，定义一个类型中数据的结构
        文本：类似表中的一条记录，是一份实际的数据信息
    ElasticSearch操作
        操作环境：可以使用postman按照不同请求方式执行不同操作，也可使用ES官方的Kibana（使用时注意kibana的版本要和ES版本一致）
        针对索引操作：
            创建索引：PUT /索引名
            查询索引：GET /_cat/indices?v
            删除索引：DELETE  /索引名
        针对类型操作
            创建类型：PUT /索引名/
                     {
                        "mappings":{
                            "类型名":{
                                "properties":{
                                    "字段名":{"type":"类型名"},
                                    "字段名":{"type":"类型名"}
                                }
                            }
                        }
                     }
                     注；若是使用postman，将上面json作为请求体（格式要用json）
                     ES提供了类型中属性的类型为：integer keyword boolean text date......(仅列举了几个)
            查看索引下类型：GET /索引名
            查看索引下类型约束：GET /索引名/_mapping
        针对文档操作：
            创建文档：PUT /索引名/类型名/文档id（唯一标识）
                     {
                        "字段名":"字段值",
                        "字段名":"字段值"
                     }
            查看文档：GET /索引名/类型名/文档id
            查看一个类型下所有文档：GET /索引名/类型名/_search
            修改文档：POST /索引名/类型名/文档id（该方法会将原文档删除，再将修改内容的文档创建，即不会保留原文档内容）
                     {
                        "字段名":"字段值"
                     }
                     POST /索引名/类型名/文档id/_update
                      {
                        "doc":{
                            "字段名":"字段值"
                            }
                      }
                      注：在修改文档时可以添加新的字段，但该新字段的类型由es自动规定
            批量修改文档：PUT /索引名/类型名/_bulk
                        {"index":{"_id":"文档id"}}
                            {"字段名":"字段值","字段名":"字段值"}
                        {"delete":{"_id":"文档id"}}
                        {"update":{"_id":"文档id"}}
                            {"doc":{"字段名":"字段值","字段名":"字段值"}

        高级查询操作
            在elasticsearch中提供两种查询方式：QueryString以及QueryDSL（前者直接将查询操作作为url参数，后者将查询条件写在请求体中）
            官方建议使用后者，且面对复杂查询条件时，前者是无法实现需求的，以下内容也为QueryDSL方法
            查询原理
                索引库分为两个区：索引区和元数据区
                每条数据对应一个唯一标识符，索引区根据分词（除文本类型外均不分词，在索引区以全部内容存在）
                查询时在索引区查询到唯一标识符，再在元数据区根据唯一标识符获取数据
            所有查询的url是固定的(故下面仅写明请求体中内容)
                GET /索引名/_search
            查询全部
                {
                    "query":{
                            "match_all":{}
                        }
                }
            排序查找（查询全部基础上）
                {
                    "query":{
                             "match_all":{}
                         },
                    "sort":{
                            "需要排序字段":"排序方式（默认升序）"
                        }
                }
            分页查找
                {
                    "query":{
                             "match_all":{}
                         },
                    "from":"查询开始位置",
                    "size":"查找条数"
                }
            相关内容
            {
                "query":{
                    "term":{
                        "查询字段":"相关内容"
                    }
                }
            }
            注：elasticsearch默认使用标准分词器，对于中文内容其分词方式为一个字一个字分，所以在这种情况下查询中文只能查询到一个字的相关内容（实际开发中对于中文内容的查询需要给elasticsearch添加中文的分词器，如ik分词器）
            区间查询
            {
                "query":{
                    "range":{
                        "查询字段":{
                            这里面写区间
                            "gte","gt":小于等于，小于
                            "lte","lt":大于等于，大于
                        }
                    }
                }
            }
            前缀查询(在索引库中找到对应字段为给定前缀的数据)
            {
                "query":{
                    "prefix":{
                        "字段":"前缀"
                    }
                }
            }
            通配符查询（类似数据库中模糊查询）（在索引库中找的符合条件的数据）
            {
                "query":{
                    "wildcard":{
                        "字段":"模糊查询内容"（其中通配符?表示一个字符，*表示任意字符）
                    }
                }
            }
十二·springBoot和任务
十三·springBoot和安全
十四·springBoot和分布式
十五·springBoot和开发热部署
十六·springBoot和监控管理