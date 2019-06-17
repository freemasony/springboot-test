package com.springboot.test.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.springboot.test.alipay.config.AlipayConfig;
import com.springboot.test.alipay.config.AlipayWapConfig;
import com.springboot.test.alipay.config.AlipayWapConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhoujian
 * @date 2019/6/15
 */
@Slf4j
@Controller
@RequestMapping(value = "/alipay/wap")
public class AliPayWapController {
    @RequestMapping(value = "/payIndex", method = RequestMethod.GET)
    public String get(HttpServletRequest httpServletRequest, HttpServletResponse response, Model model) {
        return "alipay/wapPayIndex";
    }


    /**
     * 跳转到支付宝网关支付
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public void goAliPay(String orderId,HttpServletRequest request, HttpServletResponse response, Model model) throws AlipayApiException, IOException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayWapConfig.gatewayUrl, AlipayWapConfig.app_id, AlipayWapConfig.merchant_private_key, "json", AlipayWapConfig.charset, AlipayWapConfig.alipay_public_key, AlipayWapConfig.sign_type);

        //设置请求参数
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setReturnUrl(AlipayWapConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayWapConfig.notify_url);

        String out_trade_no = orderId;
        // 订单名称，必填
        String subject = "测试订单";
        System.out.println(subject);
        // 付款金额，必填
        String total_amount = "0.01";
        // 商品描述，可空
        String body = "测试商品";
        // 超时时间 可空
        String timeout_express = "2m";
        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";

        AlipayTradeWapPayModel alipayTradeWapPayModel = new AlipayTradeWapPayModel();

        alipayTradeWapPayModel.setOutTradeNo(out_trade_no);
        alipayTradeWapPayModel.setSubject(subject);
        alipayTradeWapPayModel.setTotalAmount(total_amount);
        alipayTradeWapPayModel.setBody(body);
        alipayTradeWapPayModel.setTimeoutExpress(timeout_express);
        alipayTradeWapPayModel.setProductCode(product_code);
        alipayRequest.setBizModel(alipayTradeWapPayModel);

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        log.info("result:" + result);
        model.addAttribute("htmlResult", result);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(result);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     *
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 支付宝同步通知页面
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     *
     * @author zhoujian
     * @date 2019-6-14
     * @version V1.0
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response,Model model) throws Exception {

        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayWapConfig.alipay_public_key, AlipayWapConfig.charset, AlipayWapConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("***************************************************************");


            model.addAttribute("out_trade_no", out_trade_no);
            model.addAttribute("trade_no", trade_no);
            model.addAttribute("total_amount", total_amount);
            model.addAttribute("productName", "测试产品");

        }else {
            log.info("支付, 验签失败...");
        }

        return "/alipay/alipaySuccess";
    }

}
