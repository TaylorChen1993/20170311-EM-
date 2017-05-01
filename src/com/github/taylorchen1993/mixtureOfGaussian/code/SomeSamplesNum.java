/**
 * 
 */
package com.github.taylorchen1993.mixtureOfGaussian.code;

import java.util.HashSet;
import java.util.Iterator;

import com.github.taylorchen1993.mixtureOfGaussian.Config;

/**
 * @author TaylorChen
 *
 */
/**
 * �˴��ü̳�Ҫ��һЩ������������
 * @author TaylorChen
 * 
 */
public class SomeSamplesNum{
	/**
	 * ���ڳ�ʼ����ϸ�˹ģ��ʱ������ÿ������˹ģ�͵ľ�ֵʱ���õ��������
	 */
	private static HashSet<Integer> someSamplesNum = new HashSet<>();

	public static HashSet<Integer> getSomeSampleNum() {
		return someSamplesNum;
	}

	public static void setSomeSampleNum(HashSet<Integer> someSampleNum) {
		SomeSamplesNum.someSamplesNum = someSampleNum;
	}

	public static Iterator<Integer> iterator() {
		return someSamplesNum.iterator();
	}

	// *********************************init********************************************************
	/**
	 * ���Samples��һ���ֳ���
	 */
	public static void init() {
		Integer randomSampleNum=0;
		while (someSamplesNum.size()<Config.getCountOfGaussian()) {
			Double temp=Math.random()*Samples.getCount();
			randomSampleNum=temp.intValue();
			someSamplesNum.add(randomSampleNum);
		}
	}

}
