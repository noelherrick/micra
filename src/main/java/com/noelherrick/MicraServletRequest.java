package com.noelherrick;

//import java.io.IOException;
//import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class MicraServletRequest implements MicraRequest
{
	private HttpServletRequest servletReq;
    private HttpServlet servlet;
    private Map<String, String> routeParameters = new HashMap<>();

	public MicraServletRequest(HttpServletRequest servletReq, HttpServlet servlet)
	{
		this.servletReq = servletReq;
        this.servlet = servlet;
	}

    @Override
    public boolean isParameter(String key) {
        return servletReq.getParameter(key) != null;
    }

    public String getParameter (String key)
	{
		return servletReq.getParameter(key);
	}

    public String getHeader (String key)
    {
        return servletReq.getHeader(key);
    }

    @Override
    public String getRouteParameter(String key) {
        return routeParameters.get(key);
    }

    @Override
    public void addRouteParameter(String key, String value) {
        routeParameters.put(key, value);
    }

    @Override
    public void addRouteParameters(Map<String, String> parameters) {
        routeParameters.putAll(parameters);
    }

    public String getUrl ()
    {
        String contextPath = servlet.getServletContext().getContextPath();

        return servletReq.getRequestURI().replace(contextPath, "");
    }
}