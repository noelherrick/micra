package com.noelherrick.urlrouter;

import com.noelherrick.MicraRequest;

/**
 * Created by Noel on 3/20/2014.
 */
public interface Route<T>
{
    public Match<T> match (MicraRequest request);
}
