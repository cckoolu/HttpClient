package org.geekcamp;

import org.geekcamp.http.client.HttpRequest;
import org.geekcamp.http.client.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

public class HttpClientApplication {
    public static void main(String[] args) throws Exception {
        responseTest();
        requestTest();

    }

//    存储request对象
    public static HttpRequest requestTest() throws Exception {
        final String requestFilename = "src/main/resources/request.txt";
        // Class<HttpClientApplication> clazz = HttpClientApplication.class;
        // InputStream inputStream = clazz.getResourceAsStream("/request.txt");
        final String requestStream = Files.readString(Path.of(requestFilename));

        final int requestBodyIndex = requestStream.indexOf("\r\n\r\n");
        final String requestBody = requestStream.substring(requestBodyIndex + 4);

        final int requestStartLineIndex = requestStream.indexOf("\r\n");
        final String requestStartLine = requestStream.substring(0, requestStartLineIndex);

        final int requestHeadersBeginIndex = requestStartLineIndex + 2;
        final int requestHeadersEndIndex = requestBodyIndex;
        final String requestHeaders = requestStream.substring(requestHeadersBeginIndex, requestHeadersEndIndex);
////        request内容
//        System.out.println(requestStartLine);
//        System.out.println(requestHeaders);
//        System.out.println(requestBody);

        HttpRequest httpRequest = new HttpRequest();
        requestStartLineHandle(requestStartLine, 0, httpRequest);
        requestHeaderHandle(requestHeaders, httpRequest);

        return httpRequest;


    }
//    存储response对象
    public static HttpResponse responseTest() throws Exception {
        final String responseFilename = "src/main/resources/response.txt";
        final String responseStream = Files.readString(Path.of(responseFilename));

        final int responseStartLineIndex = responseStream.indexOf("\r\n");
        final String responseStartLine = responseStream.substring(0, responseStartLineIndex);

        final int responseBodyIndex = responseStream.indexOf("\r\n\r\n");
        final String responseBody = responseStream.substring(responseBodyIndex + 4);

        final int responseHeadersBeginIndex = responseStartLineIndex + 2;
        final int responseHeadersEndIndex = responseBodyIndex;
        final String responseHeaders = responseStream.substring(responseHeadersBeginIndex, responseHeadersEndIndex);

//        response内容
//        System.out.println(responseStartLine);
//        System.out.println(responseHeaders);
//        System.out.println(responseBody);
        HttpResponse httpResponse = new HttpResponse();

        responseStartLineHandle(responseStartLine, 0, httpResponse);
        responseHeaderHandle(responseHeaders, httpResponse);

//        System.out.println(httpResponse.getHttpVersion());
//        System.out.println(httpResponse.getStatusCode());
//        System.out.println(httpResponse.getMessage());
//
//        System.out.println(httpResponse.getHeaders());

        return httpResponse;
    }

    public static void requestStartLineHandle(String startLine, int num, HttpRequest httpRequest) throws Exception {
        int startLineBegin = 0;
        int startLineEnd = startLine.indexOf(" ");

        String value;
        if (startLineEnd == -1) {
            value = startLine.substring(startLineBegin);
            httpRequest.setHttpVersion(value);
        } else {
            value = startLine.substring(startLineBegin, startLineEnd);
            String nextHederContent = startLine.substring(startLineEnd + 1);

            if (num == 0) {
                httpRequest.setMethod(value);
            } else {
                int uriEnd = startLine.indexOf("?");
                if (uriEnd == -1){
                    String uriValue = startLine.substring(startLineBegin,startLineEnd);
                    httpRequest.setUri(uriValue);

                }else {
                    String uriValue = startLine.substring(startLineBegin, uriEnd);
                    httpRequest.setUri(uriValue);

                    String parameter = startLine.substring(uriEnd + 1, startLineEnd);

                    parameterHandle(parameter, 0, httpRequest);
                }
            }

            num++;
            requestStartLineHandle(nextHederContent, num, httpRequest);
        }
    }

    public static void parameterHandle(String parameter, int num, HttpRequest httpRequest) {
        int keyBegin = 0;
        int keyEnd = parameter.indexOf("=");
        String key = parameter.substring(keyBegin, keyEnd);



        int parameterEnd = parameter.indexOf("&");

        String value;
        if (parameterEnd == -1) {
            value = parameter.substring(keyEnd + 1);
            httpRequest.addParameter(key,value);

        } else {
            value = parameter.substring(keyEnd + 1, parameterEnd);

            httpRequest.addParameter(key,value);
            String nextParameter = parameter.substring(parameterEnd + 1);


            num++;
            parameterHandle(nextParameter, num, httpRequest);
        }
    }

    public static void requestHeaderHandle(String headers, HttpRequest httpRequest) {
        int headerBegin = 0;
        int headerEnd = headers.indexOf("\r\n");

        if (headerEnd == -1) {
            int keyIndex = headers.indexOf(":");
            String key = headers.substring(0, keyIndex);
            String value = headers.substring(keyIndex + 2);
            httpRequest.addHeader(key, value);

        } else {
            String hederContent = headers.substring(headerBegin, headerEnd);
            int keyIndex = hederContent.indexOf(": ");
            String key = hederContent.substring(0, keyIndex);
            String value = hederContent.substring(keyIndex + 2);
            httpRequest.addHeader(key, value);

            String nextHederContent = headers.substring(headerEnd + 2);
//            System.out.println(nextHederContent);
            requestHeaderHandle(nextHederContent, httpRequest);
        }
    }

    public static void responseStartLineHandle(String startLine, int num, HttpResponse httpResponse) {
        int startLineBegin = 0;
        int startLineEnd = startLine.indexOf(" ");

        String value;
        if (startLineEnd == -1) {
            value = startLine.substring(startLineBegin);
            httpResponse.setMessage(value);
        } else {
            value = startLine.substring(startLineBegin, startLineEnd);
            String nextHederContent = startLine.substring(startLineEnd + 1);

            if (num == 0) {
                httpResponse.setHttpVersion(value);
            } else {

                httpResponse.setStatusCode(value);
            }
            num++;
            responseStartLineHandle(nextHederContent, num, httpResponse);
        }
    }

    public static void responseHeaderHandle(String headers, HttpResponse httpResponse) {
        int headerBegin = 0;
        int headerEnd = headers.indexOf("\r\n");

        if (headerEnd == -1) {
            int keyIndex = headers.indexOf(":");
            String key = headers.substring(0, keyIndex);
            String value = headers.substring(keyIndex + 2);
            httpResponse.addHeader(key, value);

        } else {
            String hederContent = headers.substring(headerBegin, headerEnd);
            int keyIndex = hederContent.indexOf(": ");
            String key = hederContent.substring(0, keyIndex);
            String value = hederContent.substring(keyIndex + 2);
            httpResponse.addHeader(key, value);

            String nextHederContent = headers.substring(headerEnd + 2);
            responseHeaderHandle(nextHederContent, httpResponse);
        }


    }

}
