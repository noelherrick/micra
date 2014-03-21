package com.noelherrick;

import java.util.Map;

/**
 * Created by Noel on 3/20/2014.
 */
public interface MicraRequest
{
    public boolean isParameter (String key);
    public String getParameter (String key);
    public String getUrl ();
    public String getHeader (String key);
    public String getRouteParameter (String key);
    public void addRouteParameter (String key, String value);
    public void addRouteParameters (Map<String, String> parameters);
}
