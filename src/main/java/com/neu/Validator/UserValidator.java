package com.neu.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.POJO.User;



public class UserValidator implements Validator
{
	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}

	
	public void validate(Object obj, Errors errors) 
	{
		User user = (User) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.user", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.user", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.invalid.user", "User Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.user", "Password Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.user", "Email Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userRole", "error.invalid.user", "User Role Required");
		
		
	}

	

}
