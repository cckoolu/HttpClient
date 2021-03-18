package main.java.org.geekcamp;

import java.util.HashMap;

public class HttpMessage {
    HashMap<HttpClientApplication.startLineParameter, String> startLine;
    HashMap<String, String> headers;
    String body;


    public HashMap<HttpClientApplication.startLineParameter, String> getStartLine() {
        return startLine;
    }

    public void setStartLine(HashMap<HttpClientApplication.startLineParameter, String> startLine) {
        this.startLine = startLine;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
