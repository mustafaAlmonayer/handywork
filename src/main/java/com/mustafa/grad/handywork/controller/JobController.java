package com.mustafa.grad.handywork.controller;

import com.mustafa.grad.handywork.entity.Job;
import com.mustafa.grad.handywork.entity.User;
import com.mustafa.grad.handywork.repository.JobRepository;
import com.mustafa.grad.handywork.repository.UserRepository;
import com.mustafa.grad.handywork.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/all")
    public String getAllJobs(Model model) {
        List<Job> jobs = jobRepository.findAll();

        for (Job job : jobs) {
            job.setUrlsFirstIndex();
        }

        model.addAttribute("usersJobs", jobs);
        return "/jobs/getAll";
    }

    @GetMapping(value = "/view/{id}")
    public String getOneJob(@PathVariable Long id, Model model) {

        Optional<Job> job = jobRepository.findById(id);

        if (!job.isPresent()) {
            throw new RuntimeException("job not found - 404");
        }

        Job dbJob = job.get();

        dbJob.setUrlsFirstIndex();

        model.addAttribute("job", dbJob);
        return "/jobs/getOne";
    }

    // get job form to create

    @GetMapping(value = "/create/{id}")
    public String getJobCreateFrom(@PathVariable Long id, Model model) {

        Optional<User> user = userRepository.findById(id);
        Job job = new Job();

        if (user.isPresent()) {
            job.setOwner(user.get());
        } else {
            throw new RuntimeException("user not found - 404");
        }

        model.addAttribute("userJob", job);
        return "/jobs/formForCreate";
    }

    // post job to save or update

    @PostMapping(value = "/create")
    public String createJob(@Valid @ModelAttribute("userJob") Job job, BindingResult bindingResult) {

        System.out.println(bindingResult.getErrorCount());

        if (bindingResult.getErrorCount() > 0) {
            return "/jobs/formForCreate";
        }

        if (job.getImages().get(0).getSize() != 0) {
            job.setImagesUrls(ImageUtils.ImagesToUrls(job.getImages()));
        }

        job.setPublishDate(new Date());
        System.out.println(new Date());
        jobRepository.save(job);
        return "redirect:/users/" + job.getOwner().getId();
    }

    // get job form for update

    @GetMapping(value = "/update/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Optional<Job> job = jobRepository.findById(id);

        if (job.isPresent()) {
            model.addAttribute("userJob", job.get());
            return "/jobs/formForCreate";
        }

        throw new RuntimeException("Job not found - " + id);
    }

    // get job for delete

    @GetMapping(value = "/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        Optional<Job> dataBaseJob = jobRepository.findById(id);

        if (!dataBaseJob.isPresent()) {
            throw new RuntimeException("job not found - " + id);
        }

        Job job = dataBaseJob.get();
        Long owner = job.getOwner().getId();

        Optional<User> user = userRepository.findById(job.getOwner().getId());

        User dbUser;

        if (user.isPresent()) {
            dbUser = user.get();
        } else {
            throw new RuntimeException("User not found 404");
        }

        System.out.println("\n\n" + dbUser.getJobs().remove(dbUser.findJobIndex(job.getId())) + "\n\n");
        userRepository.save(dbUser);
        jobRepository.delete(job);

        return "redirect:/users/" + owner;
    }

}
