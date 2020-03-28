package com.twister.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.twister.DataBases.COMMMENT_DB;
import com.twister.DataBases.FRIEND_DB;
import com.twister.DataBases.SESSION_DB;
import com.twister.DataBases.USER_DB;
import com.twister.tools.JSONResponse;

public class Search {

	/**
	 * Cherche un utilisateur par son nom ou prenom
	 * 
	 * @param name String le nom de l'utilisateur
	 * @return JSONObject {login:"login",nom:"nom",prenom:"prenom"}
	 */
	public static JSONObject searchUser(String key, String name) {
		try {
			if (SESSION_DB.estDejaConnecte(key)) {
				List<JSONObject> liste = USER_DB.getUser(name);
				if(liste.isEmpty())
					return JSONResponse.serviceRefused("recherche vide ", 2);
				JSONResponse rep = JSONResponse.serviceAccepted();
				rep.put("resultat", liste);
				return rep;
			} else {
				return JSONResponse.serviceRefused("n'existe pas {searchUser}", 3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("Erreur SQL {searchUser}", 10000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("Erreur JSON {searchUser}", 1000);
		}
	}

	/**
	 * Cherche un amis dans la liste d'amis de l'utilisateur
	 * 
	 * @param key  String la cle de la connexion de l'utilisateur
	 * @param name String le nom de l'amis que on veut chercher
	 * @return JSONObject si il on a trouve
	 */
	public static JSONObject serachFriendInListFriend(String key, String name) {
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("connexion denied ", 2);
			}
			int id_user = SESSION_DB.getIdUserOfKey(key);

			List<JSONObject> list = FRIEND_DB.listeOfFriend(id_user);

			for (JSONObject jsonObject : list) {
				if (jsonObject.getString("nom").toLowerCase().equals(name.toLowerCase())
						| jsonObject.getString("prenom").toLowerCase().equals(name.toLowerCase())) {
					return jsonObject;
				}
			}
			return JSONResponse.serviceRefused("pas d'amis trouvés ", 5);
		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEMS {serachFriendInListFriend}", 1000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSON PROBLEM {serachFriendInListFriend}", 100000);
		}
	}

	/**
	 *  lister les messages de l'utilisateur
	 * @param key
	 * @return JSONObject {resultat:JSONArray}
	 */
	public static JSONObject searchMyComment(String key) {
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("connexion denied", 1);
			}
			int id_user = SESSION_DB.getIdUserOfKey(key);
			List<JSONObject> list = COMMMENT_DB.getUserCommentsId_Author(id_user);

			JSONObject js = JSONResponse.serviceAccepted();
			js.put("resultat", list);
			return js;

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEM {searchMyComments}", 1000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSON PROBLEM {searchMyComments}", 10000);
		}
	}

	/**
	 * @param key  String
	 * @param name
	 * @return
	 */
	public static JSONObject searchComment(String key, String name) {
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("connexion denied", 1);
			}
			JSONObject js = JSONResponse.serviceAccepted();
			js.put("comments", COMMMENT_DB.getUserComments_NOM(name));
			return js;
		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEMS {searchComment}", 1000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSONObject erreur {searchComment}", 100000);
		}
	}

	public static JSONObject searchCommentLastNHour(String key, int n) {
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("connexion denied", 1);
			}
			JSONObject js = JSONResponse.serviceAccepted();
			js.put("comments", COMMMENT_DB.getCommentForLastNHour(n));
			return js;
		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEMS {searchCommentLastNHour}", 1000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSONObject erreur {searchCommentLastNHour}", 100000);

		}
	}

	public static JSONObject searchCommentsFriend(String key) {
		try {
			if (!SESSION_DB.estDejaConnecte(key)) {
				return JSONResponse.serviceRefused("connexion denied", 1);
			}
			int id_user = SESSION_DB.getIdUserOfKey(key);
			List<JSONObject> listFriends = FRIEND_DB.listeOfFriend(id_user);
			List<JSONObject> commentList = new ArrayList<JSONObject>();
			for (JSONObject jsonObject : listFriends) {
				commentList.addAll(COMMMENT_DB.getUserCommentsId_Author(jsonObject.getInt("id")));
			}

			JSONObject js = JSONResponse.serviceAccepted();
			js.put("idUser", id_user);
			js.put("commentsFriends", commentList);
			return js;

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEMS {searchCommentsFriend}", 1000);

		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSONObject erreur {searchCommentsFriend}", 100000);
		}
	}

}
