package com.noelherrick;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.noelherrick.urlrouter.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public abstract class MicraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UrlRouter<HttpHandler> getRouter = new UrlRouter<HttpHandler>();
    private UrlRouter<HttpHandler> postRouter = new UrlRouter<HttpHandler>();
    private UrlRouter<HttpHandler> deleteRouter = new UrlRouter<HttpHandler>();
    private UrlRouter<HttpHandler> putRouter = new UrlRouter<HttpHandler>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(getRouter, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(postRouter, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(putRouter, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(deleteRouter, req, resp);
    }

    private void handleRequest (UrlRouter<HttpHandler> router, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        MicraRequest micraRequest = new MicraServletRequest(req, this);

        Match<HttpHandler> match = router.route(micraRequest);

        if (match == null)
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else
        {
            micraRequest.addRouteParameters(match.parameters);

            String body = match.handler.handleHttpRequest(micraRequest, resp);

            resp.getOutputStream().write(body.getBytes());
        }
    }

    // Helper methods
    protected Regex Regex(String regex)
    {
        return new Regex(regex);
    }

    protected RouteCondition AcceptJson ()
    {
        return (req) -> req.getHeader("Accept").contains("application/json");
    }

    protected String Mustache (String template, Map scopes)
    {
        StringWriter writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(template), template);
        mustache.execute(writer, scopes);
        writer.flush();

        return writer.toString();
    }

    protected String MustacheView (String templateName, Map scopes)
    {
        ServletContext context = getServletContext();
        String fullTemplatePath = context.getRealPath("/WEB-INF/"+templateName);

        StringWriter writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(fullTemplatePath);
        mustache.execute(writer, scopes);
        writer.flush();

        return writer.toString();
    }

    protected RouteCondition AcceptHtml ()
    {
        return (req) -> req.getHeader("Accept").contains("text/html");
    }

    private void addHendler (UrlRouter<HttpHandler> router, String pattern, HttpHandler handler)
    {
        Route<HttpHandler> route = new StringRoute<>(pattern, handler);

        router.addRoute(route);
    }

    private void addHendler (UrlRouter<HttpHandler> router, String pattern, RouteCondition condition, HttpHandler handler)
    {
        Route<HttpHandler> route = new StringRoute<>(pattern, handler, condition);

        router.addRoute(route);
    }

    private void addHendler (UrlRouter<HttpHandler> router, Regex regex, HttpHandler handler)
    {
        Route<HttpHandler> route = new RegexRoute<>(regex, handler);

        router.addRoute(route);
    }

    private void addHendler (UrlRouter<HttpHandler> router, RouteCondition condition, HttpHandler handler)
    {
        Route<HttpHandler> route = new LambdaRoute<>(condition, handler);

        router.addRoute(route);
    }

    // Get
    protected void get (String pattern, HttpHandler handler)
    {
        addHendler(getRouter, pattern, handler);
    }

    protected void get (String pattern, RouteCondition condition, HttpHandler handler)
    {
        addHendler(getRouter, pattern, condition, handler);
    }

    protected void get (Regex regex, HttpHandler handler)
    {
        addHendler(getRouter, regex, handler);
    }

    protected void get (RouteCondition condition, HttpHandler handler)
    {
        addHendler(getRouter, condition, handler);
    }

    // Post
    protected void post (String pattern, HttpHandler handler)
    {
        addHendler(postRouter, pattern, handler);
    }

    protected void post (String pattern, RouteCondition condition, HttpHandler handler)
    {
        addHendler(postRouter, pattern, condition, handler);
    }

    protected void post (Regex regex, HttpHandler handler)
    {
        addHendler(postRouter, regex, handler);
    }

    protected void post (RouteCondition condition, HttpHandler handler)
    {
        addHendler(postRouter, condition, handler);
    }

    // Put
    protected void put (String pattern, HttpHandler handler)
    {
        addHendler(putRouter, pattern, handler);
    }

    protected void put (String pattern, RouteCondition condition, HttpHandler handler)
    {
        addHendler(putRouter, pattern, condition, handler);
    }

    protected void put (Regex regex, HttpHandler handler)
    {
        addHendler(putRouter, regex, handler);
    }

    protected void put (RouteCondition condition, HttpHandler handler)
    {
        addHendler(putRouter, condition, handler);
    }

    // Delete
    protected void delete (String pattern, HttpHandler handler)
    {
        addHendler(deleteRouter, pattern, handler);
    }

    protected void delete (String pattern, RouteCondition condition, HttpHandler handler)
    {
        addHendler(deleteRouter, pattern, condition, handler);
    }

    protected void delete (Regex regex, HttpHandler handler)
    {
        addHendler(deleteRouter, regex, handler);
    }

    protected void delete (RouteCondition condition, HttpHandler handler)
    {
        addHendler(deleteRouter, condition, handler);
    }
}