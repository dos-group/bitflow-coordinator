package de.cit.backend.mgmt;


import javax.servlet.*;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

public class AuthFilter implements Filter {


    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        System.out.println("AuthFilter");
        System.out.println(request.getServletContext().getContextPath());
        System.out.println(request.getAttribute("Content-Location"));
        System.out.println("AuthFilter end");
        /*Method method = resourceInfo.getResourceMethod();
        final AuthLevel authLevel = method.getAnnotation(AuthLevel.class);
        // return if no authorization required
        if(authLevel == null) {
            return;
        }
        //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        //Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        //If no authorization information present; block access
        if(authorization == null || authorization.isEmpty())
        {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }

        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        //Verifying Username and password
        System.out.println(username);
        System.out.println(password);
        if("test".equals(username) && "test".equals(password)) {
            if(AuthLevel.Level.ADMIN.equals(authLevel.value())) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
            }
            return;
        }*/

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
