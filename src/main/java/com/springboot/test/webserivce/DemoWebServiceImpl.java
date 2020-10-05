package com.springboot.test.webserivce;

import com.springboot.test.common.webServiceResult;

import javax.jws.WebService;

/**
 * @author zhoujian
 * @date 2020/6/12
 */
@WebService(serviceName = "DemoWebService", targetNamespace = "http://webserivce.test.springboot.com",
            endpointInterface = "com.springboot.test.webserivce.DemoWebService")
public class DemoWebServiceImpl implements DemoWebService {


    @Override
    public webServiceResult sayHello(String name) {
        webServiceResult result=webServiceResult.success();
        result.setData(name);
        return result;
    }
}
