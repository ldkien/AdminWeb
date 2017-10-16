package managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.util.*;
import pojo.*;
import dao.BillDAO;

@ManagedBean(name = "billBean")
@SessionScoped
public class BillManagedBean {
	private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
	public Map<Integer, Boolean> getChecked() {
		return checked;
	}
	private int month;
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setChecked(Map<Integer, Boolean> checked) {
		this.checked = checked;
	}

	public List<Bill> getAll() {
		return BillDAO.getAll();
	}
	
	public List<Bill> getByDate() {
		return BillDAO.getByDate(this.month);
	}
	
	public double totalBill() {
		return BillDAO.billTotal();
	}
	public double billTotalByDate() {
		return BillDAO.billTotalByDate(this.month);
	}
	public void delete() {
	    List<Bill> billToDelete = new ArrayList<Bill>();

	    for (Bill bill : BillDAO.getAll()) {
	        if (this.checked.get(bill.getBillId())) {
	            billToDelete.add(bill);
	        }
	    }
	    checked.clear();
	    BillDAO.deleteList(billToDelete);
	}
	
	public void ViewBillDetail(String id) throws IOException{
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/viewbilldetail.xhtml?id="+id);
	}
	
	public void listenerThongKe() throws IOException {
		// ...
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/thongke.xhtml");
	}

}
