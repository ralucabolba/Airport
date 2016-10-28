package ds.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.FlightDao;
import ds.assignment.dao.UserDao;
import ds.assignment.model.Flight;
import ds.assignment.model.Role;
import ds.assignment.model.User;
import ds.assignment.utils.CookieUtils;
import ds.assignment.view.FlightRenderer;

@WebServlet(urlPatterns = {"/user/flight", "/admin/flight"})
public class FlightServlet extends HttpServlet {

	private static final long serialVersionUID = -8187197597362348306L;

	private FlightDao flightDao;
	
	private UserDao userDao;

	public FlightServlet() {
		flightDao = new FlightDao(new Configuration().configure().buildSessionFactory());
		userDao = new UserDao(new Configuration().configure().buildSessionFactory());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");

		/*HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");*/
		Cookie loginCookie = CookieUtils.findCookie(request.getCookies(), "userId");
		Long userId = Long.valueOf(loginCookie.getValue());

		User loggedUser = userDao.findById(userId);
		boolean isAdmin = loggedUser.getRole().equals(Role.ADMIN);
		
		PrintWriter out = response.getWriter();

		List<Flight> flights = flightDao.findAll();
		
		out.println(FlightRenderer.renderTable(flights, isAdmin));
	}
}
