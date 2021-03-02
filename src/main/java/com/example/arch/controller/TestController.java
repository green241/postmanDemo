package com.example.arch.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
//import org.springframework.web.bind.annotation.RestController;

/**
 * 功能说明:
 * 系统版本: v1.0<br>
 * 开发人员: @author louxh28474@hundsun.com
 * 开发时间: 2021/2/26 10:01
 */

@Controller
public class TestController {

    @RequestMapping(value = "/down")
    @ResponseBody
    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String up_flag = request.getParameter("up_flag");

        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        // 设置下载文件属性(路径&&输出类型)
        String downLoadPath = "";
        if ( "1".equals(up_flag) ){
            downLoadPath = "C:\\Users\\hspcadmin\\Desktop\\temp\\0715.pdf";
            response.setContentType("application/pdf");
        } else if ( "2".equals(up_flag) ) {
            downLoadPath = "C:\\Users\\hspcadmin\\Desktop\\temp\\1.mp4";
            response.setContentType("video/mp4");
        }

        //获取文件的长度
        long fileLength = new File(downLoadPath).length();
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(fileLength));
        //获取输入流
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        //输出流
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        //关闭流
        bis.close();
        bos.close();
    }

    /**
     * 文件下载(get请求时会出现"java.io.IOException: 你的主机中的软件中止了一个已建立的连接。")
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadVedio",method = RequestMethod.POST)
    @ResponseBody
    public void showBackup(HttpServletRequest request, HttpServletResponse response) {
        String upFlag = request.getParameter("up_flag");
        String filePath = "";
        // 1-pdf 2-mp4
        if ("1".equals(upFlag)) {
            filePath = "C:\\Users\\hspcadmin\\Desktop\\temp\\0715.pdf";
            response.setHeader("Content-Type", "application/pdf");
        } else if ("2".equals(upFlag)) {
            filePath = "C:\\Users\\hspcadmin\\Desktop\\temp\\1.mp4";
            response.setHeader("Content-Type", "video/mp4");
        } else if ("3".equals(upFlag)) {
            filePath = "C:\\Users\\hspcadmin\\Desktop\\temp\\1.mp3";
            response.setHeader("Content-Type", "audio/mp3");
        }
        File file = new File(filePath);
        if(!file.exists()){
            throw new RuntimeException("文件不存在！");
        }
        byte[] fba = getFileByteArr(file);

        OutputStream sos = null;
        try {
            sos = response.getOutputStream();
            sos.write(fba);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private byte[] getFileByteArr (File file){
        byte[] buffer = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }
}
