package com.biz.lesson.business.stn;

import com.biz.lesson.dao.stn.GradeRepository;
import com.biz.lesson.model.stn.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeDao;

    //修改/添加
    public void add(Grade grade){
        gradeDao.save(grade);

    }

    //删除
    public void delete(Long id){
        gradeDao.deleteByGradeId(id);
    }

    //查找所有
    public List<Grade> findAll(){


        return (List<Grade>) gradeDao.findAll();

    }
    //查找一个班级
    public Grade findOne(Long id){


        return  gradeDao.findOne(id);

    }


}
