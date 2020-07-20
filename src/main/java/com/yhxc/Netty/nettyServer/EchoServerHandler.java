package com.yhxc.Netty.nettyServer;

import com.yhxc.Netty.Util.resolveMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class EchoServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 功能描述 所有的活动用户
     *
     * @author 张权威
     * @date 2018/10/22
     * @param
     * @return
     */
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 功能描述 处理退出的消息通道
     *
     * @return
     * @author 张权威
     * @date 2018/10/22
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            if (ch == channel) {
                ch.writeAndFlush("[" + channel.remoteAddress() + "] leaving");
            }
        }
        group.remove(channel);
    }

    /**
     * 功能描述 读取客户端的数据
     *
     * @return
     * @author 张权威
     * @date 2018/10/22
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        resolveMessage SS = new resolveMessage();
        SS.resolveMessage(ctx, msg);
        ctx.flush();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        resolveMessage SS = new resolveMessage();
        SS.resolveMessage(ctx, msg);

    }


    /**
     * 功能描述 在建立连接是发送消息
     *
     * @return
     * @author 张权威
     * @date 2018/10/22
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean active = channel.isActive();
        if (active) {
            System.out.println("[" + channel.remoteAddress() + "] 上线");

        } else {
            NettyChannelMap.getDecveId((SocketChannel)channel.remoteAddress());
            NettyChannelMap.remove((SocketChannel) ctx.channel());
            System.out.println("[" + channel.remoteAddress() + "] 离线2");
            ctx.close();
        }
    }

    /**
     * 功能描述 退出时发送消息
     *
     * @return
     *
     * @author 张权威
     * @date 2018/10/22
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (!channel.isActive()) {
            NettyChannelMap.remove((SocketChannel) ctx.channel());
            ctx.close().sync();
        } else {
            System.out.println("[" + channel.remoteAddress() + "] is oline");

        }
    }

    /**
     * 功能描述 异常捕获
     *
     * @param [ctx, cause]
     * @return
     * @author 张权威
     * @date 2018/10/22
     */
    /*@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] 离线");
        if (channel.isActive()) ctx.close().sync();
        NettyChannelMap.remove((SocketChannel) ctx.channel());
      NettyChannelMap.getDecveId((SocketChannel)channel.remoteAddress()));
        System.out.println("连接已断开");
    }*/
}
