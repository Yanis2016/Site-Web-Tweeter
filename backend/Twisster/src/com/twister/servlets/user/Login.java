package com.twister.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.twister.services.Friend;
import com.twister.services.User;
import com.twister.tools.JSONResponse;

@WebServlet("/AddServlet")
public class Login extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String password = request.getParameter("password");
		String login = request.getParameter("login");
		
		JSONResponse resLogin = (JSONResponse)User.login(login, password);
		try {
			if(resLogin.isAccepted())
				request.setAttribute("amis",Friend.listeFriend(resLogin.getString("Key")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("res", resLogin);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/principale.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
