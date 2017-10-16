package managedBeans;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import dao.CategoryDAO;
import pojo.Category;

/**
 * Servlet Filter implementation class CategoryFilter
 */
@WebFilter("/CategoryFilter")
public class CategoryFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public CategoryFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String contextPath = ((HttpServletRequest)request).getContextPath();
		HttpServletRequest req = (HttpServletRequest) request;
		String namecate = null;
		String id=null;
		if(req.getParameter("id")!=null)
			id= req.getParameter("id");
		if(req.getParameter("namecate")!=null){
			
			namecate = req.getParameter("namecate");
			namecate=chuanHoa(namecate);
			namecate = namecate.trim().toUpperCase();
			if(kiemTra(namecate,id)){
				req.setAttribute("newnamecate", namecate);
				req.setAttribute("error", "false");
			}
			else {
				((HttpServletResponse)response).setContentType("text/html;charset=UTF-8");
				 ((HttpServletResponse)response).sendRedirect(contextPath + "/faces/updatecategory.xhtml?id="+id);
				req.setAttribute("error", "true");
			}
		}
		chain.doFilter(request, response);
	}
	
	public String chuanHoa(String name){
		String[] key = name.split("\\s");
		String chuoidaxyly="";
		for (String s : key) {
			if (!s.equals(""))
				chuoidaxyly+=s+" ";
		}
		return chuoidaxyly;	
	}
	
	public boolean kiemTra(String cate,String id ){
		List<Category> category = CategoryDAO.getAll();
		for(Category c : category){
			if(c.getCategoryName().toUpperCase().equals(cate)&&c.getCategoryId()!=Integer.parseInt(id))
				return false;
		}
		return true;
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
