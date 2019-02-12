package com.kenhome.jaspersoft.service.impl;

import com.kenhome.jaspersoft.service.FileService;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 11:36 2019/1/2
 * @Modified By:
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void createEodFile() {

        File file = new File("D:\\demo.eod");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        } else if (file.exists()) {
            file.delete();
        }

        try {
            String charset = "Big5";
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, charset);
            StringBuffer sb = new StringBuffer();
            sb.append("風蕭蕭兮易水寒");
            sb.append("\r\n");
            sb.append("壯士一去兮不復還");
            String text = sb.toString();
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void readEodFile() {

        File file = new File("D:\\demo.eod");
        try {
            String charset = "Big5";
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(
                    inputStream, charset);
            StringBuffer buffer = new StringBuffer();
            char[] buf = new char[64];
            int count = 0;
            while ((count = reader.read(buf)) != -1) {
                buffer.append(buf, 0, count);
            }
            String s = buffer.toString();
            String[] split = s.split("\r\n");
            for(String line :split){
                System.out.println("行:"+line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
