package com.twister.servlets.comment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class Removecomment
 */
@WebServlet(name = "RemoveComment", urlPatterns = { "/RemoveComment" })
public class RemoveComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		String id = request.getParameter("id");

		JSONObject res = com.twister.services.Comment.removeComment(key, id);
		response.setContentType("text/JSON");
		response.getWriter().println(res);
	}

}
