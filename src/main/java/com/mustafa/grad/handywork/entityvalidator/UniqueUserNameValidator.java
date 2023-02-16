package com.mustafa.grad.handywork.entityvalidator;

import com.mustafa.grad.handywork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String>{

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UniqueUserName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        try {
            if (userName != null) {
                return !userRepository.existsByUserName(userName);
            } else {
                return true;
            }
        } catch (NullPointerException e){
            return true;
        }
    }

}
