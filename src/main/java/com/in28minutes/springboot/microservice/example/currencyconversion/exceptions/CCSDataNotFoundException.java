package com.in28minutes.springboot.microservice.example.currencyconversion.exceptions;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( { "localizedMessage", "message", "suppressed", "stackTrace", "cause" } )
public class CCSDataNotFoundException extends Exception
{

    private static final long serialVersionUID = 7718828512143293558L;

    private int status = 404;

    private List<CCSError> errors = new ArrayList<>();

    public CCSDataNotFoundException( String message, Throwable cause )
    {
        super( message, cause );
        this.errors.add( CCSError.builder()
                .internalMessage( message )
                .developerMessage( message )
                .userMessage( "Requested data not found" )
                .build() );
    }

    public CCSDataNotFoundException( String message )
    {
        super( message );
        this.errors.add( CCSError.builder()
                .internalMessage( message )
                .developerMessage( message )
                .userMessage( "Requested data not found" )
                .build() );
    }

    public CCSDataNotFoundException( Throwable cause )
    {
        super( cause );

        this.errors.add( CCSError.builder()
                .internalMessage( cause.getMessage() )
                .developerMessage( cause.getMessage() )
                .userMessage( "Requested data not found" )
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