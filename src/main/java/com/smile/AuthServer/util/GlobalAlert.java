package com.smile.AuthServer.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class GlobalAlert {
   
	public void jsmessage(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script> alert('" + message.replace('\n', ' ') + "'); history.go(-1); </script> ");
	}
}
