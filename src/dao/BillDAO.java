package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


import org.hibernate.Session;

import pojo.Admin;
import pojo.Bill;
import pojo.Product;
import utils.MySessionFactory;

public class BillDAO {

	public static List<Bill> getAll(){
		List<Bill> listBill = new ArrayList();
		try {
			Session session = MySessionFactory.getMySession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Bill> criteriaQuery = builder.createQuery(Bill.class);
			criteriaQuery.from(Bill.class);
			listBill = session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			return null;
		}
		return listBill;
	}
	
	public static List<Bill> getByDate(int month){
		List<Bill> listBill = new ArrayList();
		try {
			for(Bill b : BillDAO.getAll())
			{
				if(Integer.parseInt(b.getDate().substring(b.getDate().indexOf("/")+1, b.getDate().lastIndexOf("/")))==month)
					listBill.add(b);
			}
		} catch (Exception e) {
			return null;
		}
		return listBill;
	}
	
	public static double billTotal() {
		Session session = MySessionFactory.getMySession();
		session.beginTransaction();
		String hql = "SELECT sum(B.total) FROM Bill B";
		Query query = session.createQuery(hql);
		List results = query.getResultList();
		session.getTransaction().rollback();
		return Double.parseDouble(results.get(0).toString());
		
	}
	
	public static double billTotalByDate(int month) {
		double total =0;
		for(Bill b : BillDAO.getByDate(month)){
			total+=Double.parseDouble(b.getTotal().toString());
			
		}
		return total;
		
	}
	
	public static Bill getBill(String id) {
		Session session = MySessionFactory.getMySession();
		session.beginTransaction();
		Bill bill = (Bill) session.get(Bill.class, id);
		session.getTransaction().rollback();
		return bill;
		
	}
	
	public static boolean deleteList(List<Bill> billList) {
		Session session = MySessionFactory.getMySession();
		try {
			//CriteriaBuilder builder = session.getCriteriaBuilder();
			//CriteriaDelete<Product> delete = builder.createCriteriaDelete(Product.class);
			//Root<Product> root = delete.from(Product.class);
			//delete.where(builder.equal(product);
			//session.beginTransaction();
			session.beginTransaction();
			for(Bill b:billList)
				session.delete(b);
			session.getTransaction().commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	public static void main(String[] args) {
		/*ProductDAO dao = new ProductDAO();
		List<Product> list = dao.getAll();
		for(Product p : list){
			System.out.println(p.getProductId());
		}*/
			for(Bill b : BillDAO.getAll()){
			System.out.println(Integer.parseInt(b.getDate().substring(b.getDate().indexOf("/")+1, b.getDate().lastIndexOf("/"))));
			}
		//System.out.println()
		//System.out.println(BillDAO.getBill("124").getTotal());
		}
}
