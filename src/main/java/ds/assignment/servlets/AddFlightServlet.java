package ds.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.CityDao;
import ds.assignment.dao.FlightDao;
import ds.assignment.model.City;
import ds.assignment.model.Flight;
import ds.assignment.view.FlightRenderer;

@WebServlet("/admin/flight/add")
public class AddFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1020775228663825090L;

	private static final String URL = "http://localhost:8585/Airport";

	private FlightDao flightDao;

	private CityDao cityDao;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

	public AddFlightServlet() {
		flightDao = new FlightDao(new Configuration().configure().buildSessionFactory());
		cityDao = new CityDao(new Configuration().configure().buildSessionFactory());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<City> cities = cityDao.findAll();

		PrintWriter out = response.getWriter();
		out.println(FlightRenderer.renderAddFlight(cities));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long flightNumber = Long.valueOf(request.getParameter("flight_number"));
		String airplaneType = request.getParameter("airplane_type");

		City departureCity = cityDao.findByName(request.getParameter("departure_city"));
		Date departureTime = null;

		try {
			departureTime = format.parse(request.getParameter("departure_time"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		City arrivalCity = cityDao.findByName(request.getParameter("arrival_city"));

		Date arrivalTime = null;

		try {
			arrivalTime = format.parse(request.getParameter("arrival_time"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Flight flight = new Flight();
		flight.setFlightNumber(flightNumber);
		flight.setAirplaneType(airplaneType);
		flight.setDepartureCity(departureCity);
		flight.setDepartureTime(departureTime);
		flight.setArrivalCity(arrivalCity);
		flight.setArrivalTime(arrivalTime);

		flightDao.save(flight);

		response.sendRedirect(URL + "/admin/flight");
	}
}
