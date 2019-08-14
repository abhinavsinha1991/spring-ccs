package com.in28minutes.springboot.microservice.example.currencyconversion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{



  @ExceptionHandler(CCSServiceUnavailableException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public final CCSServiceUnavailableException handleServiceUnavailableExceptions(CCSServiceUnavailableException ex, WebRequest request) {
    return ex;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public final CCSException handleAllExceptions(Exception ex, WebRequest request) {
    return new CCSException( ex ,HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
  public final CCSUncheckedException handleAllRuntimeException(CCSUncheckedException ex, WebRequest request) {
    return ex;
  }

}
