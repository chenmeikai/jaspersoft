package com.kenhome.jaspersoft;

import com.kenhome.jaspersoft.service.FtpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 9:16 2019/1/7
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FtpTest {

    @Resource
    private FtpService ftpService;


    @Test
    public void downTest(){

         ftpService.exportUser("1");

    }


}
