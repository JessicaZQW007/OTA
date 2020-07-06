import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/3/20 9:10
 */
public class test {
    public static final String SUFFIX = ".bin"; // 分割后的文件名后缀

    public static String[] aaa(String name, long size) throws Exception {
        File file = new File(name);
        if (!file.exists() || (!file.isFile())) {
            throw new Exception("指定文件不存在！");
        }
        // 获得被分割文件父文件，将来被分割成的小文件便存在这个目录下
        File parentFile = file.getParentFile();
        // 取得文件的大小
        long fileLength = file.length();
        System.out.println("文件大小：" + fileLength + " 字节");
        if (size <= 0) {
            size = fileLength / 2;
        }
        // 取得被分割后的小文件的数目
        int num = (fileLength % size != 0) ? (int) (fileLength / size + 1)
                : (int) (fileLength / size);
        // 存放被分割后的小文件名
        String[] fileNames = new String[num];
        // 输入文件流，即被分割的文件
        FileInputStream in = new FileInputStream(file);
        // 读输入文件流的开始和结束下标
        long end = 0;
        int begin = 0;
        // 根据要分割的数目输出文件
        for (int i = 0; i < num; i++) {
            // 对于前num – 1个小文件，大小都为指定的size
            File outFile = new File(parentFile, "张权威" + (i + 1) + SUFFIX);
            // 构建小文件的输出流
            FileOutputStream out = new FileOutputStream(outFile);
            // 将结束下标后移size
            end += size;
            end = (end > fileLength) ? fileLength : end;
            // 从输入流中读取字节存储到输出流中
            for (; begin < end; begin++) {
                out.write(in.read());
            }
            out.close();
            fileNames[i] = outFile.getAbsolutePath();
        }
        in.close();
        return fileNames;
    }

    public static void readFileMessage(String fileName, int ii) {// 将分割成的小文件中的内容读出
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String string = null;
            // 按行读取内容，直到读入null则表示读取文件结束
            while ((string = reader.readLine()) != null) {
                System.out.println(string);

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] readFromByteFile(String pathname) throws IOException {
        File filename = new File(pathname);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = in.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        in.close();
        byte[] content = out.toByteArray();
        return content;
    }

    public static void main(String[] args) throws Exception {
        byte[] temp = new byte[1024];
        String name = "D:/XCKT1_6_OTA.bin";
        long size = 256;//1K=1024b(字节)
        temp = test.readFromByteFile(name);
        String aaa = test.parseByte2HexStr(temp);
        System.out.println(aaa);
        String[] fileNames = test.aaa(name, size);
        System.out.println("文件" + name + "分割的结果如下：" + size);
        for (int i = 0; i < fileNames.length; i++) {
            System.out.println(fileNames[0] + "的内容如下：");
            test.readFileMessage(fileNames[0], 0);
        }

        System.out.println();
       /* String s="8088";
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        System.out.println(str);*/
     /*   File file = new File("d:/固件文件");
        List<String> list = new ArrayList<String>();
        String fileName = "张权威";
        scanFile(file, fileName, list);
        if (list.size() == 0)
        {
            System.out.println("没有找到文件。");
        } else
        {
            for(String s : list)
            {
                System.out.println("找到文件" + s);
            }
            System.out.println("共有文件" + list.size() + "个");
        }

    }*/
   /* public static void scanFile(File thefile, String fileName, List<String> list)
    {
        //这一句也是调试用的，可以直接将thefile传入下面的语句。
        File file = thefile;
        File[] files = file.listFiles();
        //正常情况下如果文件夹为空，file.listFiles();得到的值应该是files = [];这样的话程序是无误的，
        //但是有时候会出现返回为Null的情况。暂时不知道是什么原因，但是我们依然可以把它当作[]来处理进行跳过。
        //所以需要加这样一句话。将null变成File[0];
//		if(files == null)
//		{
//			files = new File[0];
//		}
        //上面是其中一种思路，其实直接采用下面的思路即可。
        //之前没用采用下面这种思路，是担心遇到file == null的时候递归会中断。
        //看来还是对递归的了解不够深入。
        if(files != null)
        {
            for(File f : files)
            {
                if(f.isFile())
                {
                    //这一段是测试在console里面打印输出所有扫描到的文件，仅作调试使用。
                    try
                    {
                        System.out.println("查找中:" + f.getCanonicalPath());
                    } catch (IOException e1)
                    {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    //若有需要，可以同时传入参数，限制文件的名字。或者用FileFilter也行。
//				if(f.getName().substring(f.getName().length()-3).equals("xml"))

                    //使用str1.indexOf(str2),只要文件名中包含str2就可以了。
                    //用于模糊查找。
                    if(f.getName().indexOf(fileName) >= 0)
                    {
                        try
                        {
                            list.add(f.getCanonicalPath());
                        } catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else if(f.isDirectory())
                {
                    scanFile(f, fileName, list);
                }
            }
        }
    }*/
   /* public static void main(String[] args) throws Exception
    {
        String source = "D:/张权威1.bin";
        String destination = "D://NATLOG_280000069469_20160919211200_0000.DAT.txt";

        int bufferSize = 4096; // 设置缓冲区大小
        byte buffer[] = new byte[bufferSize]; // 缓冲区字节数组

        File sourceFile = new File(source);

        InputStream fis = new FileInputStream(sourceFile);
        BufferedInputStream bis = new BufferedInputStream(fis, bufferSize);

        OutputStream fos = new FileOutputStream(destination);
        BufferedOutputStream bos = new BufferedOutputStream(fos, bufferSize);
       *//* SocketChannel schannel = (SocketChannel) NettyChannelMap.get("865933037357893");
       *//**//* ByteBuf sb = Unpooled.copiedBuffer(fos);*//**//*
        schannel.writeAndFlush(fos);
*//*
        long fileSize = sourceFile.length(); // 文件总字节数
        int haveRead = 0; // 已读取字节数
        int readSize = -1; // 记录每次实际读取字节数
        while (null != bis && (readSize = bis.read(buffer)) != -1)
        {
            haveRead += readSize;
            bos.write(buffer, 0, readSize);
            System.out.println("已经复制： " + haveRead + " Byte 完成" + haveRead * 100 / fileSize + "% 单次读取：" + readSize + " Byte");
        }
        BufferedReader reader =  new BufferedReader(new FileReader("D:/张权威1.bin"));
        String[] temp = reader.readLine().split(",");
        byte[] b = new byte[temp.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp[i], 2).byteValue();
        }
        bos.close();
        bis.close();
        System.out.println("复制完成： " + haveRead);
    }*/

    }
}

