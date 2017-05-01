package com.github.taylorchen1993.mixtureOfGaussian;

import com.github.taylorchen1993.mixtureOfGaussian.code.IterateImproveMixtureGaussian;
import com.github.taylorchen1993.mixtureOfGaussian.code.Samples;

/**
 * @author TaylorChen
 *
 */
public class Main {

	/**
	 * ������
	 * 
	 * @param args �����в���
	 */
	public static void main(String args[]) {
		Config.init();
		Samples.init();
		IterateImproveMixtureGaussian.init();
		IterateImproveMixtureGaussian.printResult();
	}
}
