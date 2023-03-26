package com.springboot.springbootmonitoring.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValuesAllowed.Validator.class })
public @interface ValuesAllowed {

	String message() default "Field value should be from list of ";

	Class<?>[] groups() default {};

	String propName();

	String[] values();

	class Validator implements ConstraintValidator<ValuesAllowed, String> {
		private String propName;
		private String message;
		private List<String> allowable;

		@Override
		public void initialize(ValuesAllowed requiredIfChecked) {
			this.propName = requiredIfChecked.propName();
			this.message = requiredIfChecked.message();
			this.allowable = Arrays.asList(requiredIfChecked.values());
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			Boolean valid = this.allowable.contains(value);
			System.out.println("isValid = " + valid);
			if (!valid) {
				UUID uuid = UUID.fromString(value);
				System.out.println(uuid);
			}

			if (!Boolean.TRUE.equals(valid)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(message.concat(this.allowable.toString()))
						.addPropertyNode(this.propName).addConstraintViolation();
			}
			return valid;
		}
	}
}
