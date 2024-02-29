package org.snubi.did.resolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;

@ServletComponentScan
@EnableJpaAuditing(auditorAwareRef="loginUserAuditorAware") // JPA Auditing을 활성화
@SpringBootApplication
public class DidResolverApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		//SpringApplication.run(DidResolverApplication.class, args);		
		ApplicationContext ctx = SpringApplication.run(DidResolverApplication.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}
}


