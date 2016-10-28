package ds.assignment.view;

import java.text.SimpleDateFormat;
import java.util.List;

import ds.assignment.model.City;
import ds.assignment.model.Flight;

public class FlightRenderer {
	private static final String URL = "http://localhost:8585/Airport";

	private static final String HTML_HEADER = "<!DOCTYPE html>" + "<html>" + "<head>" + "<title>Airport</title>"
			+ "<link rel='stylesheet' href='" + URL + "/bootstrap/css/bootstrap.min.css'>" 
			+ "<link rel='stylesheet' href='" + URL + "/css/custom.css'>"
			+ "</head>" + "<body>";

	private static final String HTML_END = "<script src='" + URL
			+ "/bootstrap/js/bootstrap.min.js' type='text/javascript'></script>" + "</body>" + "</html>";

	private static final String NAV_BAR_ADMIN = "<nav class='navbar navbar-dark bg-inverse navbar-full'>"
			+ "<div class='container'>" + "<button class='navbar-toggler hidden-sm-up' type='button'"
			+ "data-toggle='collapse' data-target='#menu' aria-controls='menu'"
			+ "aria-expanded='false' aria-label='Toggle navigation'>&#9776;</button>"
			+ "<div class='collapse navbar-toggleable-xs' id='menu'>"
			+ "<ul id='menu-list' class='nav navbar-nav' style='padding-top: 8px'>"
			+ "<li class='nav-item active'><a class='nav-link' href='" + URL + "/admin/flight'>Home"
			+ "<span class='sr-only'>(current)</span>" + "</a></li>" + "<li class='nav-item'><a class='nav-link' href='"
			+ URL + "/admin/flight/add'>Add flight</a></li>"
			+ "<li class='nav-item'><a class='nav-link' href='" + URL + "/logout'>Log out</a></li>"
			+ "</ul></div></div></nav>";

	private static final String NAV_BAR_USER = "<nav class='navbar navbar-dark bg-inverse navbar-full'>"
			+ "<div class='container'>" + "<button class='navbar-toggler hidden-sm-up' type='button'"
			+ "data-toggle='collapse' data-target='#menu' aria-controls='menu'"
			+ "aria-expanded='false' aria-label='Toggle navigation'>&#9776;</button>"
			+ "<div class='collapse navbar-toggleable-xs' id='menu'>"
			+ "<ul id='menu-list' class='nav navbar-nav' style='padding-top: 8px'>"
			+ "<li class='nav-item active'><a class='nav-link' href='" + URL + "/user/flight'>Home"
			+ "<span class='sr-only'>(current)</span>" + "</a></li>" 
			+ "<li class='nav-item'><a class='nav-link' href='" + URL + "/logout'>Log out</a></li>"
			+ "</ul></div></div></nav>";

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

	public static String renderAddFlight(List<City> cities) {
		StringBuilder out = new StringBuilder();

		out.append(HTML_HEADER);
		out.append(NAV_BAR_ADMIN);

		String form = "<div class='container flight-form'>" + "<form action='" + URL + "/admin/flight/add' method='post'>" + "<div class='form-group'>"
				+ "<label for='flight_number'>Flight number: </label>" + "<input type='text' name='flight_number' required/>"
				+ "</div>" + "<div class='form-group'>" + "<label for='airplane_type'>Airplane type: </label>"
				+ "<input type='text' name='airplane_type' required/>" + "</div>" + "<div class='form-group'>"
				+ "<label for='departure_city'>Departure city: </label>"
				+ renderSelectCity(cities, null, "departure_city") + "</div>" + "<div class='form-group'>"
				+ "<label for='departure_time'>Departure time: </label>"
				+ "<input type='datetime-local' name='departure_time' required/>" + "</div>" + "<div class='form-group'>"
				+ "<label for='arrival_city'>Arrival city: </label>" + renderSelectCity(cities, null, "arrival_city")
				+ "</div>" + "<div class='form-group'>" + "<label for='arrival_time'>Arrival time: </label>"
				+ "<input type='datetime-local' name='arrival_time' required/>" + "</div>"
				+ "<button class='btn bnt-info'>Save changes</button>" + "</form></div>";

		out.append(form);
		out.append(HTML_END);

		return out.toString();
	}

	public static String renderUpdateFlight(Flight flight, List<City> cities) {
		StringBuilder out = new StringBuilder();

		out.append(HTML_HEADER);
		out.append(NAV_BAR_ADMIN);

		String form = "<div class='container flight-form'>" + "<form action='" + URL + "/admin/flight/update' method='post'>"
				+ "<input type='hidden' name='type_request' value='update'/>" + "<input type='hidden' name='id' value='"
				+ flight.getId() + "'/>" + "<div class='form-group'>"
				+ "<label for='flight_number'>Flight number: </label>"
				+ "<input type='text' name='flight_number' value='" + flight.getFlightNumber() + "' required/>" + "</div>"
				+ "<div class='form-group'>" + "<label for='airplane_type'>Airplane type: </label>"
				+ "<input type='text' name='airplane_type' value='" + flight.getAirplaneType() + "' required/>" + "</div>"
				+ "<div class='form-group'>" + "<label for='departure_city'>Departure city: </label>"
				+ renderSelectCity(cities, flight.getDepartureCity(), "departure_city") + "</div>"
				+ "<div class='form-group'>" + "<label for='departure_time'>Departure time: </label>"
				+ "<input type='datetime-local' name='departure_time' value='"
				+ format.format(flight.getDepartureTime()) + "' required/>" + "</div>" + "<div class='form-group'>"
				+ "<label for='arrival_city'>Arrival city: </label>"
				+ renderSelectCity(cities, flight.getArrivalCity(), "arrival_city") + "</div>"
				+ "<div class='form-group'>" + "<label for='arrival_time'>Arrival time: </label>"
				+ "<input type='datetime-local' name='arrival_time' value='" + format.format(flight.getArrivalTime())
				+ "' required/>" + "</div>" + "<button class='btn bnt-info'>Save changes</button>" + "</form></div>";

		out.append(form);
		out.append(HTML_END);

		return out.toString();
	}

	public static String renderResponse(List<Flight> flights, boolean isAdmin, String content) {
		StringBuilder out = new StringBuilder(renderTable(flights, isAdmin));
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

	public static String renderTable(List<Flight> flights, boolean isAdmin) {
		StringBuilder out = new StringBuilder();

		out.append(HTML_HEADER);
		if (isAdmin) {
			out.append(NAV_BAR_ADMIN);
		} else {
			out.append(NAV_BAR_USER);
		}
		if (flights != null && !flights.isEmpty()) {
			out.append("<div class='container'>");
			out.append("<table class='table'>");
			out.append("<thead>" + "<tr>" + "<th>id</th>" + "<th>Flight number</th>" + "<th>Airplane type</th>"
					+ "<th>Departure city</th>" + "<th>Departure time</th>" + "<th>Arrival city</th>"
					+ "<th>Arrival time</th>");

			if (isAdmin) {
				out.append("<th>Update</th>" + "<th>Delete</th>");
			}

			out.append("</tr>" + "</thead>" + "<tbody>");

			for (Flight f : flights) {
				out.append("<tr>" + "<td>" + f.getId() + "</td>" + "<td>" + f.getFlightNumber() + "</td>" + "<td>"
						+ f.getAirplaneType() + "</td>" + "<td><a href='" + URL + "/city?latitude="
						+ f.getDepartureCity().getLatitude() + "&longitude=" + f.getDepartureCity().getLongitude()
						+ "' data-toggle='modal' data-target='#localtime-modal'>" + f.getDepartureCity().getName() + "</a></td>" + "<td>" + f.getDepartureTime() + "</td>"
						+ "<td><a href='" + URL + "/city?latitude=" + f.getArrivalCity().getLatitude() + "&longitude="
						+ f.getArrivalCity().getLongitude() + "' data-toggle='modal' data-target='#localtime-modal'>" + f.getArrivalCity().getName() + "</a></td>" + "<td>"
						+ f.getArrivalTime() + "</td>");

				if (isAdmin) {
					out.append("<td><a class='btn btn-success' href='" + URL + "/admin/flight/update?id=" + f.getId()
							+ "' method='get'>Update</a></td>" + "<td><a class='btn btn-danger' href='" + URL
							+ "/admin/flight/delete?id=" + f.getId() + "'>Delete</a></td>");
				}
				out.append("</tr>");
			}

			out.append("</tbody>" + "</table>");
		} else {
			out.append("<p>There are no flights available.</p>");
		}

		out.append("</div>");
		out.append(HTML_END);
		return out.toString();
	}

}
