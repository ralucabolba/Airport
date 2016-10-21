package ds.assignment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "flights")
public class Flight implements Serializable {
	private static final long serialVersionUID = -8693560531572643306L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "flight_number")
	private Long flightNumber;

	@ManyToOne
	@JoinColumn(name = "airplane_type_id")
	private AirplaneType airplaneType;

	@ManyToOne
	@JoinColumn(name = "departure_city_id")
	private City departureCity;

	@ManyToOne
	@JoinColumn(name = "arrival_city_id")
	private City arrivalCity;

	@Column(name = "departure_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;

	@Column(name = "arrival_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Long flightNumber) {
		this.flightNumber = flightNumber;
	}

	public AirplaneType getAirplaneType() {
		return airplaneType;
	}

	public void setAirplaneType(AirplaneType airplaneType) {
		this.airplaneType = airplaneType;
	}

	public City getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(City departureCity) {
		this.departureCity = departureCity;
	}

	public City getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(City arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
