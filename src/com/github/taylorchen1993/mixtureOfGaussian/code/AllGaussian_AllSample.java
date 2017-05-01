/**
 * 
 */
package com.github.taylorchen1993.mixtureOfGaussian.code;

import com.github.taylorchen1993.mixtureOfGaussian.Config;

/**
 * Ϊ�˸Ľ���˹���ģ�Ͷ��������
 * ����һ�Ŷ�ά��v(i,j)������i����˹���ģ�ͣ���j������
 * �μ���־������ѧϰP210��߱ʼ�
 * @author TaylorChen
 *
 */
public class AllGaussian_AllSample {
	/**
	 * ��j�������ڵ�i����˹���ģ���µĴ�Ȩ�صĸ���
	 */
	private double[][] weightedProbability=new double[Config.getCountOfGaussian()][Samples.getCount()];
	
	/**
	 * ��ÿһ�д�Ȩ�صĸ������
	 */
	private double[] everyColumnSumOfweightedProbability=new double[Samples.getCount()];
	
	/**
	 * ��j�������ڵ�i����˹���ģ���µĺ������
	 */
	private double[][] postProbability=new double[Config.getCountOfGaussian()][Samples.getCount()];

	/**
	 * ÿһ�к�����ʵĺ�
	 */
	private double[] rowSumOfPostProbability=new double[Config.getCountOfGaussian()];
	
	public double[][] getWeightedProbability() {
		return weightedProbability;
	}

	public void setWeightedProbability(double[][] weightedProbability) {
		this.weightedProbability = weightedProbability;
	}

	public double[] getEveryColumnSumOfweightedProbability() {
		return everyColumnSumOfweightedProbability;
	}

	public void setEveryColumnSumOfweightedProbability(double[] everyColumnSumOfweightedProbability) {
		this.everyColumnSumOfweightedProbability = everyColumnSumOfweightedProbability;
	}

	public double[][] getPostProbability() {
		return postProbability;
	}

	public void setPostProbability(double[][] postProbability) {
		this.postProbability = postProbability;
	}

	public AllGaussian_AllSample() {
		super();
	}

	public AllGaussian_AllSample(double[][] weightedProbability, double[] everyColumnSumOfweightedProbability,
			double[][] postProbability) {
		super();
		this.weightedProbability = weightedProbability;
		this.everyColumnSumOfweightedProbability = everyColumnSumOfweightedProbability;
		this.postProbability = postProbability;
	}
	public double[] getRowSumOfPostProbability() {
		return rowSumOfPostProbability;
	}

	public void setRowSumOfPostProbability(double[] rowSumOfPostProbability) {
		this.rowSumOfPostProbability = rowSumOfPostProbability;
	}

	//**************************************************************************��������***********
	public void setOneWeightedProbability(int oneGaussianNum,int oneSampleID,double oneWeightedProbability) {
		weightedProbability[oneGaussianNum][oneSampleID] = oneWeightedProbability;
	}

	public void setColumnSumOfweightedProbability() {
		for (int oneGaussianID = 0; oneGaussianID < Config.getCountOfGaussian(); oneGaussianID++) {
			for (int sampleID = 0; sampleID < Samples.getCount(); sampleID++) {
				//��i������˹ģ�ͣ���j������
				double cell=getWeightedProbability(oneGaussianID,sampleID);
				addToColumnSumOfWeightedProbability(sampleID,cell);
			}
		}
	}

	private void addToColumnSumOfWeightedProbability(int sampleID, double cell) {
		everyColumnSumOfweightedProbability[sampleID]+=cell;
	}

	private double getWeightedProbability(int oneGaussianID, int sampleID) {
		return weightedProbability[oneGaussianID][sampleID];
	}

	/**
	 * ��ÿһ����Ԫ�����������
	 */
	public void setAllCellOfPostProbability() {
		for (int oneGaussianID = 0; oneGaussianID < Config.getCountOfGaussian(); oneGaussianID++) {
			for (int sampleID = 0; sampleID < Samples.getCount(); sampleID++) {
				//��i������˹ģ�ͣ���j������
				double postProbability=getWeightedProbability(oneGaussianID, sampleID)/getEveryColumnSumOfweightedProbability(sampleID);
				setCellOfPostProbability(oneGaussianID,sampleID,postProbability);
			}
		}
	}

	private void setCellOfPostProbability(int oneGaussianID, int sampleID, double postProbability2) {
		postProbability[oneGaussianID][sampleID]=postProbability2;
	}

	private double getEveryColumnSumOfweightedProbability(int j) {
		return everyColumnSumOfweightedProbability[j];
	}

	public void setRowSumOfPostProbability() {
		for (int oneGaussianID = 0; oneGaussianID < Config.getCountOfGaussian(); oneGaussianID++) {
			for (int sampleID = 0; sampleID < Samples.getCount(); sampleID++) {
				double postProbability=getCellOfPostProbability(oneGaussianID,sampleID);
				addToRowSumOfPostProbability(oneGaussianID,postProbability);
			}			
		}
	}

	private void addToRowSumOfPostProbability(int i, double postProbability2) {
		rowSumOfPostProbability[i]+=postProbability2;
	}

	private double getCellOfPostProbability(int i, int j) {
		
		return postProbability[i][j];
	}

	public double getPostProbability(int oneGaussianID, int oneSampleID) {
		return postProbability[oneGaussianID][oneSampleID];
	}

	public double getRowSumOfPostProbability(int oneGaussianID) {
		return rowSumOfPostProbability[oneGaussianID];
	}
}
