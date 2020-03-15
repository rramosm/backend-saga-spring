package pe.com.empresa.poc.azure.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import pe.com.empresa.poc.azure.core.helper.ErrorConstant;
import pe.com.empresa.poc.azure.core.helper.SagaMessageProperties;
import pe.com.empresa.poc.azure.core.exception.BusinessCoreException;

@Component
public class BaseController {

    @Autowired
    private SagaMessageProperties sagaMessageProperties;

    protected void getBindingResultError(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BusinessCoreException(ErrorConstant.REQUEST_INVALID, sagaMessageProperties.getRequestInvalid(),
                    sagaMessageProperties.getRequestInvalidShort());
        }
    }
}
