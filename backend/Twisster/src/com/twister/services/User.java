package com.twister.services;

import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.twister.DataBases.SESSION_DB;
import com.twister.DataBases.USER_DB;
import com.twister.tools.AuthentificationTools;
import com.twister.tools.JSONResponse;
import com.twister.tools.SessionTools;
import com.twister.tools.DateTools;

public class User {

	/**
	 * verifie les paramtere, le non existance de login dans la base de donnee et
	 * ajoute une ligne dans la table USER qui a ses attributs
	 * 
	 * @param nom       String nom de nouveau utilisateur
	 * @param prenom    String prenom de nouveau utilisateur
	 * @param login     String le login de nouveau utilisateur
	 * @param password  String le password de nouveau utilisateur
	 * @param sex       String le sex de l'utilisateur
	 * @param birth_day String la date de naissance de l'utilisateur sous forme
	 *                  YYYY/MM/DD
	 * @return JSONResponse {} si l'ajout est reussi si non {"motif";code} d'erreur
	 *
	 * Entrer : nom, prenom, login, password, sex, birth_date
	 * Sortie : JSONResponse(JSON)
	 * 		    JSONResponse.isAccepted() == true :
	 * 					 ->  utilisateur crée avec succés
	 * 
	 * 			JSONResponse.isAccepted() == false :
	 * 					 ->  JSONResponse.getCode() == 1 :
	 * 								-> Erreur de Saisie //controle dans coté Client et coté Serveur
	 * 
	 * 					 ->  JSONResponse.getCode() == 2 :
	 * 								-> compte existe déja 
	 *  			
	 *  				 ->  JSONResponse.getCode() == 2000 :
	 * 								-> Erreur SQL (Exception)					
	 */
	public static JSONObject createUser(String nom, String prenom, String login, String password, String sex,
			String birth_day) {
		
		if (nom == null || prenom == null || login == null || password == null || sex == null || birth_day == null) {
			return JSONResponse.serviceRefused("Erreur de saisie ", 1);
		}

		if (AuthentificationTools.userExists(login)) {
			return JSONResponse.serviceRefused("utilisateur existe deja", 2);
		}

		try {
			Date birthDay = java.sql.Date
					.valueOf(birth_day.split("/")[2] + "-" + birth_day.split("/")[1] + "-" + birth_day.split("/")[0]);

			USER_DB.addUSer(nom, prenom, login, password, sex, birthDay, DateTools.getFormatedDateAfterNHour(0));

			return JSONResponse.serviceAccepted();

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEME {creatUser}", 2000);
		}
	}

	/**
	 * Verifie ses parametre et ajout une ligne dans la table session avec une cle
	 * qui sera generer automatiquement
	 * 
	 * @param login    String le login de l'utilisateur
	 * @param password String le password de l'utilisateur
	 * @return JSONResponse {} si l'ajout est reussi si non {"motif";code} d'erreur
	 */
	public static JSONObject login(String login, String password) {
		if (login == null | password == null)
			return JSONResponse.serviceRefused("Erreur de saisie", 1);
		if (!AuthentificationTools.userExists(login))
			return JSONResponse.serviceRefused("login non reconnu ", 2);
		if (!AuthentificationTools.checkPassword(login, password))
			return JSONResponse.serviceRefused("mot de passe incorrect", 3);

		try {

			int idUser = USER_DB.getId(login);

//			if (SESSION_DB.estDejaConnecte(idUser)) {
//				return JSONResponse.serviceRefused("vous etes deja connecte", 4);
//			}

			String key = SessionTools.generateKey(idUser, login);

			SESSION_DB.insert(key, idUser, DateTools.getFormatedDateAfterNHour(0));

			JSONResponse jse = JSONResponse.serviceAccepted();

			jse.put("ID", idUser);
			jse.put("Login", login);
			jse.put("Key", key);

			return jse;
		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEME {login}", 1000);
		} catch (JSONException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("JSON PROBLEM IN {login}", 100000);
		}

	}

	/**
	 * 
	 * Supprime une ligne dans la table Session apre la verification de ses
	 * parametre
	 * 
	 * @param key String la cle de la connexion de l'utilisateur
	 * @return JSONResponse {} si l'ajout est reussi si non {"motif";code} d'erreur
	 */
	public static JSONObject logout(String key) {
		if (key == null)
			return JSONResponse.serviceRefused("Erreur de saisie", 1);

		try {
			if (!SESSION_DB.estDejaConnecte(key))
				return JSONResponse.serviceRefused("vous n'etes pas connecte", 2);

			if (SESSION_DB.removeSession(key))
				return JSONResponse.serviceAccepted();

			return JSONResponse.serviceRefused("SQL PROBLEME {logout}", 1000);

		} catch (SQLException e) {
			e.printStackTrace();
			return JSONResponse.serviceRefused("SQL PROBLEME {logout}", 1000);
		}

	}
}
