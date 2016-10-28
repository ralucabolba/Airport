package ds.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.UserDao;
import ds.assignment.model.Role;
import ds.assignment.model.User;
import ds.assignment.view.LoginRenderer;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -5559983905373073184L;

	private UserDao userDao;

	public LoginServlet() {
		userDao = new UserDao(new Configuration().configure().buildSessionFactory());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.append(LoginRenderer.renderLogin(null));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServerException, IOException, ServletException {
		response.setContentType("text/html");

		// HttpSession session = request.getSession();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User loggedUser = userDao.findByUsernameAndPassword(username, password);

		PrintWriter out = response.getWriter();

		if (loggedUser == null) {
			String message = "<div class='container'><p class='alert alert-danger'>The username or password is incorrect. Please try again.</p></div>";
			out.println(LoginRenderer.renderLogin(message));
		} else {
			// session.setAttribute("user", loggedUser);
			Cookie cookie = new Cookie("userId", loggedUser.getId().toString());
			// set cookie to expire after 60 minutes
			cookie.setMaxAge(60 * 60);
			response.addCookie(cookie);

			if (loggedUser.getRole().equals(Role.ADMIN)) {
				response.sendRedirect("admin/flight");

			} else if (loggedUser.getRole().equals(Role.USER)) {
				response.sendRedirect("user/flight");
			}
		}
	}
}
