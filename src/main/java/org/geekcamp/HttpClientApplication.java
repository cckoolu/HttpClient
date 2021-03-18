package main.java.org.geekcamp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class HttpClientApplication {

    enum startLineParameter
    {
        requestMethod,uri,httpVersion
    }


    public static void main(String[] args) throws IOException {
        final String filename = "src/main/resources/request.txt";
        // Class<HttpClientApplication> clazz = HttpClientApplication.class;
        // InputStream inputStream = clazz.getResourceAsStream("/request.txt");
        final String requestStream = Files.readString(Path.of(filename));

        final int bodyIndex = requestStream.indexOf("\r\n\r\n");
        final String body = requestStream.substring(bodyIndex + 4);

        final int startLineIndex = requestStream.indexOf("\r\n");
        final String startLine = requestStream.substring(0, startLineIndex);

        final int headersBeginIndex = startLineIndex + 2;
        final int headersEndIndex = bodyIndex;
        final String headers = requestStream.substring(headersBeginIndex, headersEndIndex);

        HashMap<startLineParameter , String> startLineMap = new HashMap<>();
        HashMap<String , String> headerMap = new HashMap<>();
        startLineHandle(startLine,0,startLineMap);
        headerHandle(headers,headerMap);

        System.out.println(startLineMap);
        System.out.println(headerMap);
        System.out.println(body);

        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setStartLine(startLineMap);
        httpMessage.setHeaders(headerMap);
        httpMessage.setBody(body);

    }

    public static void startLineHandle(String startLine,int num, HashMap<startLineParameter, String> startLineMap){

        int startLineBegin = 0;
        int startLineEnd = startLine.indexOf(" ");


        String value;
        startLineParameter key = startLineParameter.values()[num];

        if (startLineEnd == -1) {
            value = startLine.substring(startLineBegin);
            startLineMap.put(key,value);
        } else {
            value = startLine.substring(startLineBegin, startLineEnd);
            String nextHederContent = startLine.substring(startLineEnd + 1);
            startLineMap.put(key,value);

            num ++;
            startLineHandle(nextHederContent,num, startLineMap);

        }


    }

    public static void headerHandle(String headers, HashMap<String, String> headerMap){
        int headerBegin = 0;
        int headerEnd = headers.indexOf("\r\n");

        if (headerEnd == -1) {
            int keyIndex = headers.indexOf(":");
            String key = headers.substring(0, keyIndex);
            String value = headers.substring(keyIndex + 2);
            headerMap.put(key,value);

        } else {
            String hederContent = headers.substring(headerBegin, headerEnd);
            int keyIndex = hederContent.indexOf(": ");
            String key = hederContent.substring(0, keyIndex);
            String value = hederContent.substring(keyIndex + 2);
            headerMap.put(key,value);

            String nextHederContent = headers.substring(headerEnd + 2);
//            System.out.println(nextHederContent);
            headerHandle(nextHederContent, headerMap);
        }
    }
}
