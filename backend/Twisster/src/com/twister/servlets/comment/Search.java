package com.twister.servlets.comment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Entrer : key ,text
	 * Sortie : JSONResponse(JSON)
	 * 		    JSONResponse.isAccepted() == true :
	 * 					 ->  Commentaire ajouté avec succés
	 * 
	 * 			JSONResponse.isAccepted() == false :
	 * 					 ->  JSONResponse.getCode() == 1 :
	 * 								-> Erreur de Saisie (key, text)
	 * 
	 * 					 ->  JSONResponse.getCode() == 2 :
	 * 								-> User key n'est pas connectée
	 *  			
	 *  				 ->  JSONResponse.getCode() == 3 :
	 * 								-> Erreur inatendue 
	 * 
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur sql(Exception)
	 * 
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur JSON (Exception)					
	 */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
