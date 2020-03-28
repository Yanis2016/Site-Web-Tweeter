package com.twister.DataBases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author root
 *
 */

public class USER_DB {

	private static String getUser = "SELECT * FROM `USER` WHERE LOGIN = ?";
	private static String getUser1 = "SELECT * FROM `USER` WHERE NOM = ? or PRENOM = ?";
	private static String getNameUser = "SELECT * FROM `USER` WHERE ID_USER = ?";
	private static String getPassword = "SELECT password FROM `USER` WHERE login = ?";
	private static String addUser = "INSERT INTO USER (LOGIN, PASSWORD, NOM,PRENOM,SEX,DATE_DE_NAISSANCE,DATE_INSCRIPTION) VALUES (?,?,?,?,?,?,?)";
	private static String getIdUser = "SELECT ID_USER FROM `USER` WHERE LOGIN = ?";

	/**
	 * Verfie l'existance d'un utilisateur dans la base de donnee USER à partir de
	 * son login
	 * 
	 * @param login le login de l'utilisateur c'est un String
	 * @return boolean true si il existe false si non
	 * @throws SQLException peut lever une exception de connexion
	 */
	public static boolean exists(String login) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement st = connexion.prepareStatement(getUser);
		st.setString(1, login);
		ResultSet rs = st.executeQuery();
		boolean res = rs.first();
		rs.close();
		st.close();
		connexion.close();
		return res;
	}

	/**
	 * Verifie le mot de passe passe en parametre de l'utilisateur login correspond
	 * a celui qui est dans la base USER
	 * 
	 * @param login    le login de l'utiisateur c'est un String
	 * @param password le mot de passe de l'utilsateur c'est un String
	 * @return boolean true si le mot de passe correspond a celui qui est dans la
	 *         base USER
	 * @throws SQLException une exception de connexion
	 */
	public static boolean checkPassword(String login, String password) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement st = connexion.prepareStatement(getPassword);
		st.setString(1, login);
		ResultSet rs = st.executeQuery();
		rs.first();
		boolean res = rs.getString("PASSWORD").equals(password);
		rs.close();
		st.close();
		connexion.close();
		return res;
	}

	/**
	 * Retourne l'identifiant de l'utilsateur qui a pour login le login passe en
	 * parametre
	 * 
	 * @param login le login de l'utiisateur c'est un String
	 * @return int identifiant de l'utilisteur de login
	 * @throws SQLException une exception de connexion
	 */
	public static int getId(String login) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement ps = connexion.prepareStatement(getIdUser);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		rs.first();
		int res = rs.getInt("ID_USER");
		rs.close();
		ps.close();
		connexion.close();
		return res;
	}

	/**
	 * Ajoute un user a la base de donnee user, il y'a des contraintes : il faut que
	 * le login n'existe pas deja il faut qui'il soit unique
	 * 
	 * @param nom      String le nom de l'utilsateur
	 * @param prenom   String le prenom de l'utilisateur
	 * @param login    String le login de l'utilsateur
	 * @param password String le mot de passe de l'utilisateur
	 * @param sex      String le sex de l'utilisateur
	 * @param birthDay Date la date de naissance de l'utilisateur
	 * @param dateS    String la date d'inscription de l'utilisateur
	 * @return boolean true si l'ajout est reussie false si non
	 * @throws SQLException erreur de connexion a la base de donnee
	 */
	public static boolean addUSer(String nom, String prenom, String login, String password, String sex,
			java.util.Date birthDay, String dateS) throws SQLException {

		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement ps = connexion.prepareStatement(addUser);
		ps.setString(1, login);
		ps.setString(2, password);
		ps.setString(3, nom);
		ps.setString(4, prenom);
		ps.setString(5, sex);
		ps.setDate(6, java.sql.Date.valueOf(birthDay.toString()));
		ps.setString(7, dateS);

		ps.executeUpdate();

		ps.close();
		connexion.close();

		return true;
	}

	/**
	 * Retourne un JASONObject qui a le nom et le prenom de l'utilisateur qui a
	 * l'identifiant id
	 * 
	 * @param idUser int l'identifiant de l'utilisateur
	 * @return JSONObject {"nom":nomUser;"prenom":prenomUser} sont les deux des
	 *         String
	 * @throws SQLException erreur de connexion a la base de donnee
	 */
	public static JSONObject getNameUser(int idUser) throws SQLException {

		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement st = connexion.prepareStatement(getNameUser);
		st.setInt(1, idUser);
		ResultSet rs = st.executeQuery();
		JSONObject name = new JSONObject();
		while (rs.next()) {
			try {
				name.put("nom", rs.getString("NOM"));
				name.put("prenom", rs.getString("PRENOM"));
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		rs.close();
		st.close();
		connexion.close();
		return name;
	}

	/**
	 * Retourne un json object qui contient un jason et une liste de jason qui
	 * conntiennes les attributs des user qui ont le nom = name
	 * 
	 * @param name String name of user
	 * @return JSONBject {id:"id",login:"login",nom:"nom",prenom:"prenom"}
	 * @throws SQLException  erreur de connexion a la base de donnee
	 * @throws JSONException erreur de JSON
	 */
	public static List<JSONObject> getUser(String name) throws SQLException, JSONException {

		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement st = connexion.prepareStatement(getUser1);
		st.setString(1, name);
		st.setString(2, name);

		ResultSet rs = st.executeQuery();
		JSONObject result = new JSONObject();
		List<JSONObject> liste = new ArrayList<JSONObject>();

		while (rs.next()) {
			JSONObject js = new JSONObject();
			js.put("id", rs.getInt("ID_USER"));
			js.put("login", rs.getString("LOGIN"));
			js.put("nom", rs.getString("NOM"));
			js.put("prenom", rs.getString("PRENOM"));
			liste.add(js);
		}
		rs.close();
		st.close();
		connexion.close();
		return liste;
	}
}
