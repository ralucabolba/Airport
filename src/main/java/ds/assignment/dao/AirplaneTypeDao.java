package ds.assignment.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ds.assignment.model.AirplaneType;

public class AirplaneTypeDao {
	private SessionFactory sessionFactory;

	public AirplaneTypeDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public AirplaneType save(AirplaneType airplaneType) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		Long id = null;
		try {
			tx = session.beginTransaction();
			id = (Long) session.save(airplaneType);
			airplaneType.setId(id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return airplaneType;
	}
	
	@SuppressWarnings("unchecked")
	public AirplaneType findById(String type){
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		List<AirplaneType> airplaneTypes = null;
		
		try{
			transaction=session.beginTransaction();
			Query query = session.createQuery("FROM AirplaneType WHERE type = :type");
			query.setParameter("type", type);
			
			airplaneTypes = query.list();
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		
		return airplaneTypes != null && !airplaneTypes.isEmpty() ? airplaneTypes.get(0) : null;
	}
}
