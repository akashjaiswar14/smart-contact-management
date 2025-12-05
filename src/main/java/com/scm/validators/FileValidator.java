package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>{

    private static final long MAX_FILE_SIZE = 1024*1024*5;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        
        if (file == null || file.isEmpty()) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return true;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2 MB").addConstraintViolation();
            return false;
        }


        // this is for checking the actual height and width of the image
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        return true;

    }

}
