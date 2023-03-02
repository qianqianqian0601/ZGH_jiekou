package com.huantek.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.huantek.demo.model.Result;
import com.huantek.demo.service.ReadDataService;
import com.huantek.demo.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class ReadDataServerImpl implements ReadDataService {

    @Override
    public Result getLatestData() throws IOException {
        //获取数据文件的固定文件夹路径
        //String path = "/opt/dataFileWorks/deviceDataFile";
        String path = "C:\\Users\\HuanVR\\Desktop\\中广核";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当天日期
        String time = format.format(new Date());
        //获取当天文件夹的路径
        String filePath = path + time;
        String newFilePath = getNewFile(filePath);

        File file = new File(newFilePath);
        if (!file.exists()) {
            return ResultUtil.error("暂时没有最新数据或者服务器中文件路径存在错误！");
        }
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String info = null;
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        while ((info = reader.readLine())!=null && !info.equals("")){
            System.out.println(info);
            String[] split = info.split(";");
            JSONObject jsonObject = new JSONObject();
            for (String keyAndVal : split) {
                try {
                    String[] val = keyAndVal.split("=");
                    jsonObject.put(val[0],val[1]);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            jsonObjects.add(jsonObject);
        }
        inputStream.close();
        reader.close();
        new Thread(()->{
            try {
                readAndWriteFiles(filePath,newFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        return ResultUtil.success(jsonObjects);
    }

    /**
     * 循环读取文件并写历史数据
     * @param path
     */
    public static void readAndWriteFiles(String path , String newFilePath) throws IOException {
        long newFiletime = getFileCreateTime(newFilePath);//获取最新文件的修改时间
        File files = new File(path);
        File[] listFiles= files.listFiles();
        StringBuffer sb = new StringBuffer();
        for (File listFile : listFiles) {
            String absolutePath = listFile.getAbsolutePath();
            long time = getFileCreateTime(absolutePath);
            if (time <= newFiletime){
                String fileName = listFile.getName();
                sb.append(fileName).append("\r\n");
                FileInputStream inputStream = new FileInputStream(listFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String info;
                while ((info = reader.readLine())!=null && !info.equals("")){
                    System.out.println(info);
                    sb.append(info).append("\r\n");
                }
                //关闭文件IO流
                inputStream.close();
                reader.close();
                //删除读取过的文件
                listFile.delete();
            }
        }
        //获取上级目录
        String parent = files.getParent();
        //String historyFileWork = parent + "/" + "historicalData";
        String historyFileWork = "C:\\Users\\HuanVR\\Desktop\\historicalData";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(new Date());
        //String historyFile = historyFileWork + "/" + "historicalData" + time + ".txt";
        String historyFile = historyFileWork + "\\" + "historicalData" + time + ".txt";
        //没有文件创建文件，有文件进行内容追加
        File file = new File(historyFile);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
        }catch (Exception e){
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(writer);
        String fileInfo = sb.toString();
        pw.write(fileInfo);

        //文件写入刷新
        writer.flush();
        //关闭文件读写流
        pw.close();
        writer.close();
    }

    /**
     * 读取文件修改时间
     * @param filePath
     * @return
     */
    public static long getFileCreateTime(String filePath) {
        BasicFileAttributes bAttributes = null;
        try {
            bAttributes = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileTime fileTime = bAttributes.lastModifiedTime();
        long createTime = fileTime.toMillis();
        return createTime;
    }

    /**
     * 获取最新文件
     * @param path
     * @return
     */
    public static String getNewFile(String path) {
        long newFileTime = 0;
        String newFilePath = "";
        //获取文件夹下所有文件
        File files = new File(path);
        File[] fileList = files.listFiles();
        for (File file : fileList) {
            String absolutePath = file.getAbsolutePath();
            //获取文件创建时间
            long fileCreateTime = getFileCreateTime(absolutePath);
            //创建时间最大的为最新数据文件
            if (fileCreateTime > newFileTime){
                newFileTime = fileCreateTime;
                newFilePath = absolutePath;
            }
        }
        return newFilePath;
    }

}
