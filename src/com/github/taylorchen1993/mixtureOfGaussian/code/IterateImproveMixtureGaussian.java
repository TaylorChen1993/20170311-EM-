package com.github.taylorchen1993.mixtureOfGaussian.code;

import java.util.LinkedList;

import com.github.taylorchen1993.mixtureOfGaussian.Config;

public class IterateImproveMixtureGaussian {
	//******************************��Ա������getter/setter***********************************************************	
	/**
	 * �����Ľ������У����еĸ�˹ģ��
	 */
	private static LinkedList<MixtureGaussian> allMixtureGaussians = new LinkedList<MixtureGaussian>();
	
	
	


	public static LinkedList<MixtureGaussian> getAllMixtureGaussians() {
		return allMixtureGaussians;
	}


	public static void setAllMixtureGaussians(LinkedList<MixtureGaussian> allMixtureGaussians) {
		IterateImproveMixtureGaussian.allMixtureGaussians = allMixtureGaussians;
	}

	//******************************��������***********************************************************
	public static void init(){
		//�������ϸ�˹ģ��
		MixtureGaussian iterMixtureGaussian = MixtureGaussian.init();
		//TODO ��ʾģ��
		//iterMixtureGaussian.show();
		allMixtureGaussians.add(iterMixtureGaussian);
		for (int i = 0; i < Config.getMaxIterateTime(); i++) {
			iterMixtureGaussian=iterMixtureGaussian.getImprovedObj();
			//TODO ��ʾģ��
			//iterMixtureGaussian.show();
			allMixtureGaussians.add(iterMixtureGaussian);
		}
	}


	/**
	 * ������
	 */
	public static void printResult() {
		MixtureGaussian resultMixtrueGaussians=allMixtureGaussians.getLast();
		resultMixtrueGaussians.printResult();
		
	}
}
