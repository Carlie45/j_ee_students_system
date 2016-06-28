package j_ee_project.j_ee_students_system.services.filters;

import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lightning
 */
@WebFilter("/services/*")
public class AuthenticationRequestFilter implements Filter {
    
    @EJB
    SystemSecurityManager systemSecurityManager;
    @Inject
    UserSessionData userSessionData;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
            boolean authenticationRequired = (!uri.equals("/services/users/login") && !uri.equals("/services/users/is_authenticated"));
            if(authenticationRequired && !systemSecurityManager.isAuthenticated(userSessionData)){
                httpResponse.sendError(401);
                return;
            }                 
            chain.doFilter(request, response);
        } else {
            
        }
    }

    @Override
    public void destroy() {

    }

}
