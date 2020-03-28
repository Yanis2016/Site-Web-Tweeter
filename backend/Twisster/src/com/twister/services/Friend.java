package com.twister.services;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.twister.DataBases.FRIEND_DB;
import com.twister.DataBases.SESSION_DB;
import com.twister.tools.DateTools;
import com.twister.tools.JSONResponse;

public class Friend {

	/**
	 * Entrer : key ,id_friend
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
	 * 								-> User(key) et id_friend sont déja amis 
	 * 
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur sql(Exception)
	 * 
	 *  				 ->  JSONResponse.getCode() == 100000 :
	 * 								-> Erreur JSON (Exception)					
	 */
	public static JSONObject addFriend(String key, String id_Friend) {
		if (key == null | id_Friend == null)
			return JSONResponse.serviceRefused("Erreur de saisie", 1);
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("Vous n'etes pas connectee", 2);
			}

			int id_user = SESSION_DB.getIdUserOfKey(key);
			int idFriend = Integer.parseInt(id_Friend);

			if (FRIEND_DB.isFriend(id_user, idFriend)) {
				return JSONResponse.serviceRefused("Deja amis", 3);
			}

			if (FRIEND_DB.addFriend(id_user, idFriend, DateTools.getFormatedDateAfterNHour(0))) {
				return JSONResponse.serviceAccepted();

			} else
				return JSONResponse.serviceRefused("Erreur sql {addFriend}", 2000);

		} catch (SQLException e) {
			return JSONResponse.serviceRefused("JSON ERREUR {addFriend}", 1000);
		}
	}

	/**
	 * Entrer : key, id_friend
	 * Sortie : JSONResponse(JSON)
	 * 		    JSONResponse.isAccepted() == true :
	 * 					 ->  JSONArray(Liste amis ) // liste d'amis
	 * 
	 * 			JSONResponse.isAccepted() == false :
	 * 					 ->  JSONResponse.getCode() == 1 :
	 * 								-> Erreur de Saisie (key, text)
	 * 
	 * 					 ->  JSONResponse.getCode() == 2 :
	 * 								-> User key n'est pas connectée
	 *  			
	 *  				 ->  JSONResponse.getCode() == 3 :
	 * 								-> Amis introuvable 
	 * 
	 *  				 ->  JSONResponse.getCode() == 4 :
	 * 								-> Erreur lors de suppression
	 * 
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur SQL (Exception)					
	 */
	public static JSONObject removeFriend(String key, String id_Friend) {
		if (key == null | id_Friend == null)
			return JSONResponse.serviceRefused("Erreur de saisie", 1);
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("Vous n'etes pas connectee", 2);
			}

			int id_user = SESSION_DB.getIdUserOfKey(key);
			int idFriend = Integer.parseInt(id_Friend);

			if (!FRIEND_DB.isFriend(id_user, idFriend)) {
				return JSONResponse.serviceRefused("amis introuvable", 3);
			}

			if (FRIEND_DB.removeFriend(id_user, idFriend)) {
				return JSONResponse.serviceAccepted();
			} else
				return JSONResponse.serviceRefused("ERREUR de suppresion d'amis erreur grave", 4);

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("ERREUR SQL IN DELETE FRIEND ", 2000);
		}

	}

	/**
	 * Entrer : key 
	 * Sortie : JSONResponse(JSON)
	 * 		    JSONResponse.isAccepted() == true :
	 * 					 ->  JSONArray(Liste amis ) // liste d'amis
	 * 
	 * 			JSONResponse.isAccepted() == false :
	 * 					 ->  JSONResponse.getCode() == 1 :
	 * 								-> Erreur de Saisie (key, text)
	 * 
	 * 					 ->  JSONResponse.getCode() == 2 :
	 * 								-> User key n'est pas connectée
	 *  			
	 *  				 ->  JSONResponse.getCode() == 3 :
	 * 								-> Liste d'amis vide 
	 * 
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur sql(Exception)
	 * 
	 *  				 ->  JSONResponse.getCode() == 10000 :
	 * 								-> Erreur JSON (Exception)					
	 */
	public static JSONObject listeFriend(String key) {
		if (key == null)
			return JSONResponse.serviceRefused("Erreur de saisie", 1);
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("Vous n'etes pas connectee", 2);
			}

			int id_user = SESSION_DB.getIdUserOfKey(key);

			List<JSONObject> li = FRIEND_DB.listeOfFriend(id_user);
			if (li.isEmpty())
				return JSONResponse.serviceRefused("liste d'amis vides", 3);

			JSONResponse jsr = JSONResponse.serviceAccepted();
			jsr.put("Liste amis ", li);

			return jsr;

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEME {listeFriend}", 2000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSON PROBLEM IN {listeFriend}", 10000);
		}
	}

}
