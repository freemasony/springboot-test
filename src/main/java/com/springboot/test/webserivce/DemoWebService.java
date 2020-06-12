package com.springboot.test.webserivce;


import javax.jws.WebService;

@WebService(name = "DemoWebService",targetNamespace = "http://webserivce.test.springboot.com")
public interface DemoWebService {

    public String sayHello(String name);
}
