package ds.assignment.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ds.assignment.model.User;

public class UserDao{

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	private SessionFactory sessionFactory;

	public UserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User save(User user) {
		Long userId = Long.valueOf(-1);
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			userId = (Long) session.save(user);
			user.setId(userId);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}

			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return user;
	}

	public User update(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}

		return user;
	}

	public void delete(User user) {

	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		List<User> users = null;

		try {
			transaction = session.beginTransaction();
			users = session.createQuery("FROM User").list();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		
		return users;
	}

	@SuppressWarnings("unchecked")
	public User findByUsernameAndPassword(String username, String password) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		List<User> users = null;
		
		try{
			transaction=session.beginTransaction();
			Query query = session.createQuery("FROM User WHERE username = :username AND password = :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			
			users = query.list();
			System.out.println(users.toString());
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		
		return users != null && !users.isEmpty() ? users.get(0) : null;
	}

}
