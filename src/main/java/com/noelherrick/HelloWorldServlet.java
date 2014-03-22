package com.noelherrick;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

/**
 * Created by Noel on 3/20/2014.
 */
public class HelloWorldServlet extends MicraServlet
{
    public HelloWorldServlet()
    {
        get("/", (req, resp) -> "Java, sans fluff.");

        get("/hello",
            (req, resp) ->
            "Hello, " + ( req.isParameter("name") ? req.getParameter("name") : "world" ) + "!"
        );

        get("/useragent",
            (req) -> req.getHeader("User-Agent").contains("Trident"),
            (req, resp) ->
                "A browser! " + req.getHeader("User-Agent"));

        get("/useragent", (req, resp) -> "A fun browser! " + req.getHeader("User-Agent") );

        get("/mustache/{name}",
                (req, resp) -> {
                    Map<String, String> values = new HashMap<>();

                    values.put("name", req.getRouteParameter("name"));

                    String template = "Hello, {{name}}, from an in-line mustache template!";

                    return Mustache(template, values);
                }
        );

        get("/mustache-file/{name}",
                (req, resp) -> {
                    Map<String, String> values = new HashMap<>();

                    values.put("name", req.getRouteParameter("name"));

                    return MustacheView("template.mustache", values);
                }
        );

        get("/helloworld/{name}",
            (req, resp) ->
            "Hello, " + ( req.getRouteParameter("name") != null ? req.getRouteParameter("name") : "world" ) + "?!?"
        );
    }
	
	public static void main(String[] args) throws Exception{
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloWorldServlet()),"/*");
        server.start();
        server.join();
    }
}
