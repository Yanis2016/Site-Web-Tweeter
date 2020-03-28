package com.twister.DataBases;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.twister.tools.DateTools;

/**
 * @author root
 *
 *         Data Base of comments like :
 *         {"_id":idOfMongo,"id":idcomment,"author_id":idUser,"nom":nom,"prenom",prenom,"date",dateDeCreation,"comment":le
 *         commentaire}
 *
 *
 */
public class COMMMENT_DB {

	private static int id = getLastId() + 1;

	/**
	 * Affiche la base de donne comment
	 * 
	 */
	public static void printMongoDB() {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		MongoCursor<Document> mCursur = col.find().iterator();

		try {
			while (mCursur.hasNext()) {
				Document document = (Document) mCursur.next();
				JSONObject jse = new JSONObject();
				jse.put("id", document.getInteger("id"));
				jse.put("author_id", document.getInteger("author_id"));
				jse.put("nom", document.getString("nom"));
				jse.put("prenom", document.getString("prenom"));
				jse.put("date", document.getDate("date"));
				jse.put("comment", document.getString("comment"));
				System.out.println(jse);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MongoDB.closeConnection();
	}

	/**
	 * Ajout un commentaire dans la base de donnee de mongo Cree un message de cette
	 * façoon
	 * {"id":idcomment,"author_id":idUser,"nom":nom,"prenom",prenom,"date",dateDeCreation,"comment":le
	 * commentaire}
	 * 
	 * 
	 * @param idUser  Integer l'identifiant de l'utilisateur
	 * @param nom     String le nom de l'utilisateur
	 * @param prenom  String le prenom de l'utilisateur
	 * @param comment String le contenu de message
	 * @return boolean true si l'operation est bien passe false si non
	 */
	public static boolean addComment(int idUser, String nom, String prenom, String comment) {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		org.bson.Document doc = new org.bson.Document();

		doc.append("id", id++);
		doc.append("author_id", idUser);
		doc.append("nom", nom);
		doc.append("prenom", prenom);
		doc.append("date", DateTools.getDateAfterNHour(0));
		doc.append("comment", comment);

		col.insertOne(doc);

		MongoDB.closeConnection();

		return true;
	}

	/**
	 * Supprime un commentaire en donnant son identifiant et l'identifiant de
	 * l'utilisateur retourne false si iduser != id_author de comentaire
	 * 
	 * @param id_user   Integer l'identifiant de l'utilisateur qui veux supprmier le
	 *                  commentaire
	 * @param idComment Integer l'identifiant de commentaire
	 * @return boolean true si l'operation est reussie false si non
	 */
	public static boolean removeComment(int id_user, int idComment) {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		org.bson.Document d = new Document();
		d.append("id", idComment);
		try {
			if (col.find(d).first().getInteger("author_id") == id_user) {
				col.deleteMany(d);
				return true;
			}

		} finally {
			MongoDB.closeConnection();
		}

		return false;
	}

	public static List<JSONObject> getUserCommentsId_Author(int id_author) throws JSONException {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		org.bson.Document doc = new org.bson.Document();

		doc.append("author_id", id_author);
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		MongoCursor<Document> mngc = col.find(doc).iterator();

		while (mngc.hasNext()) {
			Document document = (Document) mngc.next();
			JSONObject jse = new JSONObject();
			jse.put("id", document.getInteger("id"));
			jse.put("author_id", document.getInteger("author_id"));
			jse.put("nom", document.getString("nom"));
			jse.put("prenom", document.getString("prenom"));
			jse.put("date", document.getDate("date"));
			jse.put("comment", document.getString("comment"));

			listJson.add(jse);
		}

		MongoDB.closeConnection();
		return listJson;
	}

	/**
	 * Lister les message de l'utilisateur par son nom ou prenom
	 * 
	 * @param name String le nom de l'utilisateur
	 * @return List<JSONObject> qui est une liste des json tel que chaque jason
	 *         contient un commentaire
	 * @throws JSONException exception de JSON
	 */
	public static List<JSONObject> getUserComments_NOM(String name) throws JSONException {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		org.bson.Document doc = new org.bson.Document();
		doc.append("nom", name);
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		MongoCursor<Document> mngc = col.find(doc).iterator();
		while (mngc.hasNext()) {
			Document document = (Document) mngc.next();
			listJson.add(new JSONObject(document.toJson()));
		}
		org.bson.Document docprenom = new org.bson.Document();
		doc.append("prenom", name);
		MongoCursor<Document> mng = col.find(docprenom).iterator();
		while (mng.hasNext()) {
			Document document = (Document) mng.next();
			JSONObject jse = new JSONObject();
			jse.put("id", document.getInteger("id"));
			jse.put("author_id", document.getInteger("author_id"));
			jse.put("nom", document.getString("nom"));
			jse.put("prenom", document.getString("prenom"));
			jse.put("date", document.getDate("date"));
			jse.put("comment", document.getString("comment"));

			listJson.add(jse);
		}

		MongoDB.closeConnection();
		return listJson;
	}

	public static List<JSONObject> getCommentForLastNHour(int n) {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		Document doc = new Document();

		doc.append("date", new Document("$gt", DateTools.getDateAfterNHour(-1 * n)));

		List<JSONObject> listJson = new ArrayList<JSONObject>();

		MongoCursor<Document> cursor = col.find(doc).iterator();
		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				JSONObject jse = new JSONObject();
				jse.put("id", document.getInteger("id"));
				jse.put("author_id", document.getInteger("author_id"));
				jse.put("nom", document.getString("nom"));
				jse.put("prenom", document.getString("prenom"));
				jse.put("date", document.getDate("date"));
				jse.put("comment", document.getString("comment"));
				listJson.add(jse);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			MongoDB.closeConnection();
		}

		return listJson;
	}

	/**
	 * retourne le dernier id de la base pour differencier les commentaires
	 * 
	 * @return Integer le dernier id fournis
	 */
	private static int getLastId() {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();

		Document doc = new Document("id", -1);

		Document d = col.find().sort(doc).limit(1).first();

		int max_id = d == null ? 0 : d.getInteger("id");

		MongoDB.closeConnection();
		return max_id;
	}

	public static void clearAllComment() {
		MongoCollection<org.bson.Document> col = MongoDB.getConnectionToMongoDataBase();
		col.drop();
		MongoDB.closeConnection();
	}
}
