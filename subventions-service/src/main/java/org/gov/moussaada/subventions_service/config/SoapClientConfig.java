package org.gov.moussaada.subventions_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class SoapClientConfig {

    @Value("${soap.endpoint}")
    private String soapEndpoint;

    @Value("${soap.apikey}")
    private String soapApiKey;

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(jaxbMarshaller());
        template.setUnmarshaller(jaxbMarshaller());
        template.setDefaultUri(soapEndpoint);

        ClientInterceptor[] interceptors = new ClientInterceptor[]{new SoapApiKeyInterceptor(soapApiKey)};
        template.setInterceptors(interceptors);

        return template;
    }

    @Bean
    public Jaxb2Marshaller jaxbMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("io.spring.guides.gs_producing_web_service"); // package généré par xjc
        return marshaller;
    }

}
