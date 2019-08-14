package com.in28minutes.springboot.microservice.example.currencyconversion.exceptions;

/**
 * @author sinhaab
 */
public class CCSValidationError
{

    private String element;
    private String error;

    /**
     * @return The element.
     */
    public String getElement()
    {
        return element;
    }

    /**
     * @param element The element to set.
     */
    public void setElement( final String element )
    {
        this.element = element;
    }

    /**
     * @return The error.
     */
    public String getError()
    {
        return error;
    }

    /**
     * @param error The error to set.
     */
    public void setError( final String error )
    {
        this.error = error;
    }
}
