package com.mustafa.grad.handywork.entityvalidator;

import com.mustafa.grad.handywork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        try {
            if (phoneNumber != null) {
                return !userRepository.existsByPhoneNumber(phoneNumber);
            } else {
                return true;
            }
        } catch (NullPointerException e){
            return true;
        }

    }
}
