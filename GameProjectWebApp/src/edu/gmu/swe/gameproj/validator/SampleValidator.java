package edu.gmu.swe.gameproj.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.gmu.swe.gameproj.util.SampleData;

public class SampleValidator implements Validator {
	@Override
	public boolean supports(Class<?> arg0) {
		return SampleData.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		SampleData sampleData = (SampleData) obj;
		
		System.out.println("SampleValidator.validate()");
		if (sampleData.getFoo() == null || sampleData.getFoo().equals("")) {
			errors.rejectValue("foo", "requiredvalue", "The value is required.");
		} else if (sampleData.getFoo().equals("Foo")) {
			errors.rejectValue("foo", "requiredvalue", "Can't be set to Foo.");
		}
		
		if (sampleData.getBar() == null || sampleData.getBar().equals("")) {
			errors.rejectValue("bar", "requiredvalue", "The value is required.");
		} else if (sampleData.getBar().equals("Bar")) {
			errors.rejectValue("bar", "requiredvalue", "Can't be set to Bar.");
		}		
	}
}
