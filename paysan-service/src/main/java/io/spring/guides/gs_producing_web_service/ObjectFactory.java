//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.06.22 at 10:30:47 PM WEST 
//


package io.spring.guides.gs_producing_web_service;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the io.spring.guides.gs_producing_web_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetInfoResponse_QNAME = new QName("http://spring.io/guides/gs-producing-web-service", "getInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.spring.guides.gs_producing_web_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTerreRequest }
     * 
     */
    public GetTerreRequest createGetTerreRequest() {
        return new GetTerreRequest();
    }

    /**
     * Create an instance of {@link GetTerreResponse }
     * 
     */
    public GetTerreResponse createGetTerreResponse() {
        return new GetTerreResponse();
    }

    /**
     * Create an instance of {@link Proprietaires }
     * 
     */
    public Proprietaires createProprietaires() {
        return new Proprietaires();
    }

    /**
     * Create an instance of {@link GetInformationRequest }
     * 
     */
    public GetInformationRequest createGetInformationRequest() {
        return new GetInformationRequest();
    }

    /**
     * Create an instance of {@link GetInformationsResponse }
     * 
     */
    public GetInformationsResponse createGetInformationsResponse() {
        return new GetInformationsResponse();
    }

    /**
     * Create an instance of {@link GetInfoResponse }
     * 
     */
    public GetInfoResponse createGetInfoResponse() {
        return new GetInfoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://spring.io/guides/gs-producing-web-service", name = "getInfoResponse")
    public JAXBElement<GetInfoResponse> createGetInfoResponse(GetInfoResponse value) {
        return new JAXBElement<GetInfoResponse>(_GetInfoResponse_QNAME, GetInfoResponse.class, null, value);
    }

}
