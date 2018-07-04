package com.neu.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.POJO.Song;


public class SongValidator implements Validator {

	public boolean supports(Class aClass) {
		return aClass.equals(Song.class);
	}


	
	public void validate(Object obj, Errors errors) {
		
		Song song = (Song) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "songName", "error.invalid.song", "Song Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "movieName", "error.invalid.song", "Movie Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Actor", "error.invalid.song", "Actor Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Actress", "error.invalid.song", "Actress Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fileName", "error.invalid.song", "Album Name Required");
		
	}

}
