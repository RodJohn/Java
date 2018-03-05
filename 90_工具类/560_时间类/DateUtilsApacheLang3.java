package com.john.manage.date;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

/* 提前注意
 * 		如果有不同时区的问题,先设置时区
 * 
 * 字符串<-->Date		simpleDateFormat     如果要时区,必须设置
 * 
 * 
 * 
 */







public class DateUtilsApacheLang3 {

	
	@Test
	public void dateFormat() throws Exception{
		
		//时间格式化
		System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		System.out.println(DateFormatUtils.format(new Date().getTime(), "yyyy-MM-dd HH:mm:ss:SSS"));
		
		
		//时间偏移
		Date addDays = DateUtils.addDays(new Date(), -10);
		System.out.println(DateFormatUtils.format(addDays, "yyyy-MM-dd HH:mm:ss:SSS"));
	}

}
