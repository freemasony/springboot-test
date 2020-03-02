package com.springboot.test.controller;

import com.springboot.test.aop.OperateLog;
import com.springboot.test.aop.OperateType;
import com.springboot.test.common.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhoujian
 * @date 2020/3/2
 */
@Controller
@RequestMapping(value = "/operate")
public class OperateLogController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @OperateLog(remark = "add",operateType = OperateType.add)
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult add() throws Exception {
        JsonResult rsult=new JsonResult();
        rsult.setCode("0000");
        rsult.setMsg("add");
        return rsult;
    }

    @OperateLog(remark = "update",operateType = OperateType.update)
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult update() throws Exception {
        JsonResult rsult=new JsonResult();
        rsult.setCode("0000");
        rsult.setMsg("update");
        return rsult;
    }


    @OperateLog(remark = "delete",operateType = OperateType.delete)
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult delete() throws Exception {
        JsonResult rsult=new JsonResult();
        rsult.setCode("0000");
        rsult.setMsg("delete");
        return rsult;
    }


    @OperateLog(remark = "query",operateType = OperateType.query)
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult query() throws Exception {
        JsonResult rsult=new JsonResult();
        rsult.setCode("0000");
        rsult.setMsg("query");
        return rsult;
    }
}
