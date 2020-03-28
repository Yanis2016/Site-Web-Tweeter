package com.twister.DataBases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class FRIEND_DB {

	private static final String QUERY_IS_FRIEND = "SELECT * FROM `FRIEND` WHERE id_user = ? and id_Friend = ? ;";
	private static final String LISTE_FRIEND = "SELECT * FROM `FRIEND` WHERE id_user=? ;";

	/**
	 * Ajout une ligne dans la base de donne Friend qui a comme attribut
	 * id_user,id_friend et Date tel que id_user c'est celui qui essay d'ajouter
	 * l'autre utilisateur id_friend a sa liste d'amis date c'est la date d'ajout
	 * 
	 * @param idUser   int identifiant de l'utilisateur
	 * @param idFriend int identifiant de l'autre utilisateur
	 * @param date     String la date de l'ajout a sa liste d'amis
	 * @return boolean true si l'operation est reussie false si non
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean addFriend(int idUser, int idFriend, String date) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		String query = "INSERT INTO `FRIEND` VALUES ('" + idUser + "','" + idFriend + "','" + date + "')";

		Statement st = connexion.createStatement();

		st.executeUpdate(query);

		st.close();
		connexion.close();
		return true;
	}

	/**
	 * Supprime une ligne dans la base de donne Friend qui a comme attribut
	 * id_user,id_friend et Date tel que id_user c'est celui qui essay de supprimer
	 * l'autre utilisateur id_friend de sa liste d'amis
	 * 
	 * @param idUser   Integer l'identifiant de l'utilisateur
	 * @param idFriend Integer l'identifiant de l'autre l'utilisateur
	 * @return true si l'operation est ressie
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean removeFriend(int idUser, int idFriend) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		String query = "DELETE FROM `FRIEND` WHERE `id_Friend`= \"" + idFriend + "\"";

		Statement st = connexion.createStatement();

		st.executeUpdate(query);

		st.close();
		connexion.close();
		return true;
	}

	/**
	 * retourne true si les deux utilisateur qui ont comme identifaint id_user et
	 * id_friend sont amis false si non
	 * 
	 * 
	 * @param id_user   Integer l'identifiant de l'utilisateur
	 * @param id_friend Integer l'identifiant de l'autre l'utilisateur
	 * @return true si les deux utilisateur sont amis false si non
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean isFriend(int id_user, int id_friend) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement ps = connexion.prepareStatement(QUERY_IS_FRIEND);

		ps.setInt(1, id_user);
		ps.setInt(2, id_friend);

		ResultSet rs = ps.executeQuery();

		boolean resultat = rs.first();

		rs.close();
		connexion.close();

		return resultat;
	}

	/**
	 * Retourne la liste des amis de l'utilisateur qui a comme identifiant id_user
	 * List of JSON {"id":id,"nom":nom,"prenom":prenom}
	 * @param id_user Integer l'identifiant de l'utilisateur
	 * @return List of JSONObject liste des amis de l'utilisateur id_user
	 * @throws SQLException
	 */
	public static List<JSONObject> listeOfFriend(int id_user) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement ps = connexion.prepareStatement(LISTE_FRIEND);
		ps.setInt(1, id_user);

		ResultSet rs = ps.executeQuery();

		List<JSONObject> list_json_amis = new ArrayList<JSONObject>();

		while (rs.next()) {
			try {
				JSONObject js = new JSONObject();
				js.put("id", rs.getInt("id_Friend"));
				js.put("nom", USER_DB.getNameUser(rs.getInt("id_Friend")).get("nom"));
				js.put("prenom", USER_DB.getNameUser(rs.getInt("id_Friend")).get("prenom"));
				list_json_amis.add(js);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return list_json_amis;

	}

}
