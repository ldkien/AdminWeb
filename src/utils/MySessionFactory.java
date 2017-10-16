package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MySessionFactory {
	private static SessionFactory sessionFactory;
	private static Session session;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}
	
	public static Session getMySession() {
		if (session == null) {
			session = getSessionFactory().openSession();
		}
		return session;
	}
	
	public static void main(String[] args) {
		System.out.println(MySessionFactory.getMySession());
	}
}
