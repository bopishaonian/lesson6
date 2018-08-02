package com.biz.lesson.dao.stn;


import com.biz.lesson.model.stn.Student;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface StudentRepository extends PagingAndSortingRepository<Student,Long>,JpaSpecificationExecutor<Student> {

    @Modifying
    @Transactional
    @Query(value = "delete from usr_student where studentId=:studentId",nativeQuery = true)
    public void deleteByStudentId(@Param("studentId") Long studentId);

    public void findByStudentId(@Param("studentId") Long studentId);
    @Modifying
    @Transactional
    @Query(value = "update usr_student s set s.avgNum=:avgNum  where s.studentId = :studentId",nativeQuery = true)
    public void updateAvg(@Param("studentId") Long studentId, @Param("avgNum") int avgNum);

    List<Student> findAllByGradeId(Long gradeid);
}
