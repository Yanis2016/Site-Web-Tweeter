package com.twister.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONResponse extends JSONObject {

	private boolean isAccepted = false;
	private String motif;
	private int code;

	private JSONResponse(boolean isAccepted, String motif, int code) {
		this.isAccepted = isAccepted;
		this.motif = motif;
		this.code = code;
	}

	public static JSONResponse serviceRefused(String msg, int code) {
		JSONResponse jsr = new JSONResponse(false, msg, code);
		try {
			jsr.put(msg, code);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsr;
	}

	public static JSONResponse serviceAccepted() {
		JSONResponse jsr = new JSONResponse(true, "operation reussie", -1);
		try {
			jsr.put("operation reussie", -1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsr;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public String getMotif() {
		return motif;
	}

	public int getCode() {
		return code;
	}

}
