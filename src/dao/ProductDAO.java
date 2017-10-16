package dao;

import pojo.*;
import utils.MySessionFactory;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

public class ProductDAO {
	
	public static List<Product> getAll(){
		List<Product> listProduct = new ArrayList<>();
		try {
			Session session = MySessionFactory.getMySession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
			criteria.from(Product.class);
			listProduct = session.createQuery(criteria).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listProduct;
	}
	
	public static List<Product> getTop(){
		List<Product> listProduct = new ArrayList<>();
		try {
			Session session = MySessionFactory.getMySession();
			
			//session.createSQLQuery("Select * From bill_detail");
			listProduct = session.createNativeQuery("Select product.product_id,category_id,product_name,product_image,"
					+ "product_importPrice,product_price,product_description "
					+ "From product,bill_detail where product.product_id=bill_detail.product_id "
					+ "GROUP BY product.product_id,category_id,product_name,product_image,product_importPrice,product_price,product_description "
					+ "having sum(quantity) in(select  top 2 sum(quantity) from bill_detail group by product_id order by SUM(quantity) DESC) ",Product.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listProduct;
	}
	
	public static Product findProductById(Integer productId) {
		Session session = MySessionFactory.getMySession();
		
		try {
			session.beginTransaction();
			Product product =(Product) session.get(Product.class, productId);
			session.getTransaction().rollback();
			return product;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int getLastProduct() {
		Session session = MySessionFactory.getMySession();
		session.beginTransaction();
		String hql= "SELECT P.productId FROM Product P";
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
	
	public static boolean deleteProduct(Product product){
		Session session = MySessionFactory.getMySession();
		try {
			//CriteriaBuilder builder = session.getCriteriaBuilder();
			//CriteriaDelete<Product> delete = builder.createCriteriaDelete(Product.class);
			//Root<Product> root = delete.from(Product.class);
			//delete.where(builder.equal(product);
			//session.beginTransaction();
			session.beginTransaction();
			session.delete(product);
			session.getTransaction().commit();
			//session.getTransaction().rollback();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public static boolean updateProduct(String productId, String name, String image, BigDecimal price, BigDecimal importPrice, String description, String category){
		Product product;
		Session session = MySessionFactory.getMySession();
		try {
			session.beginTransaction();
			product = (Product)session.get(Product.class, Integer.parseInt(productId));
			product.setProductName(name);
			product.setProductImage(image);
			product.setProductImportPrice(importPrice);
			product.setProductPrice(price);
			product.setProductDescription(description);
			product.setCategory(session.get(Category.class, Integer.parseInt(category)));
			session.update(product);
			session.getTransaction().commit();
			//session.getTransaction().rollback();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public static boolean insertProduct(Product product){
		Session session = MySessionFactory.getMySession();
		BigDecimal d = new BigDecimal(8);
		try {
			session.beginTransaction();
			session.saveOrUpdate(product);
			session.getTransaction().commit();
			//session.getTransaction().rollback();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
	
	public static void main(String[] args) {
		ProductDAO dao = new ProductDAO();
		
		List<Product> list = dao.getTop();
		for(Product p : list){
			System.out.println(p.getProductId());
		}
		//System.out.println(ProductDAO.getLastProduct());
		//System.out.println(ProductDAO.findProductById(20).getProductId());
		
		}
			
}
