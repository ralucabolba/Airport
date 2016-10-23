package ds.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.FlightDao;
import ds.assignment.model.Flight;

@WebServlet("/flight")
public class FlightServlet extends HttpServlet {

	private static final long serialVersionUID = -8187197597362348306L;

	private static final String HTML_HEADER = "<!DOCTYPE html>" + "<html>" + "<head>" + "<title>Airport</title>"
			+ "<link rel='stylesheet' href='bootstrap/css/bootstrap.min.css'>" + "</head>" + "<body>"
			+ "<div class='container'>";

	private static final String HTML_END = "</div>"
			+ "<script src='bootstrap/js/bootstrap.min.js' type='text/javascript'></script>" + "</body>" + "</html>";

	private FlightDao flightDao;

	public FlightServlet() {
		flightDao = new FlightDao(new Configuration().configure().buildSessionFactory());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		Enumeration<String> parameters = request.getParameterNames();

		PrintWriter out = response.getWriter();

		out.println(HTML_HEADER);

		if (!parameters.hasMoreElements()) { // find all flights
			List<Flight> flights = flightDao.findAll();
			if (flights != null && !flights.isEmpty()) {
				out.println("<table class='table'>");
				out.println("<thead>"
						+ "<tr>"
						+ "<th>id</th>"
						+ "<th>Flight number</th>"
						+ "<th>Airplane type</th>"
						+ "<th>Departure city</th>"
						+ "<th>Departure time</th>"
						+ "<th>Arrival city</th>"
						+ "<th>Arrival time</th>"
						+ "</tr>"
						+ "</thead>"
						+ "<tbody>");
				
				for (Flight f : flights) {
					out.println("<tr>"
							+ "<td>" + f.getId() + "</td>"
							+ "<td>" + f.getFlightNumber() + "</td>"
							+ "<td>" + f.getAirplaneType().getType() + "</td>"
							+ "<td><a href='http://localhost:8181/Airport/city?latitude=" + 
								f.getDepartureCity().getLatitude() + "&longitude=" + 
								f.getDepartureCity().getLongitude() + "'>" + 
								f.getDepartureCity().getName() + "</a></td>"
							+ "<td>" + f.getDepartureTime() + "</td>" 
							+ "<td>" + f.getArrivalCity().getName() + "</td>"
							+ "<td>" + f.getArrivalTime() + "</td>"
							+ "</tr>");
				}
				
				out.println("</tbody>"
						+ "</table>");
			}
			else{
				out.println("<p>There are no flights available.</p>");
			}

		}
		else{
			out.println(parameters.toString());
		}
		out.println(HTML_END);
	}
}
