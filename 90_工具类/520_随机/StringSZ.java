package com.john.manage.random;

import org.junit.Test;

public class StringSZ {
	

	
	
	@Test
	public void testEmailRegex() {
		String[] gradeArray = getGradeArray(0.46,3,0.54,4);
		for (String string : gradeArray) {
			System.out.println(string);
		}
	}
	
	
	/**
	 * author： wujirui date: 2015-06-11 随机生成一个3到5的评论分
	 * 数组中包含五个元素，最后一个元素作为“综合评级（foverallrating）”，是前四个的平均值； 前四个分别为：1.iprice（价格评级）
	 * 2.质量评级（iquality） 3.物流评级（ishipping） 4.有用评级（iusefulness）；
	 * 
	 * @return arry
	 */
	public String[] getGradeArray(double rate0, int result0, double rate1,int result1) {
		String[] arry = new String[5];
		int sum = 0;
		int random = 0;
		double avg = 0.0;
		for (int i = 0; i < arry.length - 1; i++) {
			random = percentageRandom(rate0, result0, rate1, result1);// 获取一个3---4的随机数
			arry[i] = String.valueOf(random);
			sum += random;
		}
		avg = sum * 1.0 / (arry.length - 1);// 计算平均值；
		arry[arry.length - 1] = String.valueOf(avg);// 将平均值放到数组的最末位
		return arry;
	}

	/**
	 * 随机生产两个数
	 * 
	 * @param rate0
	 *            第一个数出现的百分比
	 * @param result0
	 *            需要返回的第一个数
	 * @param rate1
	 *            第二个数出现的百分比
	 * @param result1
	 *            需要返回的第二个数
	 * @return
	 */
	private static int percentageRandom(double rate0, int result0,
			double rate1, int result1) {
		double randomNumber = Math.random();
		if (randomNumber >= 0 && randomNumber <= rate0) {
			return result0;
		} else if (randomNumber >= rate0 && randomNumber <= rate0 + rate1) {
			return result1;
		}
		return result1;
	}
}
