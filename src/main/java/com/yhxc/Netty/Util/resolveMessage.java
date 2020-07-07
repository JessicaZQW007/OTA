package com.yhxc.Netty.Util;

import com.yhxc.Netty.Decode.decodeAir;
import com.yhxc.Netty.nettyServer.NettyChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;


public class resolveMessage {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void resolveMessage(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        int datalen = bytes.length;
        int[] intarray = new int[datalen];
        for (int i = 0; i < datalen; i++) {
            // byte转无符号int
            intarray[i] = bytes[i] & 0xff;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[datalen * 2];
        for (int j = 0; j < datalen; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        String hexstr = new String(hexChars);       //////
        System.out.println(hexstr);
        String type2 = hexstr.substring(0, 2);
        String decveId = "";
        if("AA".equals(type2)){
            decveId = Transcoding.AsciiDecode(hexstr.substring(8, 38));
            NettyChannelMap.add(decveId, (SocketChannel) ctx.channel());

        }else {
             decveId = Transcoding.AsciiDecode(hexstr.substring(6, 36));
            NettyChannelMap.add(decveId, (SocketChannel) ctx.channel());
        }

        decodeAir air = new decodeAir();
        if("AA".equals(type2)) {
            air.ota(hexstr, ctx);
        }
    }
}