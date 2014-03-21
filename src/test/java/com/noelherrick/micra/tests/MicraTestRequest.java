package com.noelherrick.micra.tests;

import com.noelherrick.MicraRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noel on 3/20/2014.
 */
public class MicraTestRequest implements MicraRequest {

    public Map<String, String> parameters = new HashMap<>();
    public Map<String, String> headers = new HashMap<>();
    public String url;

    public MicraTestRequest (String url)
    {
        this.url = url;
    }

    @Override
    public boolean isParameter(String key) {
        return false;
    }

    @Override
    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public String getRouteParameter(String key) {
        return null;
    }

    @Override
    public void addRouteParameter(String key, String value) {
    }

    @Override
    public void addRouteParameters(Map<String, String> parameters) {
    }
}
