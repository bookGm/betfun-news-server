

package io.information;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.guansuo.common.JsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication(scanBasePackages = {"io.information","io.elasticsearch","io.mq"})
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
@EnableFeignClients
public class BetApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(BetApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(SpringApplication.class);
	}
	@Bean
	public HttpMessageConverters customConverters() {
		// 先转换为String类型，避免@ResponseBody 返回的 json 带有转义字符1
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setDefaultCharset(Charset.forName("UTF-8"));

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// 日期时间格式化
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setSerializerFeatures(JsonUtil.features);
		SerializeConfig serializeConfig = fastJsonConfig.getSerializeConfig();
		serializeConfig.put(Long.class, new ObjectSerializer() {
			@Override
			public void write(JSONSerializer serializer, Object object,
							  Object fieldName, Type fieldType, int features) throws IOException {
				SerializeWriter out = serializer.getWriter();
				out.writeString(Objects.toString(object, null));
			}

		});
		fastJsonConfig.setSerializeConfig(serializeConfig);
		//附加：处理中文乱码
		List<MediaType> fastMedisTypes = new ArrayList<MediaType>();
		fastMedisTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMedisTypes);

		fastConverter.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastConverter,stringConverter);
	}
}