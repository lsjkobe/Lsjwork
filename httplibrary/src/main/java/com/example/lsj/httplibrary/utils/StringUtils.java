package com.example.lsj.httplibrary.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @Title: StringUtils.java
* @Description: 时间处理
* @author xiaoluo
* @date 2014年10月21日 下午4:03:43
* @version V1.0
*/
public class StringUtils {
	/**
	 * 获得系统时间，字符串
	 * 
	 * @return
	 */
	public static String getTime_MMdd_HHmm() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		String dataString = mDateFormat.format(new Date(System
				.currentTimeMillis()));
		return dataString;
	}
	/**
	 * 获得系统时间，字符串
	 * 
	 * @return
	 */
	public static String getTime_MMddHHmm() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MMddHHmm");
		String dataString = mDateFormat.format(new Date(System.currentTimeMillis()));
		return dataString;
	}
	/**
	 * 获得系统时间，字符串
	 * 
	 * @return
	 */
	public static String getTime_yyyy_MM_dd() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dataString = mDateFormat.format(new Date(System.currentTimeMillis()));
		return dataString;
	}
	public static String getTime_yyyy_MM_dd_HHmm() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dataString = mDateFormat.format(new Date(System.currentTimeMillis()));
		return dataString;
	}
	/**
	 * 获得系统时间，年份
	 * 
	 * @return 年份
	 */
	public static int getIntYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 获得系统时间，月份
	 * 
	 * @return 月份
	 */
	public static int getIntMonth() {
		Calendar cal = Calendar.getInstance();
		//Calendar.MONTH从0开始，国人习惯应加1
		int month = cal.get(Calendar.MONTH)+1;
		return month;
	}
		
	/**
	 * 时间
	 * 格式：2012-12-12 12:12
	 * @return
	 */
	private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);
		return sbBuffer.toString();
	}
	
	
	/**
	 * 判断电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
	    return Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$").matcher(email).matches();
	}
	
	 /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles){
     boolean flag = false;
     try{
	      Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	      Matcher m = p.matcher(mobiles);
	      flag = m.matches();
     }catch(Exception e){
         flag = false;
     }
     return flag;
    }
    
    /**
	 * 判断登录名类型
	 * @param loginName 登录名
	 * @return	是否是用户名登录(另外的是邮箱或手机号登录)
	 */
	public static boolean isUserName(String loginName){
		if(isEmail(loginName) || isMobileNum(loginName)){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 随机数
	 * @param i	
	 * @return	0~i(不包含i)
	 */
	public static int getRandomNum(int i){
		return new Random().nextInt(i);
	}
	
	
	/**
	 * 将string[]转为List集合
	 * 
	 * @param str
	 * @return List
	 */
	public static List<String> stringToList(String str[]) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);

		}
		return list;

	}
	
	/**
	 * 过滤指定String中指定的字符串
	 * @param initialStr 原来的字符串
	 * @param str 想要去除的字符串
	 * @return
	 */
	public static String stringToFilter(String initialStr, String str){
		//假设你想过滤 str  
		Pattern p = Pattern.compile(str);
		Matcher m =p.matcher(initialStr);
		return m.replaceAll("");
	}
	
	/**
	 * 统计特定字符出现的次数
	 * @param str 原字符串
	 * @param key 需要统计的字符串
	 * @return 统计次数
	 */
	public static int getSubCount(String str,String key){
	    int count = 0;
	    int index = 0;
	    while ((index=str.indexOf(key,index))!=-1){
	        str = str.substring(index+key.length());
	        count++;
	    }
	    return count;
	}

	/**
	 * 回车替换
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceEnter(String value) {
		return value.replaceAll("[\\r\\n]", "<br/>");
	}

	/**
	 * 回车还原
	 * 
	 * @param value
	 * @return
	 */
	public static String oldEnter(String value) {
		return value.replaceAll("<br/>", "\n");
	}
	
	/**
	 * 空格替换
	 * 
	 */
	public static String replaceBlank(String value){
		return value.replaceAll("[\\r\\n]", "nbsp;");
	}
	
	/**
	 * 空格还原
	 * 
	 */
	public static String oldBlank(String value){
		return value.replace("nbsp;", "\n");
	}
	
	
	/**
	 * 反斜杆替换
	 */
	public static String replaceDiagonalRod(String value){
		return value.replaceAll("\\", "%32%");
	}
	
	/**
	 * 反斜杆还原
	 */
	public static String oldDiagonalRod(String value){
		return value.replace("%32%", "\\");
	}
}
