package com.twister.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test2 {
	
	public static void main(String args []) {
		try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
        }
		
		Connection connexion = null;
		try {

            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstDataBase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "java", "Tapez_yanis_");

        } catch (SQLException e) {

            e.printStackTrace();

        }
		
		 Statement statement = null;
		 ResultSet resultat = null;
		 
		 try {
	            statement = connexion.createStatement();

	            // Exécution de la requête
	            resultat = statement.executeQuery("SELECT * FROM USER;");

	            // Récupération des données
	            while (resultat.next()) {
	                String nom = resultat.getString("NOM");
	                String prenom = resultat.getString("PRENOM");
	                System.out.println("NOM :" + nom + ", PRENOM :" + prenom);
	            }
	        } catch (SQLException e) {
	        } finally {
	            // Fermeture de la connexion
	            try {
	                if (resultat != null)
	                    resultat.close();
	                if (statement != null)
	                    statement.close();
	                if (connexion != null)
	                    connexion.close();
	            } catch (SQLException ignore) {
	            }
	        }
	}
}


