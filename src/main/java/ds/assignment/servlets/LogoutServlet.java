package ds.assignment.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ds.assignment.utils.CookieUtils;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 4536867634590742855L;

	private static final String URL = "http://localhost:8585/Airport";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = CookieUtils.findCookie(cookies, "userId");
		
		if (cookie != null) {
			//remove cookie
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		response.sendRedirect(URL + "/login");
	}
	
	/*private Cookie findCookie(Cookie[] cookies, String name){
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}*/
}
