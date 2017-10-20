package com.springboot.test.controller;

import com.springboot.test.model.OrderSplit;
import com.springboot.test.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/31.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<String> getFollowedAuhorArticles(HttpServletRequest httpServletRequest) {
//        String url="http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=hhad&poolcode[]=had&_=1496285997811";
//        adminService.getOdds(url);

        List<OrderSplit> list=adminService.getId();
        StringBuffer stringBuffer=new StringBuffer();
        StringBuffer logid=new StringBuffer();
        int ordercount=0;
        int logcount=0;
        for(OrderSplit orderSplit:list){
            if(isMessyCode(orderSplit.getLogisticsname())){
                stringBuffer.append(orderSplit.getOrderId()+",");
                logid.append(orderSplit.getLogisticId()+",");
                ordercount++;
                logcount++;
            }
        }

        System.out.println("----------order : "+stringBuffer.toString());
        System.out.println("----------logid : "+logid.toString());
        System.out.println("----------ordercount : "+ordercount);
        System.out.println("----------logcount : "+logcount);


        String result = "test";
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public boolean isMessyCode(String strName) {
        try {
            Pattern p = Pattern.compile("\\s*|t*|r*|n*");
            Matcher m = p.matcher(strName);
            String after = m.replaceAll("");
            String temp = after.replaceAll("\\p{P}", "");
            char[] ch = temp.trim().toCharArray();
            float chLength = ch.length;
            float count = 0;
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                if (!Character.isLetterOrDigit(c)) {
                    if (!isChinese(c)) {
                        count = count + 1;
                    }
                }
            }
            float result = count / chLength;
            if (result > 0.4) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }
}
