package com.john.manage.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/* 提前注意
 * 		如果有不同时区的问题,先设置时区
 * 
 * 字符串<-->Date		simpleDateFormat     如果要时区,必须设置
 * 
 * 
 * 
 */







public class DateTest {

	
	SimpleDateFormat dateFormat = null;
	SimpleDateFormat dateFormat1 = null;
	
	
	@Before
	public void before(){
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	
	@Test
	public void dateFormat() throws Exception{
		
		Date parseDate = dateFormat.parse("2017-01-10 15:05:21 +0000");
		System.out.println(dateFormat.format(parseDate));//2017-01-10 23:05:21 +0800
		
		Date parseDate1 = dateFormat.parse("2017-01-10 15:05:21 +0800");
		System.out.println(dateFormat.format(parseDate1));//2017-01-10 15:05:21 +0800
		
		Date parseDate2 = dateFormat1.parse("2017-01-10 15:05:21");
		System.out.println(dateFormat1.format(parseDate2));//2017-01-10 15:05:21 
		
		
	}
	
	
	
	@Test
	public void offsetDate(){
			
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"));
		System.out.println(TimeZone.getTimeZone("GMT-8"));
		cal.add(Calendar.MONTH, -(new Random().nextInt(2)));
		cal.add(Calendar.DAY_OF_MONTH, -(new Random().nextInt(29)));
		
		cal.set(Calendar.HOUR_OF_DAY, (new Random().nextInt(14)+9));
		cal.set(Calendar.MINUTE, (new Random().nextInt(60)));
		cal.set(Calendar.SECOND, (new Random().nextInt(60)));
		
		
		//System.out.println(standard.format(new Date(cal.getTimeInMillis())));
	}
	
	
	
	@Test
	public void offsetDateZone(){
		
			//1454259661775  +8
			//1454263261219  +7
			TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
			Calendar cal = Calendar.getInstance(timeZone);
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 1);
			cal.set(Calendar.MINUTE, 1);
			cal.set(Calendar.SECOND, 1);
			
			System.out.println(cal.getTime());
		
	}
}
