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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -5559983905373073184L;
	
	private UserDao userDao;
	
	public LoginServlet(){
		userDao = new UserDao(new Configuration().configure().buildSessionFactory());
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServerException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("username = " + username + ", password = " + password);
		User loggedUser = userDao.findByUsernameAndPassword(username, password);
		
		PrintWriter out = response.getWriter();
		
		if(loggedUser != null){
			Role role = loggedUser.getRole();
			
			out.println("<p>" + role.name() + "</p>");
		}
		else{
			out.println("<p>Username or password are invalid</p>");
		}
	}
}