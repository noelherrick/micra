micra
=

    get("/", (req, resp) -> "Java, sans fluff.");


Inspired by Java 8 which introduced lambdas, Micra is a proof-of-concept for a Sinatra-inspired micro Web framework. It allows you to map URLs to lambdas. The routing syntax is derived from both Sinatra and Golang's Gorilla library.

Running it
-

You'll need Gradle to build this project. To run this example project, just type:

    $ gradle jettyRun
    
To stop the server, press CTRL+C

Making your own
-

You can start your own project by removing HelloWorldServlet.java and creating your own class that inherits from MicraServlet:

    public class YourServlet extends MicraServlet
    
Then, update the WEB-INF/web.xml file with the name of your class.

You can then use the helper functions to route requests to your new app:

    post("/user/", (req, resp) -> {/* Do some database work here.. */ "Got it!" });
    
There are four helper functions: get, post, put, delete.

Routes
-

There are four types of routes: a Gorilla-inspired route (called a parameterized route), a plain Regex route, and a lambda route.

A parameterized route can be:

1. "/"
2. "/foo"
3. "/foo/bar"
4. "/foo/{bar}" where bar will match everything
5. "/foo/{bar:[0-9]{0,2}} where bar will match all two-digit numbers

Any parameters (those parts of the routes in {}) can be used by the handler lambda:

    get("/helloworld/{name}",
        (req, resp) -> {
            String name = "world";
            if (req.getRouteParameter("name") != null)
                name = req.getRouteParameter("name")
                
        }
        "Hello, " + name + "!"
    );

A regex route is exactly how it sounds: a regex that checks agains the URL.

A lambda route takes a MicraRequest object (currently a wrapper around HTTPServletRequest) and returns a boolean:

    put((req) -> req.getUrl().equals("/"), ...);
    
Route Condition
-

You can specify a route condition if you want to use other information besides the URL to route:

    put("/foo",
        (req) -> req.getParameter("User-Agent").contains("Trident"),
        (req, resp) -> "Get chrome!"
    );
    
Gradle Goodness
-

I've included a plugin that watches the src/ folder and it will recompiles the webapp if there's a change. To use it, open two command windows, and run this in the first:

    gradle watch

You need to start that first to prevent it from being locked by the other process. Run this command in the other command window:

    gradle jettyRun
    
Jetty is set to reload when it see's that you've recompiled. On my machine, this is fast enough that the change is reflected as soon as I tab over to refresh.
