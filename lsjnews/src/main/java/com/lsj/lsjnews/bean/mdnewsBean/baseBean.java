package com.lsj.lsjnews.bean.mdnewsBean;

import java.io.Serializable;

public class baseBean implements Serializable {

	private static final long serialVersionUID = 8872590260474746706L;

	public final static int RESPONE_ERROR = 404; 
	
	private int resultCode;
	
	private String resultContent;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
}
