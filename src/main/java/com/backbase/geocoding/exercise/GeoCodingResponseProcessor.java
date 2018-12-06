package com.backbase.geocoding.exercise;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

public class GeoCodingResponseProcessor implements Processor {

    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    @Override
    public void process(Exchange exchange) {
        String input = (String) exchange.getIn().getBody();
        exchange.getOut().setBody(parse(input), AddressInfo.class);
    }

    AddressInfo parse(String input) {
        try {
            Document document = getDocument(input);
            XPath xpath = getXPath();
            return getAddressInfo(document, xpath);
        } catch (Exception e) {
            throw new IllegalArgumentException("The response could not be parsed to XML");
        }
    }

    private Document getDocument(String input) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(new InputSource(new StringReader(input)));
    }

    private XPath getXPath() {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        return xPathfactory.newXPath();
    }

    private AddressInfo getAddressInfo(Document document, XPath xpath) throws XPathExpressionException {
        XPathExpression expression = xpath.compile("/GeocodeResponse/result");
        if (expression.evaluate(document).equals(Constants.NO_VALUE)) {
            return new AddressInfo.Builder().withFormattedAddress(Constants.NO_VALUE).withLatitude(0.0).withLongitude(0.0).build();
        }
        return new AddressInfo.Builder()
                .withFormattedAddress(xpath.compile("/GeocodeResponse/result/formatted_address").evaluate(document))
                .withLatitude(Double.parseDouble(xpath.compile("/GeocodeResponse/result/geometry/location/lat").evaluate(document)))
                .withLongitude(Double.parseDouble(xpath.compile("/GeocodeResponse/result/geometry/location/lng").evaluate(document)))
                .build();
    }
}
