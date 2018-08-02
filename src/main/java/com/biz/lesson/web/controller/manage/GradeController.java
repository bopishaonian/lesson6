package com.biz.lesson.web.controller.manage;

import com.biz.lesson.business.stn.GradeService;
import com.biz.lesson.business.stn.StudentManager;
import com.biz.lesson.model.stn.Grade;
import com.biz.lesson.model.stn.Student;
import com.biz.lesson.util.IdWorker;
import com.biz.lesson.util.NotNull;
import com.biz.lesson.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@Controller
@MultipartConfig
@RequestMapping("manage/grade")
public class GradeController extends BaseController {

    @Autowired
    private GradeService manager;

    @Autowired
    private StudentManager studentManager;

    @RequestMapping("/list")
    public ModelAndView list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("manage/homework/showGradePage");
        List<Grade> grades = manager.findAll();
        modelAndView.addObject("grades",grades);
        return modelAndView;
    }
    @RequestMapping("/showAddGrade")
    public String showAddGrade() throws Exception {

        return "manage/homework/addGradePage";
    }

    @RequestMapping("/addGrade")
    public String addGrade(Grade grade) throws Exception {
        grade.setAvgNum(0);
        grade.setNum(0);
        grade.setGradeId((new IdWorker(1)).nextId());
        if(NotNull.isAllFieldNull(grade)){
            manager.add(grade);
        }
        return "redirect:/manage/grade/list.do";
    }
    @RequestMapping("/del")
    public String del(String id) throws Exception {
        //删除该班级的所有学生
        List<Student> students = studentManager.findAllByGradeId(Long.valueOf(id));
        for(Student student : students){
            studentManager.deledteVo(student.getStudentId());
        }
        //删除班级
        manager.delete(Long.valueOf(id));
        return "redirect:/manage/grade/list.do";
    }
    @RequestMapping("/showEdit")
    public ModelAndView showEditGrade(String id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("manage/homework/editGradePage");
        Grade grade  = manager.findOne(Long.valueOf(id));
        modelAndView.addObject("grade",grade);
        return modelAndView;
    }
    @RequestMapping("/edit")
    public String edit(String id,Grade grade) throws Exception {
        Grade grade1 = manager.findOne(Long.valueOf(id));
        grade.setGradeId(grade1.getGradeId());
        grade.setNum(grade1.getNum());
        grade.setAvgNum(grade1.getAvgNum());
        if(NotNull.isAllFieldNull(grade)){
            manager.add(grade);
        }

        return "redirect:/manage/grade/list.do";
    }

}

