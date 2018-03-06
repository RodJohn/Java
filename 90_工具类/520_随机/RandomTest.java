/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.john.manage.random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class RandomTest {
	
	
	
	
	@Test
	public void t21(){
		
		
		//使用指定的字符生成5位长度的随机字符串
		System.out.println(RandomStringUtils.random(5, new char[]{'a','b','c','d','e','f', '1', '2', '3'}));
		
		//生成指定长度的字母和数字的随机组合字符串
		System.out.println(RandomStringUtils.randomAlphanumeric(5));
	}
	
	
	@Test
	public void t22(){
		System.out.println(RandomUtils.nextInt(0,1000));
		//在0~1000之间产生一位随机数
		
		//此外还有nextBytes/Double/Float/Long等
		System.out.println(RandomUtils.nextFloat(1.1f,2.3f));
	}
	

}
