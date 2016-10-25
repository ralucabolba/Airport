package ds.assignment.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ds.assignment.model.City;

public class CityDao {
	private SessionFactory sessionFactory;
	
	public CityDao(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public City findById(Long id){
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		List<City> cities = null;
		
		try{
			transaction=session.beginTransaction();
			Query query = session.createQuery("FROM City WHERE id = :id");
			query.setParameter("id", id);
			
			cities = query.list();
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		
		return cities != null && !cities.isEmpty() ? cities.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public City findByName(String name){
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		List<City> cities = null;
		
		try{
			transaction=session.beginTransaction();
			Query query = session.createQuery("FROM City WHERE name = :name");
			query.setParameter("name", name);
			
			cities = query.list();
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		
		return cities != null && !cities.isEmpty() ? cities.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<City> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		List<City> cities = null;

		try {
			transaction = session.beginTransaction();
			cities = session.createQuery("FROM City").list();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}

		return cities;
	}
}
