package org.gov.moussaada.admin_service.config;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.namespace.QName;

public class SoapApiKeyInterceptor implements ClientInterceptor {

    private final String apiKey;

    public SoapApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        SaajSoapMessage soapMessage = (SaajSoapMessage) messageContext.getRequest();
        soapMessage.getSoapHeader().addHeaderElement(
                        new QName("http://spring.io/guides/gs-producing-web-service", "ApiKey")).setText(apiKey);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }


    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }
}
