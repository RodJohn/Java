package com.john.manage.number;

import org.junit.Test;

public class NumberTest {
	@Test
	public void t21(){
		
        System.out.println("从数组中选出最大值.");
        System.out.println(NumberUtils.max(new int[] { 1, 2, 3, 4 }));  
  
        System.out.println("判断字符串是否全是整数.");  
        System.out.println(NumberUtils.isDigits("123.1"));  
  
        System.out.println("判断字符串是否是有效数字.");  
        System.out.println(NumberUtils.isNumber("0123.1"));  
	}
}
