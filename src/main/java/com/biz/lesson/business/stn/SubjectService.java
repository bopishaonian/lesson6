package com.biz.lesson.business.stn;

import com.biz.lesson.dao.stn.SubjectRepository;
import com.biz.lesson.model.stn.Subject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubjectService {

    @Autowired
    private SubjectRepository subjectDao;

    //添加+修改
    public void add(Subject subject){
        subjectDao.save(subject);

    }

    //删除一门学科
    public void deleteName(Long id){
        subjectDao.deleteBySubjectId(id);
    }

    //删除一条记录
    public  void deleteOne(Long id){
        subjectDao.delete(id);
    }

    //查找所有
    public List<Subject> findAll(){

        List<Subject> all = subjectDao.findAll();

        return all;
    }
    //
    public  Subject findOne(Long id){
      return subjectDao.findOne(id);
    }

    public List<String> findName(){
        return subjectDao.findAllname();
    }
    //查询某个学生的选修门数
    public  int findSubjectNum(Long id){
        return subjectDao.findSubjectNum(id);
    }
    //查询某个学生已选课程
    public  List<String> findOverSubNames(Long id){
        List<Subject> subs = subjectDao.findByStudentId(id);
        List<String> names = new ArrayList<>();
        for(int i=0;i<subs.size();i++){
            names.add(subs.get(i).getName());
        }
        return names;
    }
    //查询某个学生剩余课程
    public  List<String> findNewSubNames(Long id){

        List<String> allSubNames = this.findName();
        List<String> overSubNames = this.findOverSubNames(id);

        List<String> newSubNames = (List<String>) CollectionUtils.disjunction(allSubNames, overSubNames);

        return newSubNames;
    }
    public  Subject findOneByStnidAndName(Long studentid,String name){

        return subjectDao.findAllByStudentIdAndName(studentid,name);
    }
    public  List<Subject> findOneByStudentId(Long studentid){
        return subjectDao.findAllByStudentId(studentid );
    }
    public  void delByStudentId(Long studentid){
        subjectDao.deleteAllByStudentId(studentid);
    }
    public  List<Subject> findByName(String name){

        return subjectDao.findAllByName(name);
    }
    //删除一个课程
    public void deleteAllByName(String name){
        subjectDao.deleteAllByName(name);
    }
}
