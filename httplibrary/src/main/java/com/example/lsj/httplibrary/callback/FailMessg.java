package com.example.lsj.httplibrary.callback;

/**   
 * 错误信息
* @Title: CallBack.java 
* @Package cn.dxb.app.base 
* @Description: TODO(用一句话描述该文件做什么) 
* @author xiaoluo 
* @date 2014年10月22日 下午3:32:08 
* @version V1.0   
*/
public class FailMessg{
	
	public static final int 	FIALPARSING			=1001;
	public static final String 	FIALPARSINGMSG 		="解析数据出错";
	public static final int 	FIALGETDATA			=1002;
	public static final String 	FIALGETDATAMSG  	="获取数据出错";
	public static final int 	FIALTIMEOUT	    	=1003;
	public static final String 	FIALTIMEOUTMSG  	="请求数据超时";
	public static final int 	FIALUNKNOnHOST		=1004;
	public static final String 	FIALUNKNOnHOSTMSG 	="连接异常";

	public FailMessg() {
		
	}
	public FailMessg(int failCode, String failString) {
		this.failCode = failCode;
		this.failString = failString;
	}
	public int failCode;
	public String failString;
	
	public int getFailCode() {
		return failCode;
	}
	public void setFailCode(int failCode) {
		this.failCode = failCode;
	}
	public String getFailString() {
		return failString;
	}
	public void setFailString(String failString) {
		this.failString = failString;
	}
}