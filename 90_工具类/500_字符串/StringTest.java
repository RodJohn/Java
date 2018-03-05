package com.john.manage.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringTest {
	
	@Test
	public void t21(){
		
	   
		
		/**
	     * StringUtils.isNotBlank(null)      = false
	     * StringUtils.isNotBlank("")        = false
	     * StringUtils.isNotBlank(" ")       = false
	     * StringUtils.isNotBlank("bob")     = true
	     * StringUtils.isNotBlank("  bob  ") = true
	     */
		
	    /**
	     * StringUtils.trim(null)          = null
	     * StringUtils.trim("")            = ""
	     * StringUtils.trim("     ")       = ""
	     * StringUtils.trim("abc")         = "abc"
	     * StringUtils.trim("    abc    ") = "abc"
	     */
		
		System.out.println(StringEscapeUtils.unescapeHtml3("</html>")); 
		
		System.out.println(CharSetUtils.count(  
                "The quick brown fox jumps over the lazy dog.", "aeiou")); 
		
        System.out.println("合并重复的字符.");  
        System.out.println(CharSetUtils.squeeze("a  bbbbbb     c dd", "b d")); 
        
        
        
	}

}
