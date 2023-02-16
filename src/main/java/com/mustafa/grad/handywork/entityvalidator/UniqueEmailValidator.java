package com.mustafa.grad.handywork.entityvalidator;

import com.mustafa.grad.handywork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        System.out.println(email);
        try {
            if (email != null) {
                return !userRepository.existsByEmail(email);
            } else {
                return true;
            }
        } catch (NullPointerException e){
            return true;
        }
    }

}
