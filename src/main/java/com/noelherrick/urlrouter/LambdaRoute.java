package com.noelherrick.urlrouter;

import com.noelherrick.MicraRequest;

/**
 * Created by Noel on 3/20/2014.
 */
public class LambdaRoute<T> implements Route<T> {

    private T handler;
    private RouteCondition condition;

    public LambdaRoute (RouteCondition condition, T handler)
    {
        this.condition = condition;
        this.handler = handler;
    }

    @Override
    public Match<T> match(MicraRequest request) {

        if (condition.checkCondition(request))
        {
            Match<T> match = new Match<>();
            match.handler = handler;
            return match;
        }
        else
        {
            return null;
        }
    }
}
