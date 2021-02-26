package com.example.arch.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
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

    @RequestMapping(value = "/downloadVedio",method = RequestMethod.POST)
    @ResponseBody
    public void show(HttpServletRequest request, HttpServletResponse response) {
        File file = new File("C:\\Users\\hspcadmin\\Desktop\\temp\\ff.mp4");
        if(!file.exists()){
            throw new RuntimeException("视频文件不存在！");
        }
        byte[] fba = getFileByteArr(file);

        OutputStream sos = null;
        try {
            response.setHeader("Content-Type", "video/mp4");
            sos = response.getOutputStream();
            sos.write(fba);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
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
