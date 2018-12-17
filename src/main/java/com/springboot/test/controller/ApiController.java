package com.springboot.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhoujian on 2018/9/5.
 */
@Controller
public class ApiController {

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String  get(HttpServletRequest httpServletRequest, HttpServletResponse response, Model model) throws IOException {
        model.addAttribute("code","00001");
        return "test";
    }
}
