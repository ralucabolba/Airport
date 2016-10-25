package ds.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.FlightDao;
import ds.assignment.model.Flight;
import ds.assignment.view.FlightRenderer;

@WebServlet("/flight")
public class FlightServlet extends HttpServlet {

	private static final long serialVersionUID = -8187197597362348306L;

	private FlightDao flightDao;

	public FlightServlet() {
		flightDao = new FlightDao(new Configuration().configure().buildSessionFactory());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");

		Enumeration<String> parameters = request.getParameterNames();

		PrintWriter out = response.getWriter();

		if (!parameters.hasMoreElements()) { // find all flights
			List<Flight> flights = flightDao.findAll();
			out.println(FlightRenderer.renderTable(flights));
		} else {
			out.println(parameters.toString());
		}
	}
}
