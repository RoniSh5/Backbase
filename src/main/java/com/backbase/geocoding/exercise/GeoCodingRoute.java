package com.backbase.geocoding.exercise;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class GeoCodingRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("jetty:http://localhost:8081/")
                .process(new GeoCodingRequestProcessor())
                .recipientList(simple("jetty:https://maps.googleapis.com/maps/api/geocode/xml?address=${header.address}&key=${header.apiKey}"))
                .convertBodyTo(String.class)
                .process(new GeoCodingResponseProcessor())
                .marshal().json(JsonLibrary.Jackson);
    }
}
