package managedBeans;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.UserDAO;
import pojo.*;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserManagedBean {
	public Users  getUser(int userId) {
		return UserDAO.findUserById(userId);
	}
}
