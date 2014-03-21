package com.noelherrick.urlrouter;

import com.noelherrick.MicraRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noel on 3/19/14.
 */
public class StringRoute<T> implements Route<T> {
    private T handler;
    private List<Matcher> matcherList = new ArrayList<Matcher>();
    private RouteCondition condition;

    public StringRoute (String pattern, T handler)
    {
        this(pattern, handler, (req) -> true);
    }

    public StringRoute (String pattern, T handler, RouteCondition condition)
    {
        this.handler = handler;
        this.condition = condition;

        pattern = trimSlashes(pattern);

        String[] routeParts = pattern.split("/");

        List<Matcher> matcherList = new ArrayList<Matcher>();

        for (String part : routeParts)
        {
            Matcher matcher = new Matcher();

            if (part.startsWith("{") && part.endsWith("}"))
            {
                String usefulPart = part.substring(1,part.length()-1);

                if (usefulPart.contains(":"))
                {
                    String[] subparts = usefulPart.split(":");

                    matcher.parameterName = subparts[0];
                    matcher.regex = subparts[1];
                }
                else
                {
                    matcher.parameterName = usefulPart;
                    matcher.regex = ".*";
                }
            }
            else
            {
                matcher.regex = part;
            }

            matcherList.add(matcher);
        }

        this.matcherList = matcherList;
    }

    public Match<T> match (MicraRequest req)
    {
        String url = trimSlashes(req.getUrl());

        String[] urlParts = url.split("/");

        if (matcherList.size() == urlParts.length)
        {
            Match<T> match = new Match<>();
            match.handler = handler;

            boolean matchFound = true;

            for (int i = 0; i < urlParts.length; i++)
            {
                Matcher matcher = matcherList.get(i);

                if (urlParts[i].matches(matcher.regex))
                {
                    if (matcher.isParameter())
                    {
                        match.parameters.put(matcher.parameterName, urlParts[i]);
                    }
                }
                else
                {
                    matchFound = false;
                    break;
                }
            }

            if (matchFound && condition.checkCondition(req))
            {
                return match;
            }
        }

        return null;
    }

    private String trimSlashes (String str)
    {
        if (str.startsWith("/"))
        {
            str = str.substring(1);
        }

        if (str.endsWith("/"))
        {
            str = str.substring(0, str.length()-1);
        }

        return str;
    }
}
