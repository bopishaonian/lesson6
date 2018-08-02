package com.biz.lesson.web.controller.manage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.biz.lesson.business.stn.GradeService;
import com.biz.lesson.business.stn.StudentManager;
import com.biz.lesson.business.stn.SubjectService;
import com.biz.lesson.model.stn.Grade;
import com.biz.lesson.model.stn.Student;
import com.biz.lesson.model.stn.StudentVo;
import com.biz.lesson.model.stn.Subject;
import com.biz.lesson.util.IdWorker;
import com.biz.lesson.util.NotNull;
import com.biz.lesson.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@MultipartConfig
@RequestMapping("manage/student")
public class StudentController extends BaseController {
    private String accessKeyID = "LTAIUh5BN9t52DJ9";
    private String accessKeySecret = "GaaAaHECMiXIBdxFhPt2pq3bk2y06u";
    private String bucket = "huangbo-oss";
    private String endPoint = "oss-cn-shenzhen.aliyuncs.com";

    @Autowired
    private StudentManager manager;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GradeService gradeService;

    @RequestMapping("/list")
    public ModelAndView list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("manage/homework/showStnPage");
        List<Student> stns = manager.findAll();
        List<StudentVo> stnVos = new ArrayList<StudentVo>();
        if(stns!=null){
            for (int i=0;i<stns.size();i++) {
                StudentVo stnVo = new StudentVo();
                stnVo.setStudent(stns.get(i));

                Long gradeId= stns.get(i).getGradeId();
                String name = gradeService.findOne(gradeId).getName();

                stnVo.setName(name);
                stnVos.add(i,stnVo);
            }
        }

        modelAndView.addObject("stns", stnVos);
        return modelAndView;
    }
    //跳转添加页面
    @RequestMapping("/showAddStn")
    public ModelAndView showAddStn(){
        ModelAndView modelAndView = new ModelAndView("manage/homework/addStnPage");

        List<Grade> grades= gradeService.findAll();

        modelAndView.addObject("grades",grades);
        return  modelAndView;
    }
    //添加学生

    @RequestMapping("/addStn")
    public String addStn (Student stn, String stn_birthdayStr , HttpServletRequest request, @RequestParam("filename") MultipartFile file) throws IOException {

        stn.setAvgNum(0);
        stn.setSubjectNum(0);

        //set日期
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stn_birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        stn.setDate(date);

        //上传头像文件到OSS
        OSSClient ossClient = new OSSClient(endPoint, accessKeyID, accessKeySecret);

        if (!ossClient.doesBucketExist(bucket)) {
            /*
             * Create a new OSS bucket
             */
            //System.out.println("Creating bucket " + bucketName + "\n");
            ossClient.createBucket(bucket);
            CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucket);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }

        if(!file.isEmpty()) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/images/");
            //上传文件名
            String filename = file.getOriginalFilename();

            String str =filename.substring(filename.lastIndexOf("."),filename.length());

            String newname = UUID.randomUUID().toString()+str;

            File filepath = new File(path, newname);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + newname));

            InputStream is = new FileInputStream(filepath);

            ossClient.putObject(bucket, newname, is);

            ossClient.shutdown();

            //将文件名set进stn
            stn.setImg(newname);
            stn.setStudentId((new IdWorker(1)).nextId());
            try {
                if(NotNull.isAllFieldNull(stn)){
                    manager.add(stn);
                }else {
                    return "error";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //更新班级表学生人数
        Grade grade = gradeService.findOne(stn.getGradeId());
        grade.setNum(grade.getNum()+1);
        gradeService.add(grade);

        return  "redirect:/manage/student/list.do";
    }

    @RequestMapping("/del")
    public String del(String id){
        Long studentid = Long.valueOf(id);
        manager.deledteVo(studentid);
        return  "redirect:/manage/student/list.do";
    }
    @RequestMapping("/showEdit")
    public ModelAndView showEdit(String id){
        ModelAndView modelAndView =new ModelAndView("manage/homework/editStnPage");
        List<Grade> grades= gradeService.findAll();

        Student stn  = manager.findOne(Long.valueOf(id));
        modelAndView.addObject("stn",stn);
        modelAndView.addObject("grades",grades);
        return modelAndView;
    }
    @RequestMapping("/edit")
    public String edit(Student stn, String stn_birthdayStr , HttpServletRequest request,
                             @RequestParam("filename") MultipartFile file,String studentId){

        Student student = manager.findOne(Long.valueOf(studentId));

        stn.setAvgNum(student.getAvgNum());
        stn.setSubjectNum(student.getSubjectNum());
        //设置日期
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stn_birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stn.setDate(date);

        if(!file.isEmpty()){

            OSSClient ossClient = new OSSClient(endPoint, accessKeyID, accessKeySecret);

            //上传文件路径
            String path = request.getServletContext().getRealPath("/images/");
            //上传文件名
            String filename = file.getOriginalFilename();

            String str =filename.substring(filename.lastIndexOf("."),filename.length());

            String newname = UUID.randomUUID().toString()+str;

            File filepath = new File(path, newname);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            try {
                file.transferTo(new File(path + File.separator + newname));
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is = null;
            try {
                is = new FileInputStream(filepath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ossClient.putObject(bucket, newname, is);

            ossClient.shutdown();

            //将文件名set进stn
            stn.setImg(newname);

        }else {
            stn.setImg(student.getImg());
        }

        stn.setStudentId(stn.getStudentId());

        try {
            if(NotNull.isAllFieldNull(stn)){
                manager.add(stn);
            }else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/manage/student/list.do";
    }
    @RequestMapping("/chooseGradePage")
    public  ModelAndView chooseGrade(String id){
        ModelAndView modelAndView = new ModelAndView("manage/homework/choosesubPage");
        modelAndView.addObject("stnid",id);

        List<String> newSubNames = subjectService.findNewSubNames(Long.valueOf(id));

        subjectService.findName();

        modelAndView.addObject("names", newSubNames);
        return modelAndView;
    }

    @RequestMapping("/choosegrade")
    public  String choosegrade(String name,String studentid){
        Subject subject = new Subject();

        subject.setSubjectId(new IdWorker(1).nextId());
        subject.setStudentId(Long.valueOf(studentid));
        subject.setAvgNum(0);
        subject.setName(name);

        subjectService.add(subject);

        Student student=manager.findOne(Long.valueOf(studentid));
        int num = subjectService.findSubjectNum(Long.valueOf(studentid));
        student.setSubjectNum(num);
        manager.add(student);
        return "redirect:/manage/student/list.do";
    }
    @RequestMapping("/writeScorePage")
    public  ModelAndView writeScorePage(String id){
        ModelAndView modelAndView = new ModelAndView("manage/homework/writeScorePage");
        modelAndView.addObject("stnid",id);

        List<String> overSubNames = subjectService.findOverSubNames(Long.valueOf(id));

        List<Subject> subjects = subjectService.findOneByStudentId(Long.valueOf(id));

        modelAndView.addObject("subjects", subjects);
        return modelAndView;
    }
    @RequestMapping("/writeScore")
    public  String writeScore(String studentid,String[] name,String[] avgNum){
        //更新学科分数
        Subject subject = new Subject();
        int sum = 0;
        for(int i=0;i<name.length;i++){
            sum=sum+Integer.valueOf(avgNum[i]);
            subject = subjectService.findOneByStnidAndName(Long.valueOf(studentid),name[i]);
            subject.setAvgNum(Integer.valueOf(avgNum[i]));
            subjectService.add(subject);
        }
        //更新学生平均分
        int avg = sum/avgNum.length;
        Student student = manager.findOne(Long.valueOf(studentid));
        student.setAvgNum(avg);
        manager.add(student);
        //更新班级平均分
        Grade grade = gradeService.findOne(student.getGradeId());

        int avgnum = (grade.getAvgNum()*grade.getNum()+avg)/grade.getNum();
        grade.setAvgNum(avgnum);
        gradeService.add(grade);

        return "redirect:/manage/student/list.do";
    }
}

