package com.noelherrick.urlrouter;

import com.noelherrick.MicraRequest;

/**
 * Created by Noel on 3/20/2014.
 */
@FunctionalInterface
public interface RouteCondition {
    public boolean checkCondition (MicraRequest request);
}