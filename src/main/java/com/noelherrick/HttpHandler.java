package com.noelherrick;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface HttpHandler {
	public String handleHttpRequest (MicraRequest req, HttpServletResponse resp) throws ServletException, IOException;
}