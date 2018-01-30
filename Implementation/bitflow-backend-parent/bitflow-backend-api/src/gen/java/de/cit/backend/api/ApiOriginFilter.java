package de.cit.backend.api;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class ApiOriginFilter implements javax.servlet.Filter {
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        //res.addHeader("Access-Control-Allow-Headers", "*");
        
        echoHeader("Access-Control-Request-Headers", "Access-Control-Allow-Headers",
        		request, response);
        
        chain.doFilter(request, response);
    }
    
    private void echoHeader(String reqHeader, String respHeader, ServletRequest request, ServletResponse response){
    	HttpServletRequest requ = (HttpServletRequest) request;
    	HttpServletResponse res = (HttpServletResponse) response;
    	
        Enumeration<String> headers = requ.getHeaders(reqHeader);
        
        while (headers.hasMoreElements()) {
            String param = headers.nextElement();
            res.addHeader(respHeader, param);
        }
    }

    public void destroy() {}

    public void init(FilterConfig filterConfig) throws ServletException {}
}
