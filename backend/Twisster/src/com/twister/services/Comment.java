package com.twister.services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.twister.DataBases.COMMMENT_DB;
import com.twister.DataBases.SESSION_DB;
import com.twister.DataBases.USER_DB;
import com.twister.tools.JSONResponse;

public class Comment {

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
	 *  				 ->  JSONResponse.getCode() == 100000 :
	 * 								-> Erreur JSON (Exception)					
	 */
	public static JSONObject addComment(String key, String text) {

		if (text == null | key == null) {
			return JSONResponse.serviceRefused("Erreur de saisie", 1);
		}

		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("Vous n'etes pas connectee", 2);
			}

			int idUser = SESSION_DB.getIdUserOfKey(key);
			JSONObject nameUser = USER_DB.getNameUser(idUser);

			return COMMMENT_DB.addComment(idUser, nameUser.getString("nom"), nameUser.getString("prenom"), text)
					? JSONResponse.serviceAccepted()
					: JSONResponse.serviceRefused("erreur inatendue", 3);

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("Erreur sql {addComment}", 2000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSON PROBLEM IN {ADD COMMENT}", 10000);
		}
	}

	/**
	 * Entrer : key ,text
	 * Sortie : JSONResponse(JSON)
	 * 		    JSONResponse.isAccepted() == true :
	 * 					 ->  Commentaire supprimé avec succés
	 * 
	 * 			JSONResponse.isAccepted() == false :
	 * 					 ->  JSONResponse.getCode() == 1 :
	 * 								-> Erreur de Saisie (key, text)
	 * 
	 * 					 ->  JSONResponse.getCode() == 2 :
	 * 								-> User (key) n'est pas connectée
	 *  			
	 *  				 ->  JSONResponse.getCode() == 3 :
	 * 								-> Erreur inatendue 
	 * 
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur sql(Exception)				
	 */
	public static JSONObject removeComment(String key, String id) {

		if (id == null | key == null) {
			return JSONResponse.serviceRefused("Erreur de saisie", 1);
		}

		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("Vous n'etes pas connectee", 2);
			}

			int id_user = SESSION_DB.getIdUserOfKey(key);
			int id_comment = Integer.parseInt(id);

			return COMMMENT_DB.removeComment(id_user, id_comment) ? JSONResponse.serviceAccepted()
					: JSONResponse.serviceRefused("erreur inatendue", 3);

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("Erreur sql {addComment}", 2000);
		}
	}
}
