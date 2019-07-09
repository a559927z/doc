package net.chinahrd.modules.rbac.filter.request;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        ServletRequest requestWrapper = null;    
        if(request instanceof HttpServletRequest) {    
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);    
        }    
        if(requestWrapper == null) {    
            chain.doFilter(request, response);    
        } else {    
            chain.doFilter(requestWrapper, response);    
        }    
    }  


    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
