package org.geekcamp.http.client;

import org.geekcamp.HttpClientApplication;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;


public class HttpResponseTests {
    @Test
    void responseTests() throws Exception {
        HttpResponse httpResponse = HttpClientApplication.responseTest();

        httpResponse.setHttpVersion(httpResponse.getHttpVersion());
        assertEquals("HTTP/1.1",httpResponse.getHttpVersion());
        System.out.println("Version: " + httpResponse.getHttpVersion() + "   -> OK");

        httpResponse.setStatusCode(httpResponse.getStatusCode());
        assertEquals("200",httpResponse.getStatusCode());
        System.out.println("StatusCode: " + httpResponse.getStatusCode() + "   -> OK");

        httpResponse.setMessage(httpResponse.getMessage());
        assertEquals("OK",httpResponse.getMessage());
        System.out.println("Message: " + httpResponse.getMessage() + "   -> OK");


        httpResponse.addHeader("Server","nginx/1.18.0");
        assertEquals("nginx/1.18.0",httpResponse.getHeaders().get("Server"));

        final ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes(StandardCharsets.UTF_8));
        httpResponse.setRawData(buffer);
        assertEquals(buffer, httpResponse.getRawData());

        assertNotNull(httpResponse.getBody());
    }
}
