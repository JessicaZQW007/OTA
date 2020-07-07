package com.yhxc.Netty.Contorller;

import com.yhxc.Netty.Util.Transcoding;
import com.yhxc.Netty.nettyServer.NettyChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.yhxc.Netty.Util.FileUtil.scanFile;

/**
 * @Author: 张权威
 * @Date: 2020/4/14 10:52
 */
@RequestMapping("Air")
@Controller
@Api(value = "共享空调")
public class sendAirController {
    static int count = 0;
    public void count(){
        File file = new File("c:/OTA");
        List<String> list = new ArrayList<String>();
        String fileName = "张权威";
        scanFile(file, fileName, list);
         count = list.size();
        System.out.println("查找文件数量");
    }
    @ResponseBody
    @PostMapping(value = "/ota")
    public String OTA(String deviceID, int num) throws Exception {
        String source = "C:/OTA/张权威" + num + ".bin";
        File sourceFile = new File(source);
        InputStream fis = new FileInputStream(sourceFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer2 = null;
        int len = 0;
        byte[] buf = new byte[2048];
        while ((len = bufferedInputStream.read(buf)) != -1) {
            byteArrayOutputStream.write(buf, 0, len);
        }
        byteArrayOutputStream.flush();
        buffer2 = byteArrayOutputStream.toByteArray();

        if (num == 0) {
            byte[] a = new byte[4];
            a[0] = (byte) 0xAA;
            a[1] = 0x01;
            a[2] = 0x00;
            a[3] = 0x00;
            byte[] b = new byte[1];
            b[0] = Transcoding.crc(a);
            byte[] commandTitel = new byte[a.length + b.length];
            System.arraycopy(a, 0, commandTitel, 0, a.length);
            System.arraycopy(b, 0, commandTitel, a.length, b.length);
            SocketChannel schannel = (SocketChannel) NettyChannelMap.get(deviceID);
            ByteBuf sb = Unpooled.copiedBuffer(commandTitel);
            schannel.writeAndFlush(sb);

        } else {
            byte[] c = new byte[4];
            c[0] = (byte) 0xAA;
            c[1] = (byte) Integer.parseInt(num + "");
            c[2] = (byte) ((buffer2.length >> 8) & 0xff);
            c[3] = (byte) (buffer2.length & 0xff);
            System.out.println(buffer2.length + "张权威");
            byte[] commandTitel = new byte[buffer2.length + c.length + 1];
            System.arraycopy(c, 0, commandTitel, 0, c.length);
            System.arraycopy(buffer2, 0, commandTitel, c.length, buffer2.length);
           if(count==0){
               count();
           }
            if (num == count) {
                commandTitel[buffer2.length + c.length] = 0x00;
            } else {
                commandTitel[buffer2.length + c.length] = 0x01;
            }
            byte[] b = new byte[1];
            b[0] = Transcoding.crc(commandTitel);
            byte[] command = new byte[commandTitel.length + b.length];
            System.arraycopy(commandTitel, 0, command, 0, commandTitel.length);
            System.arraycopy(b, 0, command, commandTitel.length, b.length);
            SocketChannel schannel = (SocketChannel) NettyChannelMap.get(deviceID);
            ByteBuf sb = Unpooled.copiedBuffer(command);
            schannel.writeAndFlush(sb);
            System.out.println("已成功发送第" + num + "包");
        }
        return "升级中";
    }
}
