package com.yhxc.Netty;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.yhxc.Netty.Util.FileUtil.scanFile;

/**
 * @Author: 张权威
 * @Date: 2020/6/30 10:28
 */

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class file {
    public static int sechFile() {
        String filepath = "C:/OTA";//D盘下的file文件夹的目录
        File file = new File(filepath);//File类型可以是文件也可以是文件夹
        File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
        List<File> wjList = new ArrayList<File>();//新建一个文件集合
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {//判断是否为文件
                wjList.add(fileList[i]);
            }
        }
        return wjList.size();
    }
    public static void main(String[] args) {
     String  sss="1.3";
        System.out.println(sss.getBytes());
    }


}
