package com.example.waikan.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseErrorValidation {

    public Map<String, String> mapValidationService(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            if (result.getAllErrors() != null) {
                for (ObjectError er : result.getAllErrors()) {
                    errorMap.put(er.getCode(), er.getDefaultMessage());
                }
            }

            for (FieldError fieldError : result.getFieldErrors()) {
                System.out.println(fieldError.getField() + "Error");
                errorMap.put(fieldError.getField() + "Error", fieldError.getDefaultMessage());
            }

            return errorMap;
        }
        return null;
    }

}
