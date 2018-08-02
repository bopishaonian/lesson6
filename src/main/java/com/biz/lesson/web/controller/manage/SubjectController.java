package com.biz.lesson.web.controller.manage;

import com.biz.lesson.business.stn.GradeService;
import com.biz.lesson.business.stn.StudentManager;
import com.biz.lesson.business.stn.SubjectService;
import com.biz.lesson.model.stn.Grade;
import com.biz.lesson.model.stn.Student;
import com.biz.lesson.model.stn.Subject;
import com.biz.lesson.util.IdWorker;
import com.biz.lesson.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@Controller
@MultipartConfig
@RequestMapping("manage/subject")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectService manager;

    @Autowired
    private StudentManager studentManager;

    @Autowired
    private GradeService gradeService;

    @RequestMapping("/list")
    public ModelAndView list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("manage/homework/showSubjectPage");
        List<Subject> subjects = manager.findAll();
        for(Subject subject:subjects){
            if(subject.getCount()==1){
                subject.setAvg(0);
                subject.setCount(0);
            }else {
                subject.setAvg(subject.getAvg()/(subject.getCount()-1));
                subject.setCount(subject.getCount()-1);
            }
        }
        modelAndView.addObject("subjects",subjects);
        return modelAndView;
    }
    @RequestMapping("/ShowAdd")
    public String ShowAdd() throws Exception {
        return "manage/homework/addSubjectPage";
    }
    @RequestMapping("/add")
    public String add(Subject sub) throws Exception {
        sub.setSubjectId(Long.valueOf(new IdWorker(1).nextId()));
        sub.setAvg(0);
        sub.setAvgNum(0);
        sub.setCount(0);
        sub.setStudentId(new Long(0));
        manager.add(sub);
        return "redirect:/manage/subject/list.do";
    }
    @RequestMapping("/showEdit")
    public ModelAndView showEditGrade(String subjectId,Subject sub) throws Exception {
        ModelAndView modelAndView = new ModelAndView("manage/homework/editSubjectPage");

        Subject subject = manager.findOne(Long.valueOf(subjectId));

        sub.setSubjectId(subject.getSubjectId());
        sub.setName(subject.getName());
        sub.setAvgNum(subject.getAvgNum());
        sub.setStudentId(subject.getStudentId());

        modelAndView.addObject("subject",sub);
        return modelAndView;
    }
    @RequestMapping("/edit")
    public String edit(String id,Subject subject) throws Exception {

        Subject subject1 = manager.findOne(Long.valueOf(id));
        subject.setSubjectId(Long.valueOf(id));
        subject.setStudentId(subject1.getStudentId());
        subject.setAvgNum(subject1.getAvgNum());

        manager.add(subject);

        return "redirect:/manage/subject/list.do";
    }
    @RequestMapping("/del")
    public String del(String name) throws Exception {
        List<Subject> subjects = manager.findByName(name);
        for(Subject subject:subjects){
            if(subject.getStudentId()!=0){
                Student student = studentManager.findOne(subject.getStudentId());
                //更新选修该学科的学生平均分，选修课数减一
                if(student.getSubjectNum()==1){
                    student.setSubjectNum(0);
                    student.setAvgNum(0);
                }else {
                    int newStudentNum =  (student.getAvgNum()*student.getSubjectNum()-subject.getAvgNum())/(student.getSubjectNum()-1);
                    student.setAvgNum(newStudentNum);
                    student.setSubjectNum(student.getSubjectNum()-1);
                }

                //更新班级平均分
                Grade grade = gradeService.findOne(student.getGradeId());
                if(grade.getNum()==1){
                    grade.setAvgNum(0);
                }else {
                    int newGradeAvgNum = (grade.getAvgNum()*grade.getNum()-student.getAvgNum())/grade.getNum();
                    grade.setAvgNum(newGradeAvgNum);
                }
                studentManager.add(student);
                gradeService.add(grade);
            }
        }
        //删除该学科
        manager.deleteAllByName(name);
        return "redirect:/manage/subject/list.do";
    }

}

