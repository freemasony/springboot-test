package com.springboot.test.websocket;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhoujian
 * @date 2019/6/4
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Map<String, WebSocketHandler> webSockets = Maps.newConcurrentMap();
    private static AtomicInteger atomicInteger=new AtomicInteger(0);
    private ChannelHandlerContext ctx;

    private AttributeKey<Long> attributeKey=AttributeKey.valueOf("UserId");


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.disconnect();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        super.channelRead(ctx, msg);
    }

    /**
     * msg: userId:ping 或者 userId
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到客户端数据:" + msg.text()+":"+atomicInteger.get());
        boolean flag = true;
        String[] text = msg.text().split(":");
        if (text.length == 2 && "ping".equals(text[2])) {
            flag = false;
        }

        if (webSockets.containsKey(text[1]) && flag) {
            webSockets.get(text[1]).ctx.disconnect();
        }
        webSockets.put(text[1], this);
        ctx.writeAndFlush(new TextWebSocketFrame("身份验证完成，连接成功"));
    }


    public static void send(String message){
        log.info("发送数据:"+message);
        List<WebSocketHandler> list = Lists.newCopyOnWriteArrayList();
        List<List<WebSocketHandler>> splitList=Lists.partition(list,50);
        splitList.forEach(socketList->{
            for (WebSocketHandler webSocketServer : socketList) {
                ChannelHandlerContext ctx = webSocketServer.ctx;
                ctx.writeAndFlush(new TextWebSocketFrame(message));
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
