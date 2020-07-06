package com.yhxc.Netty.Util;

/**
 * @Author: 张权威
 * @Date: 2020/3/2 10:40
 */
public class Transcoding {
    /**
     * 功能描述 解析Ascii码类型的数据
     *
     * @return
     * @author 张权威
     * @date 2020/2/13
     */
    public static String AsciiDecode(String datastr) {
        int blen = datastr.length() / 2;
        byte[] bdata = new byte[blen];
        for (int i = 0; i < blen; i++) {
            bdata[i] = (byte) Integer.parseInt(datastr.substring(2 * i, 2 * i + 2), 16);
        }
        String value = new String(bdata);
        return value;
    }

    /**
     * 功能描述 16进制转10进制
     *
     * @return
     * @author 张权威
     * @date 2020/2/13
     */
    public static String SixteenDecode(String datastr) {
        int sum = 0;
        for (int i = 0; i < datastr.length(); i++) {
            int m = datastr.charAt(i);//将输入的十六进制字符串转化为单个字符
            int num = m >= 'A' ? m - 'A' + 10 : m - '0';//将字符对应的ASCII值转为数值
            sum += Math.pow(16, datastr.length() - 1 - i) * num;
        }
        return sum+"";
    }
    /**
     * 功能描述 16进制转10进制
     *
     * @return
     * @author 张权威
     * @date 2020/2/13
     */
    public static int SixteenDecodeInt(String datastr) {
        int sum = 0;
        for (int i = 0; i < datastr.length(); i++) {
            int m = datastr.charAt(i);//将输入的十六进制字符串转化为单个字符
            int num = m >= 'A' ? m - 'A' + 10 : m - '0';//将字符对应的ASCII值转为数值
            sum += Math.pow(16, datastr.length() - 1 - i) * num;
        }
        return sum;
    }
    /**
    * 功能描述 10进制转16进制
    * @author 张权威
    * @date 2020/5/21
    */
    public static String intToHex(int n) {
            //StringBuffer s = new StringBuffer();
            StringBuilder sb = new StringBuilder(8);
            String a;
            char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
            while(n != 0){
                sb = sb.append(b[n%16]);
                n = n/16;
            }
            a = sb.reverse().toString();
            return a;
        }

    /**
     *功能描述 byte按位解析
            * @author 张权威
            * @date 2020/3/2
            * @return
            */
    public static String ByteDecode(String datastr) {
        int ststus = Integer.parseInt(datastr,16);
        StringBuffer permission = new StringBuffer();
        String result = "";
        for (int i = 7; i >= 0; i--) {
            result = "" + (ststus >> i & 1);
            if (i == 7) {
                permission.append(result);
            } else {
                permission.append(result);
            }

        }

        return permission.toString();
    }
    /**
     *功能描述  bit转byte
            * @author 张权威
            * @date 2020/3/2
            * @return
            */
    public static byte bitToByte(String bit) {
        int re, len;
        if (null == bit) {
            return 0;
        }
        len = bit.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理  
            if (bit.charAt(0) == '0') {// 正数  
                re = Integer.parseInt(bit, 2);
            } else {// 负数  
                re = Integer.parseInt(bit, 2) - 256;
            }
        } else {//4 bit处理  
            re = Integer.parseInt(bit, 2);
        }
        return (byte) re;
    }
    /**
     *功能描述 CRC校验
            * @author 张权威
            * @date 2020/3/2
            * @return
            */
    public static byte crc(byte[] buffer){
        int[] CHECKSUM_TABLE = {
                0x00, 0x07, 0x0e, 0x09, 0x1c, 0x1b,
                0x12, 0x15, 0x38, 0x3f, 0x36, 0x31, 0x24, 0x23, 0x2a,
                0x2d, 0x70, 0x77, 0x7e, 0x79, 0x6c, 0x6b, 0x62, 0x65,
                0x48, 0x4f, 0x46, 0x41, 0x54, 0x53, 0x5a, 0x5d, 0xe0,
                0xe7, 0xee, 0xe9, 0xfc, 0xfb, 0xf2, 0xf5, 0xd8, 0xdf,
                0xd6, 0xd1, 0xc4, 0xc3, 0xca, 0xcd, 0x90, 0x97, 0x9e,
                0x99, 0x8c, 0x8b, 0x82, 0x85, 0xa8, 0xaf, 0xa6, 0xa1,
                0xb4, 0xb3, 0xba, 0xbd, 0xc7, 0xc0, 0xc9, 0xce, 0xdb,
                0xdc, 0xd5, 0xd2, 0xff, 0xf8, 0xf1, 0xf6, 0xe3, 0xe4,
                0xed, 0xea, 0xb7, 0xb0, 0xb9, 0xbe, 0xab, 0xac, 0xa5,
                0xa2, 0x8f, 0x88, 0x81, 0x86, 0x93, 0x94, 0x9d, 0x9a,
                0x27, 0x20, 0x29, 0x2e, 0x3b, 0x3c, 0x35, 0x32, 0x1f,
                0x18, 0x11, 0x16, 0x03, 0x04, 0x0d, 0x0a, 0x57, 0x50,
                0x59, 0x5e, 0x4b, 0x4c, 0x45, 0x42, 0x6f, 0x68, 0x61,
                0x66, 0x73, 0x74, 0x7d, 0x7a, 0x89, 0x8e, 0x87, 0x80,
                0x95, 0x92, 0x9b, 0x9c, 0xb1, 0xb6, 0xbf, 0xb8, 0xad,
                0xaa, 0xa3, 0xa4, 0xf9, 0xfe, 0xf7, 0xf0, 0xe5, 0xe2,
                0xeb, 0xec, 0xc1, 0xc6, 0xcf, 0xc8, 0xdd, 0xda, 0xd3,
                0xd4, 0x69, 0x6e, 0x67, 0x60, 0x75, 0x72, 0x7b, 0x7c,
                0x51, 0x56, 0x5f, 0x58, 0x4d, 0x4a, 0x43, 0x44, 0x19,
                0x1e, 0x17, 0x10, 0x05, 0x02, 0x0b, 0x0c, 0x21, 0x26,
                0x2f, 0x28, 0x3d, 0x3a, 0x33, 0x34, 0x4e, 0x49, 0x40,
                0x47, 0x52, 0x55, 0x5c, 0x5b, 0x76, 0x71, 0x78, 0x7f,
                0x6a, 0x6d, 0x64, 0x63, 0x3e, 0x39, 0x30, 0x37, 0x22,
                0x25, 0x2c, 0x2b, 0x06, 0x01, 0x08, 0x0f, 0x1a, 0x1d,
                0x14, 0x13, 0xae, 0xa9, 0xa0, 0xa7, 0xb2, 0xb5, 0xbc,
                0xbb, 0x96, 0x91, 0x98, 0x9f, 0x8a, 0x8d, 0x84, 0x83,
                0xde, 0xd9, 0xd0, 0xd7, 0xc2, 0xc5, 0xcc, 0xcb, 0xe6,
                0xe1, 0xe8, 0xef, 0xfa, 0xfd, 0xf4, 0xf3
        };
        int crc = 0;
        for (int i = 0; i < buffer.length; i++) {
            crc = CHECKSUM_TABLE[(crc ^ (buffer[i] & 0xFF)) & 0xFF];
        }
        return (byte)(crc & 0xff);
    }
    /**
     *功能描述 数组转String
            * @author 张权威
            * @date 2020/3/4
            * @return
            */
    public static String byteToString(byte[] array){
        int datalenA = array.length;
        char[] hexArray1 = "0123456789ABCDEF".toCharArray();
        char[] hexChars1 = new char[datalenA * 2];
        for (int j = 0; j < datalenA; j++) {
            int v = array[j] & 0xFF;
            hexChars1[j * 2] = hexArray1[v >> 4];
            hexChars1[j * 2 + 1] = hexArray1[v & 0x0F];
        }
        String hexstr1 = new String(hexChars1);
        return hexstr1;
    }
    public static byte[] Division(byte[] array){
        byte[] result=new byte[4];
        result[0] = array[array.length-6];
        result[1] = array[array.length-5];
        result[2] = array[array.length-4];
        result[3] = array[array.length-3];
        return result;
    }
    public static String StringToHex(String s){
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        System.out.println(str);
        return str;
    }

}
