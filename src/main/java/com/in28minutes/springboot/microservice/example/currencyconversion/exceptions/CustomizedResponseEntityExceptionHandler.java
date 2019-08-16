package com.in28minutes.springboot.microservice.example.currencyconversion.exceptions;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler( CCSDataNotFoundException.class )
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public final CCSDataNotFoundException handleDataNotFoundExceptions( CCSDataNotFoundException ex,
                                                                              WebRequest request )
    {

        return ex;
    }

    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public final CCSException handleValidationExceptions( ConstraintViolationException ex,
                                                                  WebRequest request )
    {

        CCSValidationError.CCSValidationErrorBuilder ccsValidationErrorBuilder = CCSValidationError.builder();

        CCSException ccsException = new CCSException( CCSError.builder()
                .validationErrors(
                        ex.getConstraintViolations()
                                .stream()
                                .map( a -> ccsValidationErrorBuilder.element( a.getInvalidValue().toString() )
                                        .error( a.getMessage() ).build() )
                                .collect( Collectors.toList() ) )
                .developerMessage( "Validation error." )
                .userMessage( "Wrong input given.Retry with correct inputs." )
                .internalMessage( "Validation error." )
                .build(), HttpStatus.BAD_REQUEST );

        return ccsException;
    }

    @ExceptionHandler( CCSServiceUnavailableException.class )
    @ResponseStatus( HttpStatus.SERVICE_UNAVAILABLE )
    public final CCSServiceUnavailableException handleServiceUnavailableExceptions( CCSServiceUnavailableException ex,
                                                                                    WebRequest request )
    {
        return ex;
    }

    @ExceptionHandler( Exception.class )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public final CCSException handleAllExceptions( Exception ex, WebRequest request )
    {
        return new CCSException( ex, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler( RuntimeException.class )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public final CCSUncheckedException handleAllRuntimeException( CCSUncheckedException ex, WebRequest request )
    {
        return ex;
    }

}
