package com.noelherrick.micra.tests;

import com.noelherrick.urlrouter.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UrlRoutingTests {

    private MicraTestRequest simpleRequest(String url)
    {
        return new MicraTestRequest(url);
    }

    @Test
    public void testBasicRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        StringRoute<String> route = new StringRoute<String>("/foo", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo"));

        assertEquals("right!", match.handler);

        match = router.route(simpleRequest("/foo/"));

        assertEquals("right!", match.handler);
    }

    @Test
    public void testBasicRouteWithTrueCondition ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        StringRoute<String> route = new StringRoute<String>("/foo", "right!", (req) -> req.getParameter("User-Agent").equals("IE"));

        router.addRoute(route);

        MicraTestRequest testRequest = simpleRequest("/foo");

        testRequest.parameters.put("User-Agent","IE");

        Match<String> match = router.route(testRequest);

        assertEquals("right!", match.handler);
    }

    @Test
    public void testBasicRouteWithFalseCondition ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        StringRoute<String> route = new StringRoute<String>("/foo", "right!", (req) -> req.getParameter("User-Agent").equals("Firefox"));

        router.addRoute(route);

        MicraTestRequest testRequest = simpleRequest("/foo");

        testRequest.parameters.put("User-Agent","IE");

        Match<String> match = router.route(testRequest);

        assertNull(match);
    }

    @Test
    public void testBasicWithTrailingSlashRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/","right!" );

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/"));

        assertEquals("right!", match.handler);

        match = router.route(simpleRequest("/foo"));

        assertEquals("right!", match.handler);
    }

    @Test
    public void testBasicRouteWithTwo ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/bar", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/bar"));

        assertEquals("right!", match.handler);
    }

    @Test
    public void testNoRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/bar", null);

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/bar1"));

        assertNull(match);
    }

    @Test
    public void testParameterRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/{bar}", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/thing"));

        assertEquals("right!", match.handler);
        assertEquals("thing", match.parameters.get("bar"));
    }

    @Test
    public void testMultipleParameterRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/{bar}/{zip}/{zap}", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/thing1/thing2/thing3"));

        assertEquals("right!", match.handler);
        assertEquals("thing1", match.parameters.get("bar"));
        assertEquals("thing2", match.parameters.get("zip"));
        assertEquals("thing3", match.parameters.get("zap"));
    }

    @Test
    public void testSandwhichedParameterRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/{bar}/bat", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/thing/bat"));

        assertEquals("right!", match.handler);
        assertEquals("thing", match.parameters.get("bar"));
    }

    @Test
    public void testParameterWithRegexRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/foo/{bar:[0-9]*}", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/foo/911"));

        assertEquals("right!", match.handler);
        assertEquals("911", match.parameters.get("bar"));
    }

    @Test
    public void testRootRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new StringRoute<String>("/", "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/"));

        assertEquals("right!", match.handler);
    }

    @Test
    public void testLambdaRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new LambdaRoute<String>((req) -> req.getUrl().equals("/"), "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/"));

        assertEquals("right!", match.handler);
    }

    @Test
    public void testLambdaNoRoute ()
    {
        UrlRouter<String> router = new UrlRouter<String>();

        Route<String> route = new LambdaRoute<String>((req) -> req.getUrl().equals("/"), "right!");

        router.addRoute(route);

        Match<String> match = router.route(simpleRequest("/cool"));

        assertNull(match);
    }

}
