package com.springboot.test.webserivce;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author zhoujian
 * @date 2020/6/12
 */
@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean registrationBean(){
        return new ServletRegistrationBean(new CXFServlet(),"/demo/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(){
        return new SpringBus();
    }

    @Bean
    public DemoWebService demoWebService(){
        return new DemoWebServiceImpl();
    }

    @Bean
    public Endpoint endpoint(){
        EndpointImpl endpoint=new EndpointImpl(springBus(),demoWebService());
        endpoint.publish("/api");
        return endpoint;
    }
}
