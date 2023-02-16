package com.mustafa.grad.handywork.controller;

import com.mustafa.grad.handywork.entity.Job;
import com.mustafa.grad.handywork.entity.User;
import com.mustafa.grad.handywork.repository.JobRepository;
import com.mustafa.grad.handywork.repository.UserRepository;
import com.mustafa.grad.handywork.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;

    // get all users

    @GetMapping()
    public String home() {
        return "/users/list";
    }

    // get one user

    @GetMapping(value = "/{id}")
    public String getUserById(Model model, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        User loadedUser;

        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        loadedUser = user.get();

        loadedUser.setJobs(jobRepository.findAllByOwner(loadedUser));

        if (loadedUser.getProfilePicture() == null) {
            loadedUser.setProfilePicture("https://res.cloudinary.com/dsgouaw4m/image/upload/v1671821149/blank-profile-picture-973460_kvr8i6.svg");
        } else if (loadedUser.getProfilePicture().isEmpty()) {
            loadedUser.setProfilePicture("https://res.cloudinary.com/dsgouaw4m/image/upload/v1671821149/blank-profile-picture-973460_kvr8i6.svg");
        }

        for (Job job: user.get().getJobs()) {
            job.setUrlsFirstIndex();
        }

        model.addAttribute("ModelUser", loadedUser);

        return "/users/page2";
    }

    // get the form to make a user

    @GetMapping(value = "/register")
    public String getUserFormForRegistration(Model model) {
        User user = new User();
        user.setAuthority("customer");
        model.addAttribute("ModelUser", user);
        return "/users/form";
    }

    // post to save a user

    @PostMapping(value = "/register")
    public String saveUser(@Valid @ModelAttribute("ModelUser") User modelUser, BindingResult bindingResult) {

        if (bindingResult.getErrorCount() > 0) {
            return "/users/form";
        }

        if (!modelUser.getProfilePicFile().isEmpty() || modelUser.getProfilePicFile() != null) {
            modelUser.setProfilePicture(ImageUtils.ImageToUrl(modelUser.getProfilePicFile()));
        }

        userRepository.save(modelUser);
        return "redirect:/users";
    }

    // start of updating user account

    // get the update list

    @GetMapping(value = "/update/{id}")
    public String getUserFormsForUpdate(@PathVariable Long id, Model model) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            model.addAttribute("id", id);
            return "users/update/page";
        }

        throw new RuntimeException("User not found 404");
    }

    // get the form for update first name last name

    @GetMapping(value = "/update/firstLastName/{id}")
    public String getUserFormForUpdateFirstLastName(@PathVariable Long id, Model model) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            model.addAttribute("ModelUser", dbUser);
            return "users/update/first-last-name";
        }

        throw new RuntimeException("User not found 404");
    }

    // post to update first and last name

    @PostMapping(value = "/update/firstLastName")
    public String updateUserFirstLastName(@Valid @ModelAttribute("ModelUser") User modelUser, BindingResult bindingResult) {

        if (bindingResult.getErrorCount() > 3) {
            return "users/update/first-last-name";
        }

        userRepository.updateUserFirstName(modelUser.getId(), modelUser.getFirstName());
        userRepository.updateUserLastName(modelUser.getId(), modelUser.getLastName());

        return "redirect:/users/update/" + modelUser.getId();
    }

    // get the form for update phone number

    @GetMapping(value = "/update/phoneNumber/{id}")
    public String getUserFormForUpdatePhoneNumber(@PathVariable Long id, Model model) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            model.addAttribute("ModelUser", dbUser);
            return "users/update/phone-number";
        }

        throw new RuntimeException("User not found 404");
    }


    // post to update phone number

    @PostMapping(value = "/update/phoneNumber")
    public String updateUserPhoneNumber(@Valid @ModelAttribute("ModelUser") User modelUser, BindingResult bindingResult) {

        if (bindingResult.getErrorCount() > 3) {
            return "users/update/phone-number";
        }

        userRepository.updateUserPhoneNumber(modelUser.getId(), modelUser.getPhoneNumber());

        return "redirect:/users/update/" + modelUser.getId();
    }

    // get the form for update username

    @GetMapping(value = "/update/userName/{id}")
    public String getUserFormForUpdateUserName(@PathVariable Long id, Model model) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            model.addAttribute("ModelUser", dbUser);
            return "users/update/user-name";
        }

        throw new RuntimeException("User not found 404");
    }

    // post to update username

    @PostMapping(value = "/update/userName")
    public String updateUserUserName(@Valid @ModelAttribute("ModelUser") User modelUser, BindingResult bindingResult) {

        if (bindingResult.getErrorCount() > 2) {
            return "users/update/user-name";
        }

        userRepository.updateUserUserName(modelUser.getId(), modelUser.getUserName());

        return "redirect:/users/update/" + modelUser.getId();
    }

    // get the form for update email

    @GetMapping(value = "/update/email/{id}")
    public String getUserFormForUpdateEmail(@PathVariable Long id, Model model) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            model.addAttribute("ModelUser", dbUser);
            return "users/update/email";
        }

        throw new RuntimeException("User not found 404");
    }

    // post for update email

    @PostMapping(value = "/update/email")
    public String updateUserEmail(@Valid @ModelAttribute("ModelUser") User modelUser, BindingResult bindingResult) {

        if (bindingResult.getErrorCount() > 2) {
            return "users/update/email";
        }

        userRepository.updateUserEmail(modelUser.getId(), modelUser.getEmail());

        return "redirect:/users/update/" + modelUser.getId();
    }

    // get the form for update password

    @GetMapping(value = "/update/password/{id}")
    public String getUserFormForUpdatePassword(@PathVariable Long id, Model model) {
        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isPresent()) {
            model.addAttribute("ModelUser", dbUser);
            return "users/update/password";
        }

        throw new RuntimeException("User not found 404");
    }

    // post for update password
    @PostMapping(value = "/update/password")
    public String updateUserPassword(@Valid @ModelAttribute("ModelUser") User modelUser, BindingResult bindingResult) {

        if (bindingResult.getErrorCount() > 2) {
            return "users/update/password";
        }

        userRepository.updateUserPassword(modelUser.getId(), modelUser.getPassword());

        return "redirect:/users/update/" + modelUser.getId();
    }

    @GetMapping(value = "/page2")
    public String page2() {
        return "/users/page2";
    }

    // end of updating users account

    // string white space trimmer

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}

