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
            errorMvcAutoConfiguration中的defaultErrorAttributes会添加错误信息
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