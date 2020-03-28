package com.twister.DataBases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.twister.tools.DateTools;

public class SESSION_DB {

	private static final String INSERT_SESSION_QUERY = "INSERT INTO SESSION VALUES(?,?,?,?)";
	private static final String GET_SESSION_FROM_ID_USER = "SELECT cle,Date_fin FROM `SESSION` WHERE id_user= ?";
	private static final String UPDATE_DATE_FIN_ID = "UPDATE `SESSION` SET Date_fin=? WHERE id_user= ?";
	private static final String GET_SESSION_FROM_KEY = "SELECT cle,Date_fin FROM `SESSION` WHERE cle=  ? ";
	private static final String UPDATE_DATE_FIN_KEY = "UPDATE `SESSION` SET Date_fin=? WHERE cle= ?";
	private static final String REMOVE_SESSION = "DELETE FROM `SESSION` WHERE cle= ?";
	private static final String GET_IDUSER_WITH_KEY = "SELECT id_user FROM `SESSION` WHERE cle= ?";

	/**
	 * Insert une nouvelle session de onnexion pour l'user idUsr qui a comme cle key
	 * avec une date de connexion qui est la date courante et insert une date fin
	 * qui est une heur apres il faut que la cles soit unique
	 * 
	 * @param key           String la cle de la connexion
	 * @param idUser        int l'identifaint de l'utilisateur
	 * @param dateConnexion String date de connexion
	 * @return boolean true si l'ajout de la session a la base de donne c'est bien
	 *         passe false sinon
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean insert(String key, int idUser, String dateConnexion) throws SQLException {

		Connection connexion = DataBase.getMySQLConnection();

		String DateFin = DateTools.getFormatedDateAfterNHour(+1);

		PreparedStatement ps = connexion.prepareStatement(INSERT_SESSION_QUERY);

		ps.setString(1, key);
		ps.setInt(2, idUser);
		ps.setString(3, dateConnexion);
		ps.setString(4, DateFin);

		ps.executeUpdate();

		ps.close();
		connexion.close();

		return true;
	}

	/**
	 * verifie a ce que l'utilisateur idUser est deja connecte ou pas Mis a jour la
	 * date de fin de connexion pour sa session si non envoie false si la cle de
	 * connexion a expire
	 * 
	 * @param idUser int l'identifiant de l'utilisateur
	 * @return boolean true si la cle est presente dans la base de donne et elle est
	 *         pas expire false si non
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean estDejaConnecte(int idUser) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement pst = connexion.prepareStatement(GET_SESSION_FROM_ID_USER);
		pst.setInt(1, idUser);

		ResultSet rs = pst.executeQuery();

		boolean isConnect = verif_Date(rs);

		rs.close();
		pst.close();

		if (isConnect) {
			pst = connexion.prepareStatement(UPDATE_DATE_FIN_ID);
			pst.setString(1, DateTools.getFormatedDateAfterNHour(+1));
			pst.setInt(2, idUser);
			pst.executeUpdate();
			pst.close();
		}

		connexion.close();

		return isConnect;
	}

	/**
	 * verifie a ce que l'utilisateur qui a pour cle de conexion key est deja
	 * connecte ou pas Mis a jour la date de fin de connexion pour sa session si non
	 * envoie false si la cle de connexion a expire
	 * 
	 * @param key String la cle de la session
	 * @return boolean true si la cle est presente dans la base de donne et elle est
	 *         pas expire false si non
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean estDejaConnecte(String key) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();

		PreparedStatement pst = connexion.prepareStatement(GET_SESSION_FROM_KEY);
		pst.setString(1, key);

		ResultSet rs = pst.executeQuery();

		boolean isConnect = verif_Date(rs);

		rs.close();
		pst.close();

		if (isConnect) {
			pst = connexion.prepareStatement(UPDATE_DATE_FIN_KEY);
			pst.setString(1, DateTools.getFormatedDateAfterNHour(+1));
			pst.setString(2, key);
			pst.executeUpdate();
			pst.close();
		}

		connexion.close();

		return isConnect;
	}

	/**
	 * verification de la date
	 * 
	 * 
	 * 
	 * @param rs {@link ResultSet}
	 * @return {@link Boolean} true if dateFin > dateCourante
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	private static boolean verif_Date(ResultSet rs) throws SQLException {
		boolean isConnect = false;

		if (rs.next()) {
			java.util.Date date_fin = null;
			try {
				date_fin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Date_fin"));

			} catch (Exception e) {
				e.printStackTrace();
			}

			isConnect = date_fin.compareTo(new java.util.Date()) > 0;

		}
		return isConnect;
	}

	/**
	 * supprime la session qui a pour cle key si l'utilisateur veut se deconnecter
	 * et retourne true si non false si la session n'existe pas
	 * 
	 * 
	 * @param key String la cle de la session
	 * @return boolean true si la suppression de la session est reussie
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static boolean removeSession(String key) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();

		PreparedStatement s = connexion.prepareStatement(REMOVE_SESSION);
		s.setString(1, key);

		s.executeUpdate();

		s.close();
		connexion.close();
		return true;
	}

	/**
	 * prend une cle en parametre et renvoie l'identifiant de l'utilisateur
	 * 
	 * @param key String cle de la session
	 * @return int l'identifiant de l'utilisateur , -1 si il'exite pas une cle qui
	 *         correspond a key dans SESSIONDB
	 * @throws SQLException exception de connexion a la base de donnees
	 */
	public static int getIdUserOfKey(String key) throws SQLException {
		Connection connexion = DataBase.getMySQLConnection();
		PreparedStatement st = connexion.prepareStatement(GET_IDUSER_WITH_KEY);
		st.setString(1, key);

		ResultSet rs = st.executeQuery();
		int idUser = -1;
		if (rs.next()) {
			idUser = rs.getInt("id_user");
		}
		rs.close();
		st.close();
		connexion.close();

		return idUser;

	}

}
