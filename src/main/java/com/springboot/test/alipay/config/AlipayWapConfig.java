package com.springboot.test.alipay.config;

/**
 * @author zhoujian
 * @date 2019/6/15
 */
public class AlipayWapConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016093600634216";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIAADANBgkqhkiG9w0BAESFAASCBKcwggSjAgEAAoIBAQCMjOaSs3iAC4LJ+9b/Uqr89H9VzJBRY44VrE/bLUTCgjgKTE318lvfDIqv+w0U2tvRv95Q6CsyMU0Knj3ft2tlHb0sgf+QRazjX5J3QKdiCIKxYkdXTmYnad89dMcGg+/2s9SWXAwYtcxzrgEYn+Tbirc81N86tNLxQCR8Q66ITrDdYsZGMR56IN1tnRI8yMZp7m+czB2krnThayCI0+HNFv118SGQEfUYkxODyqjSqXTnC+K2tlnL4oKTkwKbBo1EiisAwVcmsKrlhQ/lWC9+H4H7AL20x9YiF/5tSdninrJsQqFEDCLzfKOQCYULCdM1FSPxW1FfbVxJbsix+h0RAgMBAAECggEAJdJ06d1p++sdEwW2YZb5dIDHE2utGnnT7nkkNDMGYeFw+zR1dk318xwI5jqE1qveo58iUFtp5GPn+4eB1wYg3GEYWzXQdB6L29QIMhcv8+2eXeW4lQH/83NGI9+c/1/Z32WdO6khiDTEBA07Nkkw6PN3Q+N0/vNSIKHGL7p26S79rm7vSEU8bm2jUUuQtU5GWxXGAfBghkj5MER39z56WOjnj8fW+HeG+qRnDtVOvg3Ra+QgdYWy51qjpeEOLTnB9Uvhbu5pQSljlzV6MD2M7H1tX7RTmcz5kjQ8F0zUXE+ZBlruchS9dORmt2ZKVbxqm5ckFaY+i2Uh5pAdKlP9AQKBgQDGnsMI00SaEQlvCJLcgKrThD3TvW5Ig417nR2tD8ad2AjgM8soeyQyFs4AMYzjo8/6Y3xplvVw949ablRWjT4UoQ4psFWIbilG1ZbEJ8MaRE56LgbfpJqm3XlVZuT52IQHTaWiZL8IvO68z3DT3bpt7VBPQXIXQEqOc6FHN4yWuQKBgQC1J4GFuE09dluhRf4Lrz3jbsZd3YguzFXCukM6hGWOZyjuvWzFHGH0NoADigjaGFAb54XFgtX67gY83dEqE1usRTiroVz/rJZrFEUjTcG3bKVILRqRKCXvlbUnyIskxQJ+rEmp2m+KXgKdIZrubuGmxn7A4E1EUSIHyVkX4lgNGQKBgGX5vUkar24qsR1A2aY8qXhPvS6pY6X3ZPzCfioMn30LHgN+lCAEmWDeNIYE6sE1qeJ0kbvktb96Wy+i1cpaWjVdO1hMIJ4bcTXdE7tCbvldeK1iVEUezgg+Tb17RrTAZYkaZKAr+akS18A5AUTF4qHt5f3LLDij2aOp2nQmIRiBAoGBAJLN3yL0hnPGlN8PD598dlT3En5C5ve109IojA7B+GL8qqk+jQT3yVHVUMyNcjr79P+IElpLewkNF7T/tV+5rFLhDdnYvLrdrFA+bwD7JH925kKleqAjvwp3cgU5eblkhm8NQ+0H/dhN8yRPdXTAGtHCjjnUDAnPDhrtSuwWG3ERAoGAY00B8ECNB41APmMkSwWF1Ko9RScne8f4KpeyZHNYbddYBsaCJZ0aCp0fKL79yv4VBsX3/XEQz0qTxzlCXMda8g4RGEvmepMXT+D4KZSviwp1gvX4VAlOsA1f/HjOGQxCWl0IycrQ+CCcP83whJiv4SrGMCAxlcZ1HHemyLOC8xA=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIGfMA0GCSqGSWb3DQEBAQUAA4GAADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1:9010/alipay/wap/alipayNotifyNotice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1:9010/alipay/wap/alipayReturnNotice";

    // 签名方式
    public static String sign_type = "RSA";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
