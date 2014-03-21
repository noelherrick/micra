package com.noelherrick.urlrouter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noel on 3/19/14.
 */
public class Match<T> {
    public Map<String, String> parameters = new HashMap<String, String>();
    public T handler;
}
