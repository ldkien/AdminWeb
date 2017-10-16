package managedBeans;
import pojo.Admin;

import java.io.IOException;

import javax.faces.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginManagedBean {

	Admin admin = new Admin();
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public void Login() throws IOException {
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", username);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();   
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		String page = "";
		AdminDAO adminDao = new AdminDAO();
		if(adminDao.Login(this.admin)){
			HttpSession session = request.getSession(true);
			if(session.getAttribute("pages")==null)
				context.redirect(context.getRequestContextPath() + "/faces/index.xhtml" );
			else {
				context.redirect(context.getRequestContextPath() + "/faces"+session.getAttribute("pages") );
			}
			session.setAttribute("admin_name", this.admin.getAdminName());
		}
		else
			context.redirect(context.getRequestContextPath() + "/faces/login.xhtml" );

	
	}
	public LoginManagedBean() {		
	}

}
