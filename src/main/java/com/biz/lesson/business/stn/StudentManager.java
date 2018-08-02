package com.biz.lesson.business.stn;

import com.biz.lesson.dao.stn.GradeRepository;
import com.biz.lesson.dao.stn.StudentRepository;
import com.biz.lesson.dao.stn.SubjectRepository;
import com.biz.lesson.model.stn.Grade;
import com.biz.lesson.model.stn.Student;
import com.biz.lesson.model.stn.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StudentManager {
//
    @Autowired
    private StudentRepository studentDao;

    @Autowired
    private SubjectRepository subjectDao;

    @Autowired
    private GradeRepository gradeDao;
    @Autowired
    private GradeService gradeService;

    @Autowired
    private  SubjectService subjectService;

    public List<Student> findAll () {

        List<Student> stns = (List<Student>) studentDao.findAll();
        return  stns;
    }

    //按日期查询
    public Page<Student> findByDate(int page, Student student, Date date1, Date date2) {

        Pageable pageable = new PageRequest(page,5);
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Path studentId = root.get("studentId");
                Path name = root.get("name");
                Path birthday = root.get("birthday");
                Predicate like = criteriaBuilder.like(studentId, student.getStudentId() + "%");
                Predicate like1 = criteriaBuilder.like(name, "%" + student.getName() + "%");

                Predicate predicate = criteriaBuilder.lessThan(birthday, date2);
                Predicate predicate1 = criteriaBuilder.greaterThan(birthday, date1);

                Predicate and = criteriaBuilder.and(like, like1, predicate1, predicate);

                return and;
            }
        };

        Page all = studentDao.findAll(specification, pageable);

        return all;
    }

    //添加
    public void add(Student student){
        studentDao.save(student);
//        Long gradeId = student.getGradeId();
//
//        gradeDao.updateByGradeId(gradeId);


    }

    //删除
    public void delete(Long id){

        studentDao.deleteByStudentId(id);

    }


    //已选课程
    public List<Subject> findSubject(Long id){

        List<Subject> list = subjectDao.findByStudentId(id);


        return list;
    }

    //录入
    public void updateAvg(Student student){

        Long studentId = student.getStudentId();
        int avg = student.getAvgNum();
        Long gradeId = student.getGradeId();

        studentDao.save(student);

        subjectDao.updateAvg(studentId,avg);
        gradeDao.updateAvg(gradeId);
    }


    //未选课程
    public List<Subject> findNoSubject(String id){


        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Path studentId = root.get("studentId");
                Predicate notEqual = criteriaBuilder.notEqual(studentId, id);

                Predicate and = criteriaBuilder.and(notEqual);

                return and;
            }
        };

        List<Subject> all = subjectDao.findAll(specification);

        return all;
    }

    //选课
    public  void addSubject(Subject subject){
        subjectDao.save(subject);

    }
    //查询一个学生
    public  Student findOne(Long id){
      return studentDao.findOne(id);
    }
    //通过班级查询所有学生
    public List<Student> findAllByGradeId(Long id){
       return studentDao.findAllByGradeId(id);
    }
    //联级删除学生
    public  void deledteVo(Long id){

        Student student =this.findOne(id);
        //班级人数减一，班级平均数重算
        Grade grade =  gradeService.findOne(student.getGradeId());
        int newGradeAvgNum = 0;
        if(grade.getNum()==1){
            grade.setAvgNum(0);
            grade.setNum(0);
        }else {
            newGradeAvgNum = (grade.getAvgNum()*grade.getNum()-student.getAvgNum())/(grade.getNum()-1);
            grade.setAvgNum(newGradeAvgNum);
            grade.setNum(grade.getNum()-1);
        }
        gradeService.add(grade);
        //学科表
        subjectService.delByStudentId(id);
        //删除该学生
        this.delete(id);
    }
}
