package ds.assignment.view;

import java.text.SimpleDateFormat;
import java.util.List;

import ds.assignment.model.City;
import ds.assignment.model.Flight;

public class FlightRenderer {
	private static final String URL = "http://localhost:8181/Airport";

	private static final String HTML_HEADER = "<!DOCTYPE html>" + "<html>" + "<head>" + "<title>Airport</title>"
			+ "<link rel='stylesheet' href='" + URL + "/bootstrap/css/bootstrap.min.css'>" + "</head>" + "<body>";

	private static final String HTML_END = "</div>" + "<script src='" + URL
			+ "/bootstrap/js/bootstrap.min.js' type='text/javascript'></script>" + "</body>" + "</html>";

	private static final String NAV_BAR = "<nav class='navbar navbar-dark bg-inverse navbar-full'>"
			+ "<div class='container'>" + "<button class='navbar-toggler hidden-sm-up' type='button'"
			+ "data-toggle='collapse' data-target='#menu' aria-controls='menu'"
			+ "aria-expanded='false' aria-label='Toggle navigation'>&#9776;</button>"
			+ "<div class='collapse navbar-toggleable-xs' id='menu'>"
			+ "<ul id='menu-list' class='nav navbar-nav' style='padding-top: 8px'>"
			+ "<li class='nav-item active'><a class='nav-link' href='" + URL + "/flight'>Home"
			+ "<span class='sr-only'>(current)</span>" + "</a></li>" + "<li class='nav-item'><a class='nav-link' href='"
			+ URL + "/flight/add'>Add flight</a></li>" + "</ul></div></div></nav>" + "<div class='container'>";

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static String renderAddFlight(List<City> cities) {
		StringBuilder out = new StringBuilder();

		out.append(HTML_HEADER);
		out.append(NAV_BAR);

		String form = "<form action='" + URL + "/flight/add' method='post'>" + "<div class='form-group'>"
				+ "<label for='flight_number'>Flight number: </label>" + "<input type='text' name='flight_number'/>"
				+ "</div>" + "<div class='form-group'>" + "<label for='airplane_type'>Airplane type: </label>"
				+ "<input type='text' name='airplane_type'/>" + "</div>" + "<div class='form-group'>"
				+ "<label for='departure_city'>Departure city: </label>"
				+ renderSelectCity(cities, null, "departure_city") + "</div>" + "<div class='form-group'>"
				+ "<label for='departure_time'>Departure time: </label>"
				+ "<input type='datetime' name='departure_time'/>" + "</div>" + "<div class='form-group'>"
				+ "<label for='arrival_city'>Arrival city: </label>" + renderSelectCity(cities, null, "arrival_city")
				+ "</div>" + "<div class='form-group'>" + "<label for='arrival_time'>Arrival time: </label>"
				+ "<input type='datetime' name='arrival_time'/>" + "</div>"
				+ "<button class='btn bnt-info'>Save changes</button>" + "</form>";

		out.append(form);
		out.append(HTML_END);

		return out.toString();
	}

	public static String renderUpdateFlight(Flight flight, List<City> cities) {
		StringBuilder out = new StringBuilder();

		out.append(HTML_HEADER);
		out.append(NAV_BAR);

		String form = "<form action='" + URL + "/flight/update' method='post'>"
				+ "<input type='hidden' name='type_request' value='update'/>" + "<input type='hidden' name='id' value='"
				+ flight.getId() + "'/>" + "<div class='form-group'>"
				+ "<label for='flight_number'>Flight number: </label>"
				+ "<input type='text' name='flight_number' value='" + flight.getFlightNumber() + "'/>" + "</div>"
				+ "<div class='form-group'>" + "<label for='airplane_type'>Airplane type: </label>"
				+ "<input type='text' name='airplane_type' value='" + flight.getAirplaneType() + "'/>" + "</div>"
				+ "<div class='form-group'>" + "<label for='departure_city'>Departure city: </label>"
				+ renderSelectCity(cities, flight.getDepartureCity(), "departure_city") + "</div>"
				+ "<div class='form-group'>" + "<label for='departure_time'>Departure time: </label>"
				+ "<input type='datetime' name='departure_time' value='" + format.format(flight.getDepartureTime())
				+ "'/>" + "</div>" + "<div class='form-group'>" + "<label for='arrival_city'>Arrival city: </label>"
				+ renderSelectCity(cities, flight.getArrivalCity(), "arrival_city") + "</div>"
				+ "<div class='form-group'>" + "<label for='arrival_time'>Arrival time: </label>"
				+ "<input type='datetime' name='arrival_time' value='" + format.format(flight.getArrivalTime()) + "'/>"
				+ "</div>" + "<button class='btn bnt-info'>Save changes</button>" + "</form>";

		out.append(form);
		out.append(HTML_END);

		return out.toString();
	}

	public static String renderResponse(List<Flight> flights, String content) {
		StringBuilder out = new StringBuilder(renderTable(flights));
		out.append(content);

		return out.toString();
	}

	public static String renderSelectCity(List<City> cities, City c, String id) {
		StringBuilder out = new StringBuilder();
		out.append("<select class='form-control' id='" + id + "' name='" + id + "'>");

		for (City city : cities) {
			if (c != null && !c.getId().equals(city.getId())) {
				out.append("<option>" + city.getName() + "</option>");
			} else {
				out.append("<option selected='selected'>" + city.getName() + "</option>");
			}
		}

		out.append("<select/>");

		return out.toString();
	}

	public static String renderTable(List<Flight> flights) {
		StringBuilder out = new StringBuilder();

		out.append(HTML_HEADER);
		out.append(NAV_BAR);

		if (flights != null && !flights.isEmpty()) {
			out.append("<table class='table'>");
			out.append("<thead>" + "<tr>" + "<th>id</th>" + "<th>Flight number</th>" + "<th>Airplane type</th>"
					+ "<th>Departure city</th>" + "<th>Departure time</th>" + "<th>Arrival city</th>"
					+ "<th>Arrival time</th>" + "<th>Update</th>" + "<th>Delete</th>" + "</tr>" + "</thead>"
					+ "<tbody>");

			for (Flight f : flights) {
				out.append("<tr>" + "<td>" + f.getId() + "</td>" + "<td>" + f.getFlightNumber() + "</td>" + "<td>"
						+ f.getAirplaneType() + "</td>" + "<td><a href='" + URL + "/city?latitude="
						+ f.getDepartureCity().getLatitude() + "&longitude=" + f.getDepartureCity().getLongitude()
						+ "'>" + f.getDepartureCity().getName() + "</a></td>" + "<td>" + f.getDepartureTime() + "</td>"
						+ "<td><a href='" + URL + "/city?latitude=" + f.getArrivalCity().getLatitude() + "&longitude="
						+ f.getArrivalCity().getLongitude() + "'>" + f.getArrivalCity().getName() + "</a></td>" + "<td>"
						+ f.getArrivalTime() + "</td>" + "<td><a class='btn btn-success' href='" + URL
						+ "/flight/update?id=" + f.getId() + "' method='get'>Update</a></td>"
						+ "<td><a class='btn btn-danger' href='" + URL + "/flight/delete?id=" + f.getId()
						+ "'>Delete</a></td>" + "</tr>");
			}

			out.append("</tbody>" + "</table>");
		} else {
			out.append("<p>There are no flights available.</p>");
		}

		out.append(HTML_END);
		return out.toString();
	}

}
