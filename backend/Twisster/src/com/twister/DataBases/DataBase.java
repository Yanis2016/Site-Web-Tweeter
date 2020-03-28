package com.twister.DataBases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBase {
	private DataSource dataSource;

	public DataBase(String jndiname) throws SQLException {
		try {
			dataSource =(DataSource) new InitialContext().lookup("java:comp/env/"+jndiname);
			System.out.println("\n\n\npas d'erreur");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SQLException(jndiname + " is missing in JNDI! : " + e.getMessage());
		}
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static Connection getMySQLConnection() throws SQLException {
		if (DBStatic.MYSQL_POOLING == false) {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			return (DriverManager.getConnection("jdbc:mysql://" + DBStatic.HOST + "/" + DBStatic.DB_NAME+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",DBStatic.USERNAME, DBStatic.PASSWORD));
		} else {
			return new DataBase("jdbc/pool").getConnection();
		}

	}
}
