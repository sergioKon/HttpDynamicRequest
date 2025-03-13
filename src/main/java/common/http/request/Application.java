package common.http.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import rest.mainServlet.ServletReader;


@SpringBootApplication
@ComponentScan(basePackages = {"rest.controllers"})
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	public static class ServletConfig {

		@Bean
		public ServletRegistrationBean<ServletReader> customServlet() {
			ServletRegistrationBean<ServletReader> servletRegistrationBean =
					new ServletRegistrationBean<>(new ServletReader(), "/dataReader");
			servletRegistrationBean.setLoadOnStartup(1); //
			return servletRegistrationBean;
		}
	}
}
