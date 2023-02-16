package com.mustafa.grad.handywork.repository;

import com.mustafa.grad.handywork.entity.Job;
import com.mustafa.grad.handywork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository <Job, Long>{
    List<Job> findAllByOwner(User owner);

//    @Modifying
//    @Query("delete from Job j where j.id = :id")
//    @Transactional
//    void delete(Long id);
}
