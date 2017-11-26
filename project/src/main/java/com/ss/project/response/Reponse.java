package com.ss.project.response;

import java.util.List;

public class Reponse {

	private String resMsg;

	private String userId;

	private List<ErrorField> valErrors;

	public Reponse() {
		super();
	}

	public Reponse(String resMsg, String userId, List<ErrorField> valErrors) {
		super();
		this.resMsg = resMsg;
		this.userId = userId;
		this.valErrors = valErrors;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ErrorField> getValErrors() {
		return valErrors;
	}

	public void setValErrors(List<ErrorField> valErrors) {
		this.valErrors = valErrors;
	}

}
