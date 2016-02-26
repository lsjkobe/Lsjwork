package com.example.lsj.httplibrary.callback;

import java.io.Serializable;

public class ApiResponse implements Serializable {

	private static final long serialVersionUID = 8872590260474746706L;

	public final static int RESPONE_ERROR = 404; 
	
	private int result;
	
	private String content;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getResult() {
		return result;
	}
	
	public void setResult(int result) {
		this.result = result;
	}

}
