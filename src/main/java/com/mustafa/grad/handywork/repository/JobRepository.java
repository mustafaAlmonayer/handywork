package com.mustafa.grad.handywork.repository;

import com.mustafa.grad.handywork.entity.Job;
import com.mustafa.grad.handywork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository <Job, Long>{

    List<Job> findAllByOwner(User owner);

}
