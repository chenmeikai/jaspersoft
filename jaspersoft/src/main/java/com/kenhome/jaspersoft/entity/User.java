package com.kenhome.jaspersoft.entity;

import java.io.Serializable;

/**
 * @Author: cmk
 * @Description:
 * @Date: Created in 14:33 2018/12/11
 * @Modified By:
 */
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private String sex;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
