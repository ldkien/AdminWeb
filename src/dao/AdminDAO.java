package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Session;

import pojo.Admin;
import utils.MySessionFactory;
public class AdminDAO {

	public boolean Login(Admin admin){
		Session session = MySessionFactory.getMySession();
		session.beginTransaction();
		String hql= "FROM Admin a WHERE a.adminName = :name and a.adminPassword= :pass";
		List<Admin> results= session.createQuery(hql).setParameter("name", admin.getAdminName()).setParameter("pass", admin.getAdminPassword()).getResultList();
		session.getTransaction().rollback();
		if(results.isEmpty())
			return false;
		else 
			return true;
	}
	public AdminDAO() {
	}
	
	public static void main(String[] args){
		AdminDAO dao = new AdminDAO();
		Admin admin = new Admin("kien","123");
		System.out.println(dao.Login(admin));
	}
}
