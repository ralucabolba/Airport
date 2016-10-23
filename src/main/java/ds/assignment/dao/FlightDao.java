package ds.assignment.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ds.assignment.model.Flight;

public class FlightDao {
	private SessionFactory sessionFactory;

	public FlightDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Flight> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		List<Flight> flights = null;

		try {
			transaction = session.beginTransaction();
			flights = session.createQuery("FROM Flight").list();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}

		return flights;
	}

	public Flight save(Flight flight) {
		Long flightId = Long.valueOf(-1);

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			flightId = (Long) session.save(flight);
			flight.setId(flightId);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}

			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return flight;
	}

	public Flight update(Flight flight) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(flight);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}

		return flight;
	}

	public void delete(Flight flight) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(flight);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
