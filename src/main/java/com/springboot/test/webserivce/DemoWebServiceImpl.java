package com.springboot.test.webserivce;

import javax.jws.WebService;

/**
 * @author zhoujian
 * @date 2020/6/12
 */
@WebService(serviceName = "DemoWebService", targetNamespace = "http://webserivce.test.springboot.com",
            endpointInterface = "com.springboot.test.webserivce.DemoWebService")
public class DemoWebServiceImpl implements DemoWebService {

    @Override
    public String sayHello(String name) {
        return name+",您好！";
    }
}
