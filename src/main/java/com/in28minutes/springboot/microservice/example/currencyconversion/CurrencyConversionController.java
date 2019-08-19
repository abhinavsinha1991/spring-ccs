package com.in28minutes.springboot.microservice.example.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.in28minutes.springboot.microservice.example.currencyconversion.exceptions.CCSDataNotFoundException;
import com.in28minutes.springboot.microservice.example.currencyconversion.exceptions.CCSServiceUnavailableException;

import feign.FeignException;

@Validated
@RestController
public class CurrencyConversionController
{

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @Autowired
    Environment environment;

    @GetMapping( "/currency-converter/from/{from}/to/{to}/quantity/{quantity}" )
    public CurrencyConversionBean convertCurrency( @PathVariable String from, @PathVariable String to,
                                                   @PathVariable BigDecimal quantity )
    {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put( "from", from );
        uriVariables.put( "to", to );

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
                uriVariables );

        CurrencyConversionBean response = responseEntity.getBody();

        return new CurrencyConversionBean( response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply( response.getConversionMultiple() ), response.getIp(), response.getPort() );
    }

    @GetMapping( "/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}" )
    public CurrencyConversionBean convertCurrencyFeign(
            @Pattern( regexp = "^[A-Z]{3}$", message = "Source currency code should be exactly 3 letters" )
            @PathVariable String from,
            @Pattern( regexp = "^[A-Z]{3}$", message = "Target currency code should be exactly 3 letters" )
            @PathVariable String to,
            @DecimalMax( "9999999999.99" ) @PathVariable BigDecimal quantity ) throws Exception
    {


        CurrencyConversionBean response = null;
        try
        {
            response = proxy.retrieveExchangeValue( from, to );
        }
        catch ( FeignException nfex )
        {
            logger.error( "Service call to Forex failed: {}",nfex.getMessage() );
            if ( nfex.status() == 404 )
            {
                throw new CCSDataNotFoundException( nfex.getCause().getMessage() );
            }
            else
            {
                throw new CCSServiceUnavailableException( "Can't reach Forex service" );
            }
        }
        catch ( Exception e )
        {
            logger.error( "Internal error encountered..{}",e.getMessage() );
            throw new Exception( "Internal server error" );
        }

        logger.info( "{}", response );

        return new CurrencyConversionBean( response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply( response.getConversionMultiple() ), response.getIp(), response.getPort() );
    }

}