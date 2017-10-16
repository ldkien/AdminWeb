package dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import pojo.*;

import java.util.*;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import utils.MySessionFactory;

public class BillDetailDAO {

	public static List<BillDetail> GetBillDetail(String billId){
		List<BillDetail> list = new ArrayList();
		Bill bill = BillDAO.getBill(billId);
		try {
			Session session = MySessionFactory.getMySession();
			CriteriaBuilder builder = session.getCriteriaBuilder();				
			CriteriaQuery<BillDetail> criteria = builder.createQuery(BillDetail.class);
			Root<BillDetail> from = criteria.from(BillDetail.class);	
			Predicate condition = builder.equal(from.get("bill"),bill);
			criteria.where(condition);
			list=session.createQuery(criteria).getResultList();
		} catch (Exception e) {
			return null;
		}
		return list;
	}
	
	public static int getProductQuantity(int id){
		int i=0;
		List<Object> productID = new ArrayList<>();
		try {
			Session session = MySessionFactory.getMySession();
			productID = session.createNativeQuery("select sum(quantity) from bill_detail where product_id="+id).getResultList();
			for(Object p : productID){
				i = (Integer) p;
				break;
			}
		} catch (Exception e) {
			return 0;
		}
		return i;
	}
	public static void main(String args[]){
		//for(BillDetail p : BillDetailDAO.GetBillDetail("124")){
		//	System.out.println(p.getBill().getBillId());
		//}
		System.out.println(BillDetailDAO.getProductQuantity(6));
	}
}
