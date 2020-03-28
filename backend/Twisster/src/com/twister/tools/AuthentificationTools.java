package com.twister.tools;

import java.sql.SQLException;

import com.twister.DataBases.USER_DB;

public class AuthentificationTools {

	public static boolean userExists(String login) {
		try {
			return USER_DB.exists(login);
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public static boolean checkPassword(String login, String password) {
		try {
			return USER_DB.checkPassword(login, password);
		} catch (SQLException e) {
			return false;
		}
	}

	public static int getIdUser(String login) {
		try {
			return USER_DB.getId(login);
		} catch (SQLException e) {
			return -1;
		}
	}
}
