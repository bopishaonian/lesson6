package com.biz.lesson.model.stn;

/**
 * @program: lesson6
 * @description:
 * @author: Mr.Bo
 * @create: 2018-08-01 13:34
 **/
public class StudentVo {
    private Student student;
    private String name;

    public Student getStudent(){
        return student;
    }
    public void setStudent(Student student){
        this.student = student;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
