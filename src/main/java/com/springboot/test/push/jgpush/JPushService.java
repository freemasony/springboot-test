package com.springboot.test.push.jgpush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author zhoujian
 * @date 2019/9/6
 */
@Component
public class JPushService {

    @Resource
    private JPushClient jPushClient;

    public void push() throws APIConnectionException, APIRequestException {
        PushPayload pushPayload = JPushPayloadBuilder.buildPushNotification_android_and_ios(60,"无耻冠群拖欠工资", "还我血汗钱", new HashMap<>());
        PushResult pushResult = jPushClient.sendPush(pushPayload);
        System.out.println(pushResult);
    }
}
