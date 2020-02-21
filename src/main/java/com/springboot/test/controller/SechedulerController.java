package com.springboot.test.controller;

import com.springboot.test.service.SysJobTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoujian
 * @date 2020/2/21
 */
@Controller
@RequestMapping(value = "/task")
public class SechedulerController {

    private Logger logger = LoggerFactory.getLogger(SechedulerController.class);

    @Autowired
    private SysJobTaskService sysJobTaskService;


}
