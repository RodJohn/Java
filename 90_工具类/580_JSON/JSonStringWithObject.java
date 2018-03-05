
package com.john.manage.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 1.JSON字符串-->标准对象        	       ObjectMapper.readValue(studentSimStr, Student.class)
 * 1.JSON字符串-->简单对象(推荐)        ObjectMapper.readTree(studentSimStr)
 * 
 * 2.标准对象-->JSON字符串        		   MAPPER.writeValueAsString(studentSim)
 * 2.简单对象-->JSON字符串(推荐)        new JSONObject().put() 
 */
public class JSonStringWithObject {
	
	
	private ObjectMapper MAPPER = new ObjectMapper();
	private String studentMulStr = "" ;
	private String studentSimStr = "" ;
	private Student studentMul = new Student();
	private Student studentSim = new Student();
	private List<String> nickNames = new ArrayList<String>() ;
	
	
	@Before
	public void before() throws Exception{
		studentSim.setId(1);
		studentSim.setName("lijun");
		studentSimStr = "{\"name\":\"Mahesh\", \"id\":21}";
		studentMul.setId(1);
		studentMul.setName("lijun");
		nickNames.add("狗蛋");
		nickNames.add("猫蛋");
		studentMul.setNickNames(nickNames);
		studentMulStr = "{\"name\":\"Mahesh\", \"id\":21,\"nickNames\":[\"狗蛋\",\"猫蛋\"]}";
		
	}
	
	@Test
	public void pojoToJsonSimple() throws Exception{
		Student readValue = MAPPER.readValue(studentSimStr, Student.class);
		System.out.println(readValue);
	}

	
	@Test
	public void stringToPojoSimple() throws Exception{
		String str = MAPPER.writeValueAsString(studentSim);
		System.out.println(str);
	}
	
	
	@Test
	public void pojoToJsonMul() throws Exception{
		Student readValue = MAPPER.readValue(studentMulStr, Student.class);
		System.out.println(readValue);
	}
	
	
	@Test
	public void stringToPojoMul() throws Exception{
		String str = MAPPER.writeValueAsString(studentMul);
		System.out.println(str);
	}


		
	public class Student {
		
		
		private Integer id ;
		private String name ;
		
		private List<String> nickNames;
		
		
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public List<String> getNickNames() {
			return nickNames;
		}
		public void setNickNames(List<String> nickNames) {
			this.nickNames = nickNames;
		}
		@Override
		public String toString() {
			return "Student [id=" + id + ", name=" + name + ", nickNames=" + nickNames + "]";
		}
			
	}

	
}
