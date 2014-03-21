package com.noelherrick.urlrouter;

import com.noelherrick.MicraRequest;

public class RegexRoute<T> implements Route<T> {

    private T handler;
    private String regex;

    public RegexRoute (Regex regex, T handler)
    {
        this.regex = regex.getRegex();
        this.handler = handler;
    }

    @Override
    public Match<T> match(MicraRequest req) {
        if (req.getUrl().matches(regex))
        {
            Match<T> match = new Match<>();
            match.handler = handler;

            return match;
        }

        return null;
    }
}
