package com.john.manage.collection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * 循环
 * 交集并集
 */

public class CollectionTest {
	
	
	List<String> listNull = null;
	List<String> listEmpty = new ArrayList<String>();
	
	Integer[] nums1Arr = {1,2,3};
	ArrayList<Integer> nums1 = new ArrayList<Integer>();
	ArrayList<Integer> nums2 = new ArrayList<Integer>();
	
	@Before
	public void before() throws Exception{

//		nums1 = Arrays.asList(nums1Arr);
		nums1.add(1);
		nums1.add(2);
		nums1.add(3);
		nums2.add(3);
		nums2.add(4);
		
		
	}
	
	
	@Test  //循环  判断非空就行
	public void xunhuan() throws Exception{
		for (String string : listEmpty) {
			System.out.println(string);
		}
	}
	
	
	@Test  //求并集
	public void unit() throws Exception{
		nums1.removeAll(nums2);
		nums1.addAll(nums2);
		System.out.println(nums1);
		//使用set
	}
	
	@Test  //求交集
	public void intersection() throws Exception{
		nums1.retainAll(nums2);
		System.out.println(nums1);
	}
	
	
	@Test  //求交集
	public void intersection() throws Exception{
		System.out.println("将数组中的内容以,分隔: "+ StringUtils.join(test, ","));
		
		
		System.out.println("输出数组中的数据: "+ArrayUtils.toString(test)); 
	}

	
	
	
	
	
}
