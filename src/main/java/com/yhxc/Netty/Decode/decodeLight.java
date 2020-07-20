package com.yhxc.Netty.Decode;

import com.yhxc.Netty.Contorller.sendAirController;
import com.yhxc.Netty.Contorller.sendLightController;
import com.yhxc.Netty.Util.Transcoding;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 张权威
 * @Date: 2020/7/20 14:29
 */
public class decodeLight {
    sendLightController send = new sendLightController();

    public void ota(String hexstr, ChannelHandlerContext ctx) {
        Map<String, String> result = new HashMap<>();
        result.put("decveId", Transcoding.AsciiDecode(hexstr.substring(8, 38)));
        result.put("num", Transcoding.SixteenDecode(hexstr.substring(2, 4)));
        result.put("result", hexstr.substring(38, 40));
        if (hexstr.substring(38, 40).equals("01")) {
            try {
                send.OTA(Transcoding.AsciiDecode(hexstr.substring(8, 38)), Integer.parseInt(Transcoding.SixteenDecode(hexstr.substring(2, 4))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
