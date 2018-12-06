package com.backbase.geocoding.exercise;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class GeoCodingRequestProcessor implements Processor {

    private static Properties properties;

    GeoCodingRequestProcessor() {
        try {
            properties = getProperties();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load properties file");
        }
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new ClassPathResource(Constants.PROPERTIES_FILE).getInputStream());
        return properties;
    }

    @Override
    public void process(Exchange exchange) {
        String address = (String) exchange.getIn().getHeader(Constants.ADDRESS_QUERY_PARAMETER);
        Message outMessage = exchange.getOut();
        outMessage.setHeader(Constants.ADDRESS_QUERY_PARAMETER, address);
        outMessage.setHeader(Constants.API_KEY_QUERY_PARAMETER, properties.getProperty(Constants.API_KEY_PROPERTY_NAME));
    }
}
