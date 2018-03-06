package com.john.manage.regex;

import org.junit.Test;

public class StringZZ {
	
	/* java正则规律
	 * 
	 * 
	 * 逻辑
	 * 		()				每一个单独的表达式最好都要使用
	 * 		或者				|
	 * 
	 * 
	 * 数据
	 * 		范围数据			[a-z0-9A-Z]
	 * 		表示任意    			.
	 * 		表示小数点    		\\.
	 * 
	 * 次数
	 * 		0和0次以上			*
	 * 		1和1次以上			+
	 * 		0次和1次			?
	 * 		{2}				2次
	 * 		{2,}			2次以上
	 * 		{2,4}			2次到4次包括
	 * 	
	 */

	public static void main(String[] args) {
	/*	String url1 = "/sports-watch-990/p-j1057sw.html?aid=fbj1057rmkt";
		String url2 = "/index.php?r=join/login";
		String url3 = "/boat-toys-1177/p-rm5618gr-eu.html";
		String url4 = "/activity/20160926-Product-activity-en.html?aid=umiplusmagait";
		String regx = ".*\\-[0-9]+/p\\-[a-zA-Z0-9\\-]+\\.html.*";
		System.out.println(url1.matches(regx)+"   "+url1);
		System.out.println(url2.matches(regx)+"   "+url2);
		System.out.println(url3.matches(regx)+"   "+url3);
		System.out.println(url4.matches(regx)+"   "+url4);*/
		
	}
	
	
	@Test
	public void testEmailRegex() {
		String email1 = "lijunge@163ds.com";
		String email2 = "qwe@63dscom";
		String regxemail = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";
		System.out.println(email1.matches(regxemail)+"   "+email1);
		System.out.println(email2.matches(regxemail)+"   "+email2);
		
		System.out.println("'3".replaceAll("\\'", "\'"));
		System.out.println("'3".replaceAll("\\'", "\\'"));
		System.out.println("'3".replaceAll("\\'", "\\\\'"));
	}
	
	
	@Test
	public void testReplace() {
		System.out.println("www.tomtop.com".replaceFirst("www", "it"));
	}
	
	@Test
	public void testHttp() {
		String email1 = "https://www.amazon.com/gp/customer-rev";
		String regxemail = "https://www.amazon.com.+";
		System.out.println(email1.matches(regxemail)+"   "+email1);
	}
}
