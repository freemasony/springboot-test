package com.springboot.test.controller;

import com.springboot.test.common.JsonResult;
import com.springboot.test.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujian
 * @date 2020/4/7
 */
@Slf4j
@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(HttpServletRequest request, HttpServletResponse response){
        JsonResult rsult=new JsonResult();
        Map<String,Object> map=new HashMap<>();
        map.put("list",productService.findAll());
        rsult.setData(map);
        rsult.setCode("0000");
        rsult.setMsg("成功");
        return rsult;
    }
}
