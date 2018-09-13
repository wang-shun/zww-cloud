package com.stylefeng.guns.Test;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws Exception {
        Class<?> clazz = Student.class;
        Field[] fields = clazz.getFields();
        for (Field field:fields) {
            System.out.println(field);
            System.out.println("访问修饰符："+ Modifier.toString(field.getModifiers()));
            System.out.println("字段类型："+ field.getType());
            System.out.println("字段名称："+ field.getName());
        }

        fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            System.out.println(field);
            System.out.println("访问修饰符："+ Modifier.toString(field.getModifiers()));
            System.out.println("字段类型："+ field.getType());
            System.out.println("字段名称："+ field.getName());
        }
        Field field = clazz.getDeclaredField("stuGender");
        Object instance = clazz.newInstance();
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
        Method readMethod = pd.getReadMethod();
        Method writeMethod = pd.getWriteMethod();
        writeMethod.invoke(instance,"bruce");
        Object invoke = readMethod.invoke(instance);
        System.out.println(invoke);

    }
}


class Student{
    private Integer stuId;
    private String stuName;
    public Integer stuAge;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }
}


