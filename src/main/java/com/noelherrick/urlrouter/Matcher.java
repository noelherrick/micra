package com.noelherrick.urlrouter;

/**
 * Created by Noel on 3/19/14.
 */
public class Matcher {
    public String regex;
    public String parameterName;

    public boolean isParameter ()
    {
        return parameterName != null;
    }
}
