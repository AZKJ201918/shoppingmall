package com.shopping.shoppingmall;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.shopping.shoppingmall.common.config.NettyServer;
import com.shopping.shoppingmall.common.constants.Parameters;
import com.shopping.shoppingmall.common.swagger.FastJsonHttpMessageConverterEx;

import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class ShoppingmallApplication{
//	 implements CommandLineRunner
//implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>

	@Value("${netty.port}")
	private int port;

	@Value("${netty.url}")
	private String url;

//	@Autowired
//	private NettyServer server;

	@Autowired
	private Parameters parameters;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingmallApplication.class, args);
	}


	//json 配置
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverterEx();
		List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
		return new HttpMessageConverters(converter);
	}

//	@Bean
//	//密码加密校验
//	public BCryptPasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public JestHttpClient getECSlient(){
//		JestClientFactory factory=new JestClientFactory();
//		factory.setHttpClientConfig(new HttpClientConfig.Builder(
//				"http://"+parameters.getEsHost())
//				.multiThreaded(true)
//				.readTimeout(5000)
//				.build());
//		JestHttpClient client= (JestHttpClient) factory.getObject();
//		return client;
//	}
//	@Override
//	public void run(String... args) throws Exception {
//		InetSocketAddress address = new InetSocketAddress(url,port);
//		System.out.println("run  .... . ... "+url);
//		server.start(address);
//	}


//
//	@Override
//	public void customize(ConfigurableWebServerFactory factory) {
//		factory.setPort(9999);
//	}
//
//
//	@Bean
//	public TomcatServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint constraint = new SecurityConstraint();
//				constraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				constraint.addCollection(collection);
//				context.addConstraint(constraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(httpConnector());
//		return tomcat;
//	}
////
////
//	@Bean
//	public Connector httpConnector() {
//		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//		connector.setScheme("http");
//		//Connector监听的http的端口号
//		connector.setPort(80);
//		connector.setSecure(false);
//		//监听到http的端口号后转向到的https的端口号
//		connector.setRedirectPort(9999);
//		return connector;
//	}



}
