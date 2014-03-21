package com.noelherrick.urlrouter;

import com.noelherrick.MicraRequest;

import java.util.ArrayList;
import java.util.List;

public class UrlRouter<T> {

    private List<Route<T>> routes = new ArrayList<Route<T>>();

    public void addRoute (Route<T> route)
    {
        routes.add(route);
    }

    public Match<T> route(MicraRequest request)
    {
        for (Route<T> route : routes)
        {
            Match<T> match = route.match(request);

            if (match != null)
            {
                return match;
            }
        }

        return null;
    }
}
