package com.twister.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.twister.services.Comment;
import com.twister.services.Friend;
import com.twister.services.Search;
import com.twister.services.User;
import com.twister.tools.DateTools;
import com.twister.tools.JSONResponse;

public class Test {

	// information utilisateur 1
	static String nom1 = "AIT HAMMOU";
	static String prenom1 = "Yanis";
	static String login1 = "yanis.aithammou1997@gmail.com";
	static String password1 = "password1";
	static String sex1 = "M";
	static String birth_day1 = "21/09/1997";
	static String key1 = "non_renseigné";
	static String id1 = "28";

	// information utilisateur 2
	static String nom2 = "ALLOUACHE";
	static String prenom2 = "Yacine";
	static String login2 = "yacine.allouache1996@gmail.com";
	static String password2 = "password2";
	static String sex2 = "M";
	static String birth_day2 = "06/04/1996";
	static String key2 = "non_renseigné";

	
	//information du commentaire 1
	static String comment = "je suis un commentaire :)";
	static int id_comment = -1;
	
	
	public static void main(String args[]) throws JSONException {

				
		// créer un utilisateur1
		System.out.println("\n------------------*inscription utilisateur1*---------------------");
		creatUserTest(nom1, prenom1, login1, password1, sex1, birth_day1);

		// connexion de l'utilisateur1
		System.out.println("\n---------------------*connexion utilisateur1*--------------------");
		key1 = loginTest(login1, password1);

		// lister les amis de l'utilisateur 1
		System.out.println("\n----------------*lister d'amis d'utilisateur1*-------------------");
		listerLesAmisTest(key1);

		// deconnexion de l'utilisateur 1
		System.out.println("\n----------------*deconnexion utilisateur1*-----------------------");
		logoutTest(key1);

		// créer l'utilisateur 2
		System.out.println("\n------------------*inscription utilisateur2*---------------------");
		creatUserTest(nom2, prenom2, login2, password2, sex2, birth_day2);

		// connexion de l'utilisateur2
		System.out.println("\n---------------------*connexion utilisateur2*--------------------");
		key2 = loginTest(login2, password2);

		// lister les amis de l'utilisateur 2
		System.out.println("\n----------------*lister d'amis d'utilisateur2*-------------------");
		listerLesAmisTest(key2);

		// ajouter l'utilisateur 1 à la liste d'amis de l'utilisateur 2
		System.out.println("\n--*ajouter l'utilisateur1 dans la liste d'amis de l'utilisateur2--");
		ajouterUnAmisTest(key2, id1);

		// lister les amis de l'utilisateur 2
		System.out.println("\n----------------*liste d'amis d'utilisateur2*-------------------");
		listerLesAmisTest(key2);

		// retirer l'utilisateur 1 de la liste d'amis de l'utilisateur 2
		System.out.println("\n---------*retirer l'utilisateur1 de la liste d'amis de l'utilisateur2*---------");
		supprimeUnAmisTest(key2, id1);

		// lister les amis de l'utilisateur 2
		System.out.println("\n----------------*lister d'amis d'utilisateur2*-------------------");
		listerLesAmisTest(key2);

		// deconnexion de l'utilisateur 1
		System.out.println("\n----------------*deconnexion utilisateur2*-----------------------");
		logoutTest(key2);

		
		// connexion de l'utilisateur1
		System.out.println("\n\n\n\n---------------------*connexion utilisateur1*--------------------");
		key1 = loginTest(login1, password1);

		// ajouter des commentaire
		System.out.println("\n---------------------*ajouter un commentaire*---------------------");
		ajouterCommentaireTest(key1, comment);

		// afficher les commentaire
		System.out.println("\n-------------*Affiche les commentaire d'utilisateur1*--------------");
		id_comment = afficherCommentaireTest(key1);

		//supprimer le commentaire 
		System.out.println("\n------------------*Supprimer le commentaire*----------------------");
		supprimeruncommentaireTest(key1, ""+id_comment);
		
		// afficher les commentaire
		System.out.println("\n-------------*Affiche les commentaire d'utilisateur1*--------------");
		afficherCommentaireTest(key1);
		
		// deconnexion de l'utilisateur 1
		System.out.println("\n----------------*deconnexion utilisateur1*-----------------------");
		logoutTest(key1);
		
		
		// connexion de l'utilisateur1
		System.out.println("\n\n\n\n---------------------*connexion utilisateur1*--------------------");
		key1 = loginTest(login1, password1);
		
		//recherche les amis 
		System.out.println("\n-----------------------*recherche un amis a l'aide de son nom ou prenom*----------------------------");
		recherchAmisTest(key1, nom2);


		//recherche d'autre utilisateur
		System.out.println("\n-----------------------*recherche d'un utilisateur à l'aide de son nom ou prenom*----------------------------");
		rechercheUtilisateurTest(key1, nom2);
		
		
		// deconnexion de l'utilisateur 1
		System.out.println("\n----------------*deconnexion utilisateur1*-----------------------");
		logoutTest(key1);

	}

	/* permet de tester la connexion */
	public static String loginTest(String login, String password) throws JSONException {
		JSONResponse resultat = (JSONResponse) User.login(login, password);
		String key = "";
		if (resultat.isAccepted()) {
			System.out.println("connexion établi avec succès");
			key = resultat.getString("Key");
		} else {
			System.out.println(resultat.getMotif());
		}
		return key;
	}

	/* permet de tester la deconnexion d'un utilisateur */
	public static void logoutTest(String key) {
		JSONResponse resultat = (JSONResponse) User.logout(key);
		if (resultat.isAccepted())
			System.out.println("deconnexion avec succée !!");
		else {
			System.out.println(resultat.getMotif());
		}
	}

	// permet de créer un utilisateur
	public static void creatUserTest(String nom, String prenom, String login, String password, String sex,
			String birth_day) {
		JSONResponse resultat = (JSONResponse) User.createUser(nom, prenom, login, password, sex, birth_day);
		if (resultat.isAccepted())
			System.out.println("utilisateur crée avec succès ");
		else
			System.out.println(resultat.getMotif());
	}

	// permet d'ajouter un utilisateur à la liste d'amis
	public static void ajouterUnAmisTest(String key, String idFriend) {
		JSONResponse resultat = (JSONResponse) Friend.addFriend(key, idFriend);
		if (resultat.isAccepted())
			System.out.println("vous avez ajoutez un amis");
		else
			System.out.println(resultat.getMotif());
	}

	// permet de supprimer un utilisateur de la liste d'amis
	public static void supprimeUnAmisTest(String key, String idFriend) {
		JSONResponse resultat = (JSONResponse) Friend.removeFriend(key, idFriend);
		if (resultat.isAccepted())
			System.out.println("vous avez retirez un amis");
		else
			System.out.println(resultat.getMotif());
	}

	// permet de lister les amis
	public static void listerLesAmisTest(String key) throws JSONException {
		JSONResponse resultat = (JSONResponse) Friend.listeFriend(key);
		if (resultat.isAccepted()) {
			System.out.println("votre liste d'amis :");
			JSONArray listeAmis = (JSONArray) resultat.get("Liste amis ");
			for (int i = 0; i < listeAmis.length(); i++) {
				JSONObject amis = new JSONObject(listeAmis.getString(i));
				System.out.println("\t-> " + amis.getString("nom") + " " + amis.getString("prenom"));
			}

		} else {
			System.out.println(resultat.getMotif());
		}
	}

	// permet d'ajouter un commentaire
	public static void ajouterCommentaireTest(String key, String text) {
		JSONResponse resultat = (JSONResponse) Comment.addComment(key, text);
		if (resultat.isAccepted())
			System.out.println("vous avez ajouter un commentaire");
		else
			System.out.println(resultat.getMotif());
	}

	// permet de supprimer un commentaire
	public static void supprimeruncommentaireTest(String key, String id) {
		JSONResponse resultat = (JSONResponse) Comment.removeComment(key, id);
		if (resultat.isAccepted())
			System.out.println("Commentaire supprimé ");
		else
			System.out.println(resultat.getMotif());
	}

	// permet de chercher un utilisateur a l'aide de son nom ou son prenom
	public static void rechercheUtilisateurTest(String key, String name) throws JSONException {
		JSONResponse resultat = (JSONResponse) Search.searchUser(key, name);
		if (resultat.isAccepted()) {
			JSONArray trouve = (JSONArray) resultat.get("resultat");
			for (int i = 0; i < trouve.length(); i++) {
				JSONObject amis = new JSONObject(trouve.getString(i));
				System.out.println("\t-> " + amis.getString("nom") + " " + amis.getString("prenom"));
			}
		} else {
			System.out.println(resultat.getMotif());
		}
	}

	// permet de chercher de parcourir et afficher un amis
	public static void recherchAmisTest(String key, String name) throws JSONException {
		JSONResponse resultat = (JSONResponse) Search.serachFriendInListFriend(key, name);
		if (resultat.isAccepted()) {
			JSONArray trouve = (JSONArray) resultat.get("resultat");
			for (int i = 0; i < trouve.length(); i++) {
				JSONObject amis = new JSONObject(trouve.getString(i));
				System.out.println("\t-> " + amis.getString("nom") + " " + amis.getString("prenom"));
			}
		} else {
			System.out.println(resultat.getMotif());
		}
	}

	// afficher les commentaires
	public static int afficherCommentaireTest(String key) throws JSONException {
		JSONResponse resultat = (JSONResponse) Search.searchMyComment(key);
		int id = -1;
		if (resultat.isAccepted()) {
			JSONArray listeComments = (JSONArray) resultat.get("resultat");
			for (int i = 0; i < listeComments.length(); i++) {
				JSONObject comment = new JSONObject(listeComments.getString(i));
				System.out.println("--Commentaire-- :" + comment.getString("comment"));
				System.out.print("posté par :" + comment.getString("nom") + " " + comment.getString("prenom"));
				System.out.println("le : " + comment.getString("date"));
				
				return  comment.getInt("id");
				
			}
		} else
			System.out.println(resultat.getMotif());
		return id;
	}
	
	
	
	

}