package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

import pojo.Category;
import pojo.Product;
import utils.MySessionFactory;

public class CategoryDAO {
	public static List<Category> getAll(){
		List<Category> listCategory = new ArrayList<>();
		try {
			Session session = MySessionFactory.getMySession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
			criteria.from(Category.class);
			listCategory = session.createQuery(criteria).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listCategory;
	}
	
	public static boolean deleteCategory(Category category){
		Session session = MySessionFactory.getMySession();
		try {
			session.beginTransaction();
			session.delete(category);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public static boolean insertCategory(Category category){
		Session session = MySessionFactory.getMySession();
		category.setCategoryName(category.getCategoryName().toUpperCase());
		for(Category c : getAll()){
			if(c.getCategoryName().toUpperCase().equals(category.getCategoryName()))
				return false;
		}
		try {
			session.beginTransaction();
			session.saveOrUpdate(category);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public static int getLastProduct() {
		Session session = MySessionFactory.getMySession();
		session.beginTransaction();
		String hql= "SELECT C.categoryId FROM Category C";
		List<Integer> results= session.createQuery(hql).getResultList();
		int k = results.get(results.size()-1)+1;
		for(int i=0;i<results.size()-1;i++)
		{
			if(results.get(i)!=results.get(i+1)-1)
			{
				k=results.get(i)+1;
				break;
			}			
		}
		session.getTransaction().rollback();
		return k;
	}
	
	public static boolean updateCategory(String categoryId, String name){
		Category category;
		Session session = MySessionFactory.getMySession();
		try {
			session.beginTransaction();
			category= (Category)session.get(Category.class, Integer.parseInt(categoryId));
			category.setCategoryName(name);
			session.update(category);
			session.getTransaction().commit();
			//session.getTransaction().rollback();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public static Category findCategoryById(Integer categorytId) {
		Session session = MySessionFactory.getMySession();
		
		try {
			session.beginTransaction();
			Category category =(Category) session.get(Category.class, categorytId);
			session.getTransaction().rollback();
			return category;
		} catch (Exception e) {
			return null;
		}
	}
}
