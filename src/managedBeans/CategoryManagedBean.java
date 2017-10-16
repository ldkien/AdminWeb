package managedBeans;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import dao.CategoryDAO;
import dao.ProductDAO;
import pojo.Category;
import pojo.Product;

@ManagedBean(name = "categoryBean")
@SessionScoped
public class CategoryManagedBean {
	Category category = new Category();
	boolean kt=false;
	public boolean isKt() {
		return kt;
	}

	public void setKt(boolean kt) {
		this.kt = kt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getAll() {
		return CategoryDAO.getAll();
	}
	
	public Category findCategoryById(String categoryId) {
		return CategoryDAO.findCategoryById(Integer.parseInt(categoryId));
	}
	
	public boolean deleteCategory(Category category) {
		return CategoryDAO.deleteCategory(category);
	}
	
	public boolean insertCategory(Category category) {
		return CategoryDAO.insertCategory(category);
	}
	
	public void updateCatergory(String categoryId) throws IOException {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		if (req.getAttribute("newnamecate") != null&&req.getAttribute("error").toString().equals("false")) {
			String name = (String) req.getAttribute("newnamecate");
			CategoryDAO.updateCategory(categoryId, name);			
			this.kt=false;
			ec.redirect(ec.getRequestContextPath() + "/faces/details_category.xhtml");
		}
		else{
			
			this.kt=true;
			//ec.redirect(ec.getRequestContextPath() + "/faces/updatecategory.xhtml?id="+req.getAttribute("idn"));
			//this.kt=true;
		}
	}
	
	public void listenerUpdate(Category category) throws IOException {
		// ...
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/updatecategory.xhtml?id=" + category.getCategoryId());
	}
	
	public void listenerAdd() throws IOException {
		// ...
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/addcategory.xhtml");
	}
	
	public void addCategory() throws IOException {
		int i = CategoryDAO.getLastProduct();
		this.category.setCategoryId(i);
		try {
			if(CategoryDAO.insertCategory(this.category))
			{
				this.kt=false;
				this.category = new Category();
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(ec.getRequestContextPath() + "/faces/updatecategory.xhtml?id=" + i);
			}
			else
			{
				this.kt=true;
			}
		} catch (Exception e) {
			// ExternalContext ec =
			// FacesContext.getCurrentInstance().getExternalContext();
			// ec.redirect(ec.getRequestContextPath() +
			// "/faces/updateproduct2.xhtml");
		}

	}
}
