package com.smile.AuthServer.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smile.AuthServer.DAO.UsersinfoDAO;
import com.smile.AuthServer.DO.DBConnectionInfo;
import com.smile.AuthServer.DO.UsersDO;
import com.smile.AuthServer.util.GlobalAlert;
import com.smile.AuthServer.util.JwtUtils;

@WebServlet("/UserManage")
public class UserManageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletContext sc = null;
	
	private DBConnectionInfo dbInfo = null;
	private UsersinfoDAO user_dao = null;

	private String action = null;
	private Cookie[] cookies = null;
	
	private GlobalAlert g = new GlobalAlert();
	private String viewName = null;
	private JwtUtils ju = new JwtUtils();
	private Map<String, Object> jwtCont = null;
    
    public UserManageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sc = getServletContext();
        dbInfo = (DBConnectionInfo)sc.getAttribute("dbInfo");
        user_dao = new UsersinfoDAO(dbInfo);
		
		request.setCharacterEncoding("UTF-8");

		action = request.getParameter("action");
		cookies = request.getCookies();
		
		if(action.equals("user_management")){
			jwtCont = ju.checkJwt(getCookieValue(cookies, "com.smile.test.jwt"));
			if(jwtCont != null && (int)jwtCont.get("permission") == 1) {
				List<UsersDO> userList = user_dao.selectAllUsers();
				request.setAttribute("user_list", userList);
				
				viewName = "/WEB-INF/views/user_info.jsp";
			}
			else {
				viewName = "redirect:/login";
			}
		}
		else {
			viewName = "redirect:/login";
		}
		
		if (viewName != null) {
			if(viewName.contains("redirect:")) {
				response.sendRedirect(request.getContextPath() + viewName.split(":")[1]);
			}
			else {
				RequestDispatcher view = request.getRequestDispatcher(viewName);
				view.forward(request, response);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sc = getServletContext();
        dbInfo = (DBConnectionInfo)sc.getAttribute("dbInfo");
        user_dao = new UsersinfoDAO(dbInfo);
		
		request.setCharacterEncoding("UTF-8");

		action = request.getParameter("action");
		cookies = request.getCookies();
		
		if (action.equals("user_delete")){	 // 회원 정보 삭제
			jwtCont = ju.checkJwt(getCookieValue(cookies, "com.smile.test.jwt"));
			if(jwtCont != null && (int)jwtCont.get("permission") == 1) {
				String user = request.getParameter("id");
				user_dao.deleteUser(user);
				viewName = "redirect:/login?action=user_management";
			}
			else {
				viewName = "redirect:/login";
			}
		}
		else {
			viewName = "redirect:/login";
		}
		
		if (viewName != null) {
			if(viewName.contains("redirect:")) {
				response.sendRedirect(request.getContextPath() + viewName.split(":")[1]);
			}
			else {
				RequestDispatcher view = request.getRequestDispatcher(viewName);
				view.forward(request, response);
			}
		}
	}
	
	public String getCookieValue(Cookie[] cookies, String cookieName) {
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
