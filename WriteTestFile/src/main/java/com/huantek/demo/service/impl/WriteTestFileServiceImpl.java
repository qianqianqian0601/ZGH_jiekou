package com.huantek.demo.service.impl;

import com.huantek.demo.service.WriteTestFileService;
import org.springframework.stereotype.Service;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@Service
public class WriteTestFileServiceImpl implements WriteTestFileService {

    private static boolean flag = true;

    @Override
    public String writeFile() {
        if (!flag){
            return "————该程序正在运行中————";
        }
        flag = false;
        //获取数据文件的固定文件夹路径
        String path = "/opt/dataFileWorks/deviceDataFile";
        //String path = "C:\\Users\\HuanVR\\Desktop\\中广核";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当天日期
        String time = format.format(new Date());
        //获取当天文件夹的路径
        String filePath = path + time;

        File dirs = new File(filePath);
        if (!dirs.exists()) {
            dirs.mkdirs();//文件夹不存在就创建
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();//单线线程池单独运行创建测试文件
        executorService.execute(() -> {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
            for (int i = 0; i < 100 ; i++) {
                String dirsPath = dirs.getAbsolutePath();
                String createTime = sf.format(Calendar.getInstance().getTime());
                //String testFilePath = dirsPath + "\\" +"testFile"+createTime+".txt";//win下
                String testFilePath = dirsPath + "/" +"testFile"+createTime+".txt";//linux下
                File testFile = new File(testFilePath);
                FileWriter writer = null;
                try {
                    writer = new FileWriter(testFile , true);
                }catch (Exception e){
                    e.printStackTrace();
                }
                PrintWriter printWriter = new PrintWriter(writer);
                String testData = createTestData();
                printWriter.write(testData);
                try {
                    writer.flush();
                    writer.close();
                    printWriter.close();
                    sleep(3000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flag = true;
        });

        return "————程序已启动————";
    }


    public static String createTestData() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] deviceName = {"循环水冷却塔" , "净化试验段" , "测试名称A"  , "测试名称B" , "测试名称C"};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < Math.random()*30+10; i++) {
            sb.append("设备编码=ID");
            int number = (int) (Math.random() * 50);
            sb.append(number);
            sb.append(";");
            sb.append("设备名称=");
            sb.append(deviceName[(int) (Math.random()*(deviceName.length-1))]);//随机获取数组中的设备名称
            sb.append(";");
            sb.append("时间=");
            sb.append(sf.format(Calendar.getInstance().getTime()));//获取时间
            sb.append(";");
            sb.append("T=");
            int T = (int) (Math.random() * 100);
            sb.append(T).append("°").append(";");
            int P = (int) (Math.random() * 200);
            sb.append("P=").append(P).append("N").append(";").append("\r\n");
        }
        String data = sb.toString();
        return data;
    }



}
