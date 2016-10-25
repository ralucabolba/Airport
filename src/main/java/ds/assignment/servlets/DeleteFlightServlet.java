package ds.assignment.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;

import ds.assignment.dao.FlightDao;
import ds.assignment.model.Flight;

@WebServlet("flight/delete")
public class DeleteFlightServlet extends HttpServlet {
	private static final long serialVersionUID = -4329773332913992336L;

	private FlightDao flightDao;

	public DeleteFlightServlet() {
		flightDao = new FlightDao(new Configuration().configure().buildSessionFactory());
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Long id = Long.valueOf(request.getParameter("id"));
		Flight flight = flightDao.findById(id);
		
		flightDao.delete(flight);
		
		response.sendRedirect("http://localhost:8585/Airport/flight");
	}
}
