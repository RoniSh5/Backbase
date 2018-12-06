package com.backbase.geocoding.exercise;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class GeoCodingResponseProcessorTest {

    private static final GeoCodingResponseProcessor GEO_CODING_RESPONSE_PROCESSOR = new GeoCodingResponseProcessor();

    @Test
    public void testZeroResults() {
        String response = "<GeocodeResponse>\n" +
                "<status>ZERO_RESULTS</status>\n" +
                "<plus_code>\n" +
                "<global_code>8G4P3PXW+VM</global_code>\n" +
                "<compound_code>3PXW+VM Tel Aviv-Yafo, Israel</compound_code>\n" +
                "</plus_code>\n" +
                "</GeocodeResponse>";

        AddressInfo addressInfo = GEO_CODING_RESPONSE_PROCESSOR.parse(response);
        assertEquals("", addressInfo.getFormattedAddress());
        assertEquals(0.0, addressInfo.getLatitude(), 0.0);
        assertEquals(0.0, addressInfo.getLongitude(), 0.0);
    }

    @Test
    public void testValidResponse() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("xml_response_example.txt");
        String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        AddressInfo addressInfo = GEO_CODING_RESPONSE_PROCESSOR.parse(response);
        assertEquals("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA", addressInfo.getFormattedAddress());
        assertEquals(37.4217550, addressInfo.getLatitude(), 0.0);
        assertEquals(-122.0846330, addressInfo.getLongitude(), 0.0);
    }
}
