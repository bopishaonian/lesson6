package com.biz.lesson.model.stn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 学生
 */
@Entity
@Table(name = "usr_student")
public class Student {

    @Id
    @Column(length = 20,nullable = false)
    private Long studentId;

    @Column(length = 20,nullable = false)
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 2,nullable = false)
    private String sex;

    @Column(length = 20)
    private Date date;

    @Column(length = 20)
    private Long gradeId;

    @Column(length = 10)
    private int subjectNum;

    @Column(length = 20)
    private int avgNum;

    @Column(length = 100)
    private String img;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(int avgNum) {
        this.avgNum = avgNum;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public int getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(int subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
