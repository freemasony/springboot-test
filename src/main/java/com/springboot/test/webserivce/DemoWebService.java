package com.springboot.test.webserivce;


import com.springboot.test.common.webServiceResult;

import javax.jws.WebService;

@WebService(name = "DemoWebService",targetNamespace = "http://webserivce.test.springboot.com")
public interface DemoWebService {

    public webServiceResult sayHello(String name);
}
