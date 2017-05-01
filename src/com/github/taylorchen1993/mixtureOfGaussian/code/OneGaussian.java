package com.github.taylorchen1993.mixtureOfGaussian.code;

import com.github.taylorchen1993.mixtureOfGaussian.Config;

import Jama.Matrix;

/**
 * 
 */

/**
 * @author TaylorChen
 *
 */
/**
 * @author TaylorChen
 *
 */
public class OneGaussian {
	/**
	 * �ڻ�ϸ�˹ģ�������Ȩ��
	 */
	private Double weight ;
	/**
	 * ��ֵ��2*1�ľ�������(x-mean)
	 */
	private Matrix mean=new Matrix(Config.getDimension(),1);
	/**
	 * Э�������2*2�ľ���
	 */
	private Matrix covarianceMatrix=new Matrix(Config.getDimension(), Config.getDimension());


	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Matrix getMean() {
		return mean;
	}

	public void setMean(Matrix mean) {
		this.mean = mean;
	}

	public Matrix getCovarianceMatrix() {
		return covarianceMatrix;
	}

	public void setCovarianceMatrix(Matrix covarianceMatrix) {
		this.covarianceMatrix = covarianceMatrix;
	}
	

	public OneGaussian() {
		super();
	}

	public OneGaussian(Double weight, Matrix mean, Matrix covariance) {
		super();
		this.weight = weight;
		this.mean = mean;
		this.covarianceMatrix = covariance;
	}

	//*******************************��������*********************************************************
	/**
	 * һ�����ڱ���˹ģ���еĸ���*����˹ģ�͵�Ȩ��
	 * @param point n*1�����ʾһ����
	 */
	public double getWeightedProbability(Matrix point) {
		return weight*getProbability(point);
	}
	
	/**
	 * һ�����ڱ���˹ģ���еĸ���
	 * @param point n*1�����ʾһ����
	 */
	private double getProbability(Matrix point) {
		//��ĸ�е�ϵ��
		double down=Math.pow((2*Math.PI),Config.getDimension()/2.0)
				*Math.pow(covarianceMatrix.det(), 0.5);
		Matrix pointMinusMean=point.minus(mean);
		//[].()������������
		double index=-0.5*pointMinusMean.transpose().times(this.getCovarianceMatrix().inverse()).times(pointMinusMean).det();
		return Math.exp(index)/down;
	}


	public OneGaussian getImprovedOneGaussian(int oneGaussianID,AllGaussian_AllSample allGaussian_AllSampleTable) {
		Matrix improvedMean=getImprovedMean(oneGaussianID, allGaussian_AllSampleTable);
		Matrix improvedCovariance=getImprovedCovarianceMatrix(oneGaussianID,improvedMean, allGaussian_AllSampleTable);
		double improvedWeight=getImprovedWeight(oneGaussianID,allGaussian_AllSampleTable);
		OneGaussian improvedOneGaussian=new OneGaussian(improvedWeight, improvedMean, improvedCovariance);
		return improvedOneGaussian;
	}



	private double getImprovedWeight(int oneGaussianID, AllGaussian_AllSample allGaussian_AllSampleTable) {
		double improvedWeight=allGaussian_AllSampleTable.getRowSumOfPostProbability(oneGaussianID)/Samples.getCount();
		return improvedWeight;
	}


	private Matrix getImprovedCovarianceMatrix(int oneGaussianID, Matrix improvedMean,AllGaussian_AllSample allGaussian_AllSampleTable) {
		Matrix upperSum=new Matrix(Config.getDimension(),Config.getDimension());
		for (int oneSampleID = 0; oneSampleID < Samples.getCount(); oneSampleID++) {
			//��һ�г������Ӧ�ü�ȥimprovedMean�����������ڵ�mean
			//Matrix sampleMinusMean=Samples.getNthSample(oneSampleID).minus(mean);
			Matrix sampleMinusImprovedMean=Samples.getNthSample(oneSampleID).minus(improvedMean);
			Matrix upperOne=sampleMinusImprovedMean.times(sampleMinusImprovedMean.transpose()).times(allGaussian_AllSampleTable.getPostProbability(oneGaussianID, oneSampleID));
			upperSum.plusEquals(upperOne);
		}
		return upperSum.times(1.0/allGaussian_AllSampleTable.getRowSumOfPostProbability(oneGaussianID));
	}


	private Matrix getImprovedMean(int oneGaussianID,AllGaussian_AllSample allGaussian_AllSampleTable) {
		Matrix upperSum=new Matrix(Config.getDimension(), 1);
		for (int sampleID = 0; sampleID < Samples.getCount(); sampleID++) {
			Matrix one=Samples.getNthSample(sampleID).times(allGaussian_AllSampleTable.getPostProbability(oneGaussianID,sampleID));
			upperSum.plusEquals(one);			
		}
		return upperSum.times(1.0/allGaussian_AllSampleTable.getRowSumOfPostProbability(oneGaussianID));
	}




	/**
	 * ������
	 */
	public void printResult() {
		System.out.println("����һ������˹ģ�͵Ĳ�������");
		System.out.println("Ȩ�أ�"+weight.toString());
		System.out.println("��ֵ:");
		System.out.println(Double.valueOf(mean.get(0, 0)).toString());
		System.out.println(Double.valueOf(mean.get(1, 0)).toString());
		//����
		System.out.println("Э�������:");
		System.out.println(Double.valueOf(covarianceMatrix.get(0, 0)).toString()+"\t"+Double.valueOf(covarianceMatrix.get(0, 1)).toString());
		System.out.println(Double.valueOf(covarianceMatrix.get(1, 0)).toString()+"\t"+Double.valueOf(covarianceMatrix.get(1, 1)).toString());
		System.out.println("");
	}

	public void setEverySampleWeightedProbabilityInTable(int oneGaussianID,
			AllGaussian_AllSample allGaussian_AllSampleTable) {
		for (int sampleID = 0; sampleID < Samples.getCount(); sampleID++) {
			Matrix sample=Samples.getNthSample(sampleID);
 			double weightedProbability=getWeightedProbability(sample);
			allGaussian_AllSampleTable.setOneWeightedProbability(oneGaussianID, sampleID, weightedProbability);
			
		}
		
	}


}
