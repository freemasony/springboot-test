package com.springboot.test.webserivce;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;

/**
 * @author zhoujian
 * @date 2020/6/12
 */
@Component
public class WebServiceClient {

    public String invokeWebService(){
        String result=null;
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf
                .createClient("http://localhost:9010/demo/api?wsdl");
        // url为调用webService的wsdl地址
        QName name = new QName("http://webserivce.test.springboot.com", "sayHello");
        // namespace是命名空间，methodName是方法名
        String xmlStr = "aaaaaaaa";
        // paramvalue为参数值
        Object[] objects;
        try {
            objects = client.invoke(name, xmlStr);
            result=objects[0].toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
