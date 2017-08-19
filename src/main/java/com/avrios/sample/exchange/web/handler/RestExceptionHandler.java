package com.avrios.sample.exchange.web.handler;

import com.avrios.sample.exchange.dto.ErrorResponseDto;
import com.avrios.sample.exchange.exception.CustomException;
import com.avrios.sample.exchange.exception.ErrorCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleCustomException(CustomException e) {
        log.error("Runtime Exception thrown: {}", e);
        return new ErrorResponseDto(e.getCode().getCode(), e.getDetails());
    }

    @ExceptionHandler({InvalidFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidFormatException(Exception e) {
        log.error("Invalid format exception: {}", e);
        return new ErrorResponseDto(ErrorCode.DATE_WRONG_FORMAT.getCode(), ErrorCode.Constants.DATE_WRONG_FORMAT_MSG);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleRequestBodyNotValidException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponseDto(ErrorCode.INCORRECT_REQUEST_BODY.getCode());
    }
}
