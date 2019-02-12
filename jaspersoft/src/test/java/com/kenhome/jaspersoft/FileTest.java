package com.kenhome.jaspersoft;

import com.kenhome.jaspersoft.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 12:03 2019/1/2
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileTest {

    @Resource
    private FileService fileService;


    @Test
    public void createFile(){
        fileService.createEodFile();
    }

    @Test
    public void readFile(){
        fileService.readEodFile();
    }

}
