package ds.assignment.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.UserDao;
import ds.assignment.model.Role;
import ds.assignment.model.User;
import ds.assignment.utils.CookieUtils;

@WebFilter("/user/*")
public class UserFilter implements Filter {

	private static final String URL = "http://localhost:8585/Airport";

	private UserDao userDao;

	public UserFilter() {
		userDao = new UserDao(new Configuration().configure().buildSessionFactory());
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		/*
		 * HttpSession session = req.getSession();
		 * 
		 * User loggedUser = (User) session.getAttribute("user");
		 */

		Cookie loginCookie = CookieUtils.findCookie(req.getCookies(), "userId");

		if (loginCookie != null) {
			Long userId = Long.valueOf(loginCookie.getValue());

			User loggedUser = userDao.findById(userId);
			if (loggedUser == null || !loggedUser.getRole().equals(Role.USER)) {
				res.sendRedirect(URL + "/login");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			res.sendRedirect(URL + "/login");
		}
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {

	}

}
