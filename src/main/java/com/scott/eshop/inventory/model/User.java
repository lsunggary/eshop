package com.scott.eshop.inventory.model;

/**
 * @ClassName 测试用户 Model类
 * @Description
 * @Author 47980
 * @Date 2020/4/28 20:39
 * @Version V_1.0
 **/
public class User {

    /**
     * 测试用户姓名
     */
    private String name;

    /**
     * 测试用户年龄
     */
    private Integer age;

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
}
