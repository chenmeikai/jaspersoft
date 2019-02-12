package com.kenhome.jaspersoft.controller;

import com.kenhome.jaspersoft.common.WorkbookModel;
import com.kenhome.jaspersoft.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 16:24 2019/2/11
 * @Modified By:
 */

@RestController
public class ExcelController {


    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse response, String flag){

        System.out.println("==> " + flag);
        List<String> titleList = Arrays.asList(new String[]{"表头1", "表头2", "表头3", "表头4", "表头5", "表头6", "表头7", "表头8"});
        List<CellType> cellTypeList = Stream.of(CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING,
                CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING).collect(Collectors.toList());
        List<List<String>> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add(
                    Stream.of(i + "",
                            "什么是挨踢男人？",
                            "就是充满智慧与理性，想象与智慧的男人，思想如天马行空，爱情缺乏浪漫与温馨，他们成熟，他们智慧，但他们缺少爱的勇气，表白的机会，挨踢男人是一群可靠的男人，是一群值得托付终身的男人",
                            "时间是个什么东西？",
                            "时间是让人猝不及防的东西，晴时有风阴有时雨。争不过朝夕，又念着往昔，偷走了青丝却留住一个你。",
                            "岁月是个什么玩意？",
                            "岁月是一场有去无回的旅行，好的坏的都是风景，别怪我贪心，只是不愿醒，因为你只为你愿和我一起。",
                            "明天你是否会想起，今天写下的程序？明天你是否还惦记，曾经爱编程的你；领导们都已想不起，总爱加班的你；我也是偶然看注释，才想起码农的你；你从前总是很小心，从不把离职的事提起；你也曾无意中说起，薪水实在太低；那时候屏幕总是在闪，日子总过得太慢；你总说离职遥遥无期，转眼就各奔东西；")
                            .collect(Collectors.toList()));
        }
        List<WorkbookModel> workbookList = new ArrayList<>();
        workbookList.add(new WorkbookModel("哥就像巴黎欧莱雅,你值得拥有", titleList, cellTypeList, dataList));
        workbookList.add(new WorkbookModel("美来自内心,美来自美宝莲纽约", titleList, cellTypeList, dataList));
        ExcelUtil.download(response, "下载测试", workbookList);



    }

}
