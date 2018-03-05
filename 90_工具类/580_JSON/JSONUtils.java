package com.john.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
	
	
	private static ObjectMapper jacksonObjectMapper = new ObjectMapper();
	
	private final static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

	
	public static String toJSONString(Object obj) {
		try {
			return jacksonObjectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("对象序列化为json字符串失败", e);
			return null;
		}
	}

	public static File toJSONFile(Object obj, File outFile) {
		try {
			jacksonObjectMapper.writeValue(outFile, obj);
			return outFile;
		} catch (IOException e) {
			logger.error("对象序列化为文件" + outFile.getAbsolutePath() + "失败", e);
			return null;
		}
	}

	public static void toJSONFile(Object obj, OutputStream out) {
		try {
			jacksonObjectMapper.writeValue(out, obj);
		} catch (IOException e) {
			logger.error("对象序列化至输出流失败", e);
		}
	}

	public static <T> T fromObject(String content, TypeReference<T> typeReference) {
		try {
			return jacksonObjectMapper.readValue(content, typeReference);
		} catch (IOException e) {
			logger.error(" 反序列化json失败", e);
			return null;
		}
	}

	public static <T> T fromObject(String content, Class<T> tagcaz) {
		try {
			return jacksonObjectMapper.readValue(content, tagcaz);
		} catch (IOException e) {
			logger.error(" 反序列化json失败", e);
			return null;
		}
	}

	public static <T> T fromObject(Object obj, Class<T> tagcaz) {
		try {
			String content = toJSONString(obj);
			return jacksonObjectMapper.readValue(content, tagcaz);
		} catch (IOException e) {
			logger.error(" 反序列化json失败", e);
			return null;
		}
	}

	public static <T1, T2> Map<T1, T2> fromObject(String content) {
		try {
			return jacksonObjectMapper.readValue(content, new TypeReference<Map<T1, T2>>() {
			});
		} catch (IOException e) {
			logger.error(" 反序列化json为map失败", e);
			return null;
		}
	}

	public static <T> List<T> fromArray(String content) {
		try {
			return jacksonObjectMapper.readValue(content, new TypeReference<List<T>>() {
			});
		} catch (IOException e) {
			logger.error(" 反序列化json数组失败", e);
			return null;
		}
	}

	public static <T> T fromObject(File inputfile, Class<T> t) {
		try {
			return jacksonObjectMapper.readValue(inputfile, t);
		} catch (IOException e) {
			logger.error("读文件{}反序列化为{}类型失败", inputfile.getAbsoluteFile(), t.getName(), e);
			return null;
		}
	}

	public static Object fromObject(File inputfile) {
		return fromObject(inputfile, Object.class);
	}

	public static ArrayList<?> fromArray(File inputfile) {
		return fromObject(inputfile, ArrayList.class);
	}

	public static <T> T fromObject(InputStream inputStream, Class<T> t) {
		try {
			return jacksonObjectMapper.readValue(inputStream, t);
		} catch (IOException e) {
			logger.error("读文件输入域反序列化为" + t.getName() + "类型失败", e);
			return null;
		}
	}

	public static Object fromObject(InputStream inputStream) {
		return fromObject(inputStream, Object.class);
	}

	public static ArrayList<?> fromArray(InputStream inputStream) {
		return fromObject(inputStream, ArrayList.class);
	}

	public static <T> T fromObject(byte[] bytes, Class<T> tagcaz) {
		try {
			if (bytes == null)
				return null;
			return jacksonObjectMapper.readValue(bytes, 0, bytes.length, tagcaz);
		} catch (IOException e) {
			logger.error(" 反序列化json失败", e);
			return null;
		}
	}

	public static <T> T fromObject(byte[] bytes, TypeReference<T> ref) {
		try {
			if (bytes == null)
				return null;
			return jacksonObjectMapper.readValue(bytes, 0, bytes.length, ref);
		} catch (IOException e) {
			logger.error(" 反序列化json失败", e);
			return null;
		}
	}

	/*public static Object fromObject(String content, Type tagType) {
		try {
			if (content == null)
				return null;
			JavaType javaType = TypeFactory.defaultInstance().constructType(tagType);
			return jacksonObjectMapper.readValue(content, javaType);
		} catch (IOException e) {
			logger.error(" 反序列化json失败", e);
			return null;
		}
	}*/
}
