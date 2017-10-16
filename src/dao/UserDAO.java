package dao;

import org.hibernate.Session;

import pojo.Product;
import utils.MySessionFactory;
import pojo.*;
public class UserDAO {

	public static Users findUserById(Integer userId) {
		Session session = MySessionFactory.getMySession();
		
		try {
			session.beginTransaction();
			Users user =(Users) session.get(Users.class,userId);
			session.getTransaction().rollback();
			return user;
		} catch (Exception e) {
			return null;
		}
	}
}
