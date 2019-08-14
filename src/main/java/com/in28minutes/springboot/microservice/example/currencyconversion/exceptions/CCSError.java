/*
 * Copyright 2019 Applied Card Technologies Ltd
 */
package com.in28minutes.springboot.microservice.example.currencyconversion.exceptions;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author sinhaab
 */

@Data
@Builder
@AllArgsConstructor
public class CCSError implements Serializable
{

    private String developerMessage;
    private String userMessage;
    private String internalMessage;
    private List<CCSValidationError> validationErrors;

    /**
     * @return The developerMessage.
     */
    public String getDeveloperMessage()
    {
        return developerMessage;
    }

    /**
     * @return The userMessage.
     */
    public String getUserMessage()
    {
        return userMessage;
    }

        /**
     * @return The internalMessage.
     */
    public String getInternalMessage()
    {
        return internalMessage;
    }

    /**
     * @return The validationErrors.
     */
    public List<CCSValidationError> getValidationErrors()
    {
        return validationErrors;
    }
}
