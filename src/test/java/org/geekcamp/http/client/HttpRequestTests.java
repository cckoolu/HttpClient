package org.geekcamp.http.client;

import org.geekcamp.HttpClientApplication;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTests {
    @Test
    void requestTests() throws Exception {

        HttpRequest httpRequest = HttpClientApplication.requestTest();

        assertThrows(Exception.class, () -> httpRequest.setMethod("FOO"));

        for (HttpMethodEnum method : HttpMethodEnum.values()) {
            assertDoesNotThrow(() -> httpRequest.setMethod(method.toString()));
//            assertNotNull(httpRequest.getMethod());
            System.out.println(httpRequest.getMethod());
        }


        httpRequest.setUri(httpRequest.getUri());
        assertEquals("/api/login", httpRequest.getUri());
        System.out.println("Uri: " + httpRequest.getUri() + "   -> OK");

        httpRequest.setHttpVersion(httpRequest.getHttpVersion());
        assertEquals("HTTP/1.1", httpRequest.getHttpVersion());
        System.out.println("Version: " + httpRequest.getHttpVersion() + "   -> OK");

        final ByteBuffer buffer = ByteBuffer.wrap("Hello world!".getBytes(StandardCharsets.UTF_8));
        httpRequest.setRawData(buffer);
        assertEquals(buffer, httpRequest.getRawData());

    }
}
