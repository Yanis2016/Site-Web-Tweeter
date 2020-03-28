package com.twister.DataBases;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

	private static MongoClient mc;

	/**
	 * une nouvelle connexion pour mongo 
	 * 
	 * @return {@link MongoDatabase} une nouvelle connexin pour la base mongo 
	 */
	public static MongoCollection<Document> getConnectionToMongoDataBase() {
		mc = MongoClients.create(DBStatic.MONGO_HOST);
		MongoDatabase db = mc.getDatabase(DBStatic.MONGO_DB);
		MongoCollection<org.bson.Document> col = db.getCollection("comments");
		return col;
	}

	public static void closeConnection() {
		mc.close();
	}

}
