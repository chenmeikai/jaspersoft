package com.kenhome.jaspersoft.controller;

import com.kenhome.jaspersoft.entity.User;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 14:32 2018/12/11
 * @Modified By:
 */

@Controller
@RequestMapping("demo")
public class DemoController {


    @GetMapping("user")
    public void demo(HttpServletResponse response){


        try {

            JasperCompileManager.compileReportToFile(this.getClass().getResource("/templates/demo_A4.jrxml").getPath());

            File reportFile = new File(this.getClass().getResource("/templates/demo_A4.jasper").getPath());
            if (!reportFile.exists())
                throw new JRRuntimeException("FileWebappReport.jasper "
                        + "not found. The report design must be compiledfirst.");

            String jasperPath = this.getClass().getResource("/templates/demo_A4.jasper").getPath();

            FileInputStream isRef = null;
            ServletOutputStream sosRef = null;
            try {
                isRef = new FileInputStream(reportFile);
                sosRef = response.getOutputStream();
                //组装list数据源
                List<User> list = new ArrayList<User>();
                for (int i = 1;i<=30;i++) {
                    User user = new User();
                    user.setId("10"+i);
                    user.setName("hello");
                    user.setAge(55+i);
                    user.setSex("boy");
                    list.add(user);
                }
                //javabean为数据源注入报表数据
                JasperRunManager.runReportToPdfStream(isRef, sosRef, null,new JRBeanCollectionDataSource(list));
                response.setContentType("application/pdf");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                sosRef.flush();
                sosRef.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
