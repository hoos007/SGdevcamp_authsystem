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

import com.smile.AuthServer.DAO.*;
import com.smile.AuthServer.DO.*;
import com.smile.AuthServer.util.*;

@WebServlet("/login")
public class AuthController extends HttpServlet {
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
	
    public AuthController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sc = getServletContext();
        dbInfo = (DBConnectionInfo)sc.getAttribute("dbInfo");
        user_dao = new UsersinfoDAO(dbInfo);
		
		request.setCharacterEncoding("UTF-8");
		
		action = request.getParameter("action");
		cookies = request.getCookies();
		
		if(action == null){
			jwtCont = ju.checkJwt(getCookieValue(cookies, "com.smile.test.jwt"));
			if(jwtCont != null) {
				request.setAttribute("id", jwtCont.get("id"));
			}
			viewName = "/login.jsp";
		}
		else if(action.equals("user_register_form")) { // 가입 화면	
			viewName = "/WEB-INF/views/register.jsp";
		}
		else if(action.equals("logout")) { // 로그아웃 과정				
			Cookie cookie = new Cookie("com.smile.test.jwt", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			
			viewName = "redirect:/login";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		sc = getServletContext();
        dbInfo = (DBConnectionInfo)sc.getAttribute("dbInfo");
        user_dao = new UsersinfoDAO(dbInfo);
		
		action = request.getParameter("action");
		cookies = request.getCookies();
		
		if(action.equals("register")) { // 계정 추가
			UsersDO user = new UsersDO();
			user.setId(request.getParameter("id"));
			
			if(user_dao.selectUser(user.getId()) != null) {
				g.jsmessage(response, "해당 아이디가 이미 존재합니다.");
			}
			else {
				String password = request.getParameter("password");
				String pwCK = request.getParameter("passwordCheck");
				
				if(pwCK.equals(password)) {
					user.setPassword(password);
					user.setName(request.getParameter("name"));
	
					user_dao.insertUser(user);
					
					viewName = "redirect:/login";
				}
				else {
					g.jsmessage(response, "비밀번호가 일치하지 않습니다.");
				}
			}
		}
		else if(action.equals("login")) { // 로그인 과정				
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UsersDO users = new UsersDO();
			users.setId(id);
			users.setPassword(password);
			UsersDO user = user_dao.loginID(users);
			
			if(user != null) {
				//로그인 성공
				String token = ju.createJwt(user.getId(), user.getPermission());
				Cookie cookie = new Cookie("com.smile.test.jwt", token);
				cookie.setHttpOnly(true);
				response.addCookie(cookie);
				
				viewName = "redirect:/login";
			}
			else {
				//로그인 실패
				g.jsmessage(response, "아이디/비밀번호가 일치하지 않습니다.");
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
