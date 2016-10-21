package ds.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.UserDao;
import ds.assignment.model.Role;
import ds.assignment.model.User;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

	private UserDao userDao;

	public UserServlet() {
		userDao = new UserDao(new Configuration().configure().buildSessionFactory());
	}

	private static final long serialVersionUID = -3271477371758946544L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<h2>" + userDao.findAll() + "</h2>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
		response.setContentType("text/html");
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Role role = Role.USER;
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);

		user = userDao.save(user);

		PrintWriter out = response.getWriter();
		out.println("<p>" + user.toString() + "</p>");

	}

}
