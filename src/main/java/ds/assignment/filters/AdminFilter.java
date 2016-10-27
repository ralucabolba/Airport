package ds.assignment.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ds.assignment.model.Role;
import ds.assignment.model.User;

@WebFilter("/admin/*")
public class AdminFilter implements Filter{

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		User loggedUser = (User) session.getAttribute("user");
		
		if(loggedUser == null || !loggedUser.getRole().equals(Role.ADMIN)){
			res.sendRedirect("login");
		}
		else{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {

	}

}