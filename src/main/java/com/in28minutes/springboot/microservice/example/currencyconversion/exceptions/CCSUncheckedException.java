package com.in28minutes.springboot.microservice.example.currencyconversion.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
@JsonIgnoreProperties( { "localizedMessage", "message", "suppressed", "stackTrace", "cause" } )
public class CCSUncheckedException extends Exception
{

    private static final long serialVersionUID = 7718828512143293558L;

    private int status;

    private List<CCSError> errors = new ArrayList<>();

    public CCSUncheckedException( String message, Throwable cause, HttpStatus status )
    {
        super( message, cause );
        this.errors.add( CCSError.builder()
                .internalMessage( message )
                .developerMessage( cause.getMessage() )
                .build() );

        this.status = status.value();
    }

    public CCSUncheckedException( String message, HttpStatus status )
    {
        super( message );
        this.status = status.value();
        this.errors.add( CCSError.builder().internalMessage( message ).build() );
    }

    public CCSUncheckedException( Throwable cause, HttpStatus status )
    {
        super( cause );

        this.status = status.value();

        this.errors.add( CCSError.builder()
                .internalMessage( cause.getMessage() )
                .developerMessage( cause.getMessage() )
                .build() );
    }

    /**
     * @return The status.
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus( final int status )
    {
        this.status = status;
    }

    /**
     * @return The errors.
     */
    public List<CCSError> getErrors()
    {
        return errors;
    }

    /**
     * @param errors The errors to set.
     */
    public void setErrors(
            final List<CCSError> errors )
    {
        this.errors = errors;
    }
}