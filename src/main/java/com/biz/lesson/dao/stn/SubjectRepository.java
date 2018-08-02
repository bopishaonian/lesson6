package com.biz.lesson.dao.stn;


import com.biz.lesson.model.stn.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject,Long>,JpaSpecificationExecutor<Subject> {

    @Modifying
    @Transactional
    @Query(value = "update usr_subject s set s.avgNum=:avgNum  where s.studentId = :studentId",nativeQuery = true)
    public void updateAvg(@Param("studentId") Long studentId, @Param("avgNum") int avgNum);

    public List<Subject> findByStudentId(@Param("studentId") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from usr_subject where name=(select name from usr_subject where subjectId=:subjectId)",nativeQuery = true)
    public void deleteBySubjectId(@Param("subjectId") Long subjectId);



    @Query(value = "SELECT subjectId,avgNum ,name,studentId,COUNT(subjectId) count,sum(avgNum) avg FROM usr_subject GROUP BY name ORDER BY count DESC",nativeQuery = true)
    List<Subject> findAll();


    @Modifying
    @Transactional
    @Query(value = "update usr_subject set name=:newName where name=:oldName",nativeQuery = true)
    public  void  updateByName(@Param("newName") String newName, @Param("oldName") String oldName);

    @Query(value = "select name from usr_subject group by name",nativeQuery = true)
    List<String> findAllname();
    //通过studentId查询行数
    @Query(value = "select COUNT(subjectId) AS count from usr_subject where studentid=?1",nativeQuery = true)
    int findSubjectNum(Long studentId);


    Subject findAllByStudentIdAndName(Long studentId,String name);

    List<Subject> findAllByStudentId(Long studentId);

    @Modifying
    void deleteAllByStudentId(Long studentId);


    public List<Subject> findAllByName(String name);

    void deleteAllByName(String name);
}
