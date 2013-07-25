
package com.coverity.ws.v5;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ConfigurationServiceService", targetNamespace = "http://ws.coverity.com/v5", wsdlLocation = "http://localhost:14800/ws/v5/configurationservice?wsdl")
public class ConfigurationServiceService
    extends Service
{

    private final static URL CONFIGURATIONSERVICESERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.coverity.ws.v5.ConfigurationServiceService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.coverity.ws.v5.ConfigurationServiceService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:14800/ws/v5/configurationservice?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:14800/ws/v5/configurationservice?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        CONFIGURATIONSERVICESERVICE_WSDL_LOCATION = url;
    }

    public ConfigurationServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConfigurationServiceService() {
        super(CONFIGURATIONSERVICESERVICE_WSDL_LOCATION, new QName("http://ws.coverity.com/v5", "ConfigurationServiceService"));
    }

    /**
     * 
     * @return
     *     returns ConfigurationService
     */
    @WebEndpoint(name = "ConfigurationServicePort")
    public ConfigurationService getConfigurationServicePort() {
        return super.getPort(new QName("http://ws.coverity.com/v5", "ConfigurationServicePort"), ConfigurationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConfigurationService
     */
    @WebEndpoint(name = "ConfigurationServicePort")
    public ConfigurationService getConfigurationServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.coverity.com/v5", "ConfigurationServicePort"), ConfigurationService.class, features);
    }

}
