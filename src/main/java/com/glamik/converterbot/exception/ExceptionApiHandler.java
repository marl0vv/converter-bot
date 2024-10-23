package com.glamik.converterbot.exception;

import com.glamik.converterbot.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionApiHandler {

    @ExceptionHandler(ConversionTaskNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDto notFoundException(ConversionTaskNotFoundException e) {
        return ErrorDto.of(e.getMessage());
    }
}
