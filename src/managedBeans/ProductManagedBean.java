package managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import dao.ProductDAO;
import javafx.util.converter.BigDecimalStringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import pojo.*;

@ManagedBean(name = "productBean")
@SessionScoped
public class ProductManagedBean {
	private Part part;
	private Product product = new Product();
	private Category category = new Category();
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public List<Product> getAll() {
		return ProductDAO.getAll();
	}
	
	public List<Product> getTop() {
		return ProductDAO.getTop();
	}

	public Product findProductById(String productId) {
		return ProductDAO.findProductById(Integer.parseInt(productId));
	}

	public boolean deleteProduct(Product product) {
		return ProductDAO.deleteProduct(product);
	}

	public void listenerUpdate(Product product) throws IOException {
		// ...
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/updateproduct.xhtml?id=" + product.getProductId());
	}

	public void listenerAdd() throws IOException {
		// ...
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/addproduct.xhtml");
	}
	
	public void listenerTop() throws IOException {
		// ...
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/topproduct.xhtml");
	}
	
	public void updateProduct(String productId) throws IOException {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String image = "";
		try {
			part.write(
					"D:\\Appilication\\JavaApp\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\AdminWeb\\images\\"
							+ getFileName(part));
			image = "images/" + getFileName(part);
		} catch (Exception e) {
			image = req.getParameter("image");
		}

		String name = req.getParameter("name");

		BigDecimal price = new BigDecimal(req.getParameter("price"));
		String description = req.getParameter("description");
		String category = req.getParameter("category");
		BigDecimal importPrice = new BigDecimal(req.getParameter("imprice"));
		ProductDAO.updateProduct(productId, name, image, price, importPrice, description, category);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/updateproduct.xhtml?id=" + productId);
	}

	public String getFileName(Part tempPart) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}

	public void addProduct() throws IOException {
		int i =ProductDAO.getLastProduct();
		part.write(
				"D:\\Appilication\\JavaApp\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\AdminWeb\\images\\"
						+ getFileName(part));
		String image = "images/" + getFileName(part);
		this.product.setProductImage(image);
		this.product.setCategory(this.category);
		this.product.setProductId(i);
		try {
			ProductDAO.insertProduct(this.product);
			this.product = new Product();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/faces/updateproduct.xhtml?id=" + i);
		} catch (Exception e) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/faces/updateproduct2.xhtml");
		}

	}
}
