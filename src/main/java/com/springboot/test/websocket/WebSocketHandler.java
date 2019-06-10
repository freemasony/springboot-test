package com.springboot.test.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhoujian
 * @date 2019/6/4
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static List<WebSocketHandler> webSockets = new CopyOnWriteArrayList<WebSocketHandler>();
    private static AtomicInteger atomicInteger=new AtomicInteger(0);
    private ChannelHandlerContext ctx;



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
        webSockets.add(this);
        atomicInteger.incrementAndGet();
        log.info("与客户端建立连接，通道开启！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        webSockets.remove(this);
        atomicInteger.decrementAndGet();
        log.info("与客户端断开连接，通道关闭！");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("客户端收到服务器数据:" + msg.text()+":"+atomicInteger.get());
        Thread.sleep(1000);
        String resp= "服务器推送 (" +ctx.channel().remoteAddress() + ") ：哈哈哈" ;
        ctx.writeAndFlush(new TextWebSocketFrame(resp));
    }


    public static void send(String message){
        log.info("发送数据:");
        for(WebSocketHandler webSocketServer:webSockets){
            ChannelHandlerContext ctx= webSocketServer.ctx;
            ctx.writeAndFlush(new TextWebSocketFrame(message));
        }
    }
}
