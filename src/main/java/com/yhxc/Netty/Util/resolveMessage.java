package com.yhxc.Netty.Util;

import com.yhxc.Netty.Decode.decodeAir;
import com.yhxc.Netty.Decode.decodeLigth;
import com.yhxc.Netty.nettyServer.NettyChannelMap;
import com.yhxc.Netty.serviceTwo.ReportCycleAirService;
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
      /*  if (datalen < 30)
            return;*/
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
        String protocolMark = hexstr.substring(4, 6);
        String type = hexstr.substring(0, 4);
        String type2 = hexstr.substring(0, 2);
        String decveId = "";
        if("AA".equals(type2)){
            decveId = Transcoding.AsciiDecode(hexstr.substring(8, 38));
            NettyChannelMap.add(decveId, (SocketChannel) ctx.channel());

        }else {
             decveId = Transcoding.AsciiDecode(hexstr.substring(6, 36));
            NettyChannelMap.add(decveId, (SocketChannel) ctx.channel());
        }


        decodeLigth Decode = new decodeLigth();
        decodeAir air = new decodeAir();
        if("FFAA".equals(type)){
            switch (protocolMark) {
                case "01": {//1、设备请求接入服务器
                    Decode.login(hexstr, ctx);
                    break;
                }
                case "03": {//3、设备上报设备状态以及采集数据（电流，电量）
                    Decode.decodeLight(hexstr, ctx);
                    break;
                }

                case "05": {//5、设备上报报警或异常开关灯
                    Decode.erro(hexstr, ctx);
                    break;
                }
                case "08": {//8、设备响应七日计划配置
                    Decode.Timing(hexstr, ctx);
                    break;
                }
                case "0A": {//10、设备响应操作继电器
                    Decode.Switch(hexstr, ctx);
                    break;
                }
                case "0C": {//12、设备响应查询继电器状态
                    Decode.Query(hexstr, ctx);
                    break;
                }
                case "0E": {//14、设备响应查询继电器状态
                    Decode.Relieve(hexstr, ctx);
                    break;
                }
                case "10": {//16、上报周期配置回复
                    Decode.Revolution(hexstr, ctx);
                    break;
                }
                case "FF": {//18、设备收到复位回复
                    Decode.Reset(hexstr, ctx);
                    break;
                }
                case "12": {//18、设备收到复位回复
                    Decode.Cler(hexstr, ctx);
                    break;
                }
                case "14": {//18、设备收到复位回复
                    Decode.Setdate(hexstr, ctx);
                    break;
                }
            }
        }else if("FFDD".equals(type)){
            ReportCycleAirService reportcycleairservice = SpringContextHolder.getBean(ReportCycleAirService.class);
            reportcycleairservice.deleteReportCycle(decveId);
            switch(protocolMark){
                case "01": {//1、设备请求接入服务器
                    air.login(hexstr, ctx);
                    break;
                }
                case "03": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.decodeAir(hexstr, ctx);
                    break;
                }
                case "06": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Switch(hexstr, ctx);
                    break;
                }
                case "08": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Query(hexstr, ctx);
                    break;
                }
                case "0A": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Revolution(hexstr, ctx);
                    break;
                }
                case "FF": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Reset(hexstr, ctx);
                    break;
                }
                case "0C": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Setdate(hexstr, ctx);
                    break;
                }
                case "10": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Cler(hexstr, ctx);
                    break;
                }
                case "0E": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.Timing(hexstr, ctx);
                    break;
                }
                case "11": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.heartbeat(hexstr, ctx);
                    break;
                }
                case "AA": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.ota(hexstr, ctx);
                    break;
                }
                case "F1": {//3、设备上报设备状态以及采集数据（电流，电量）
                    air.update(hexstr, ctx);
                    break;
                }
            }
        }else if("AA".equals(type2)) {
            air.ota(hexstr, ctx);
        }
    }
}