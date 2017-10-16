package managedBeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.*;

import dao.BillDetailDAO;
import pojo.*;

@ManagedBean(name = "detailBean")
@SessionScoped
public class BillDetailManagedBean {
	Product product = new Product();
	Bill bill = new Bill();
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<BillDetail> ViewBill(String id) {
		try {
			 return BillDetailDAO.GetBillDetail(id);
		} catch (Exception e) {
			return null;
		}
	
	}
	
	public int getProductQuantity(int id) {
		return BillDetailDAO.getProductQuantity(id);
	}
}
