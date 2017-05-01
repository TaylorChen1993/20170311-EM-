/**
 * 
 */
package com.github.taylorchen1993.mixtureOfGaussian.code;

import java.util.Iterator;
import java.util.LinkedList;

import org.jzy3d.plot3d.builder.Mapper;

import com.github.taylorchen1993.mixtureOfGaussian.Config;

import Jama.Matrix;

/**
 * @author TaylorChen
 *
 */
public class MixtureGaussian {
	/**
	 * ��ϸ�˹ģ��
	 */
	private LinkedList<OneGaussian> mixtureGaussian = new LinkedList<>();

	/**
	 * ͼ��
	 */
	private Plot3D plot3d;

	private AllGaussian_AllSample allGaussian_AllSampleTable=new AllGaussian_AllSample();


	public LinkedList<OneGaussian> getMixtureGaussian() {
		return mixtureGaussian;
	}

	public void setMixtureGaussian(LinkedList<OneGaussian> mixtureGaussian) {
		this.mixtureGaussian = mixtureGaussian;
	}

	public Plot3D getPlot3d() {
		return plot3d;
	}

	public void setPlot3d(Plot3D plot3d) {
		this.plot3d = plot3d;
	}

	public MixtureGaussian() {
		super();
	}

	public MixtureGaussian(LinkedList<OneGaussian> mixtureGaussian, Plot3D plot3d) {
		super();
		this.mixtureGaussian = mixtureGaussian;
		this.plot3d = plot3d;
	}

	public Iterator<OneGaussian> iterator() {
		return mixtureGaussian.iterator();
	}

	public AllGaussian_AllSample getAllGaussian_AllSampleTable() {
		return allGaussian_AllSampleTable;
	}


	// ****************************��������*************************************************************
	/**
	 * ����SomeSamplesNum,���ɵ�һ����ϸ�˹ģ��
	 */
	public static MixtureGaussian init() {
		MixtureGaussian initModel = new MixtureGaussian();
		//  ��ʼ��ʱ�ĵ���˹ģ�͵ľ�ֵ�����������ѡȡ
		SomeSamplesNum.init();
		double weight = 1.0 / Config.getCountOfGaussian();
		Matrix mean;
		Matrix covarianceMatrix = Samples.getCovarianceMatrix();
		OneGaussian tempOneGaussian=new OneGaussian();
		for (Iterator<Integer> iterator = SomeSamplesNum.iterator(); iterator.hasNext();) {
			Integer oneSampleNum = (Integer) iterator.next();
			mean = Samples.getNthSample(oneSampleNum);
			tempOneGaussian = new OneGaussian(weight, mean, covarianceMatrix);
			initModel.add(tempOneGaussian);
		}
		return initModel;
	}

	/**
	 * ��ӵ���˹ģ��ģ��
	 * @param tempOneGaussian
	 */
	private void add(OneGaussian tempOneGaussian) {
		mixtureGaussian.add(tempOneGaussian);
	}
	/**
	 * ��ͼ������ʾ��˹ģ��
	 */
	public void show() {
		setPlot3d();
		plot3d.show();	
	}
	/**
	 * ���ݸ�˹���ģ�ͣ�new Plot3D
	 */
	private void setPlot3d() {
		Mapper mapper=new Mapper() {
			@Override
			public double f(double x, double y) {
				Matrix point=new Matrix(Config.getDimension(), 1);
				point.set(0, 0, x);
				point.set(1, 0, y);
				return getProbability(point);
			}
		};
		//Ϊ�γ�������
		plot3d= new Plot3D(mapper);
	}
	
	/**
	 * ���ĳ�����ڸ�˹���ģ���еĸ���
	 * @param point ����ĵ�
	 * @return point�ڸ�˹���ģ���еĸ���
	 */
	public double getProbability(Matrix point) {
		double probability=0.0;
		for (OneGaussian oneGaussian : mixtureGaussian) {
			probability+=oneGaussian.getWeightedProbability(point);
		}
		return probability;
	}
	public MixtureGaussian getImprovedObj() {
		// ���ɸĽ���Ļ�ϸ�˹ģ��
		MixtureGaussian improvedMixtureGaussian=new MixtureGaussian();		
		
		
		//�˴��μ���־������ѧϰP210��߱ʼ�
		
		//��ʼ���������ϸ�˹ģ�������е���˹ģ������������֮�乹�ɵľ���
		int oneGaussianID=0;//��i������˹ģ��
		for (Iterator<OneGaussian> iterator = mixtureGaussian.iterator(); iterator.hasNext();oneGaussianID++) {
			OneGaussian oneGaussian = (OneGaussian) iterator.next();
			//��ÿһ����Ԫ��ֵ
			oneGaussian.setEverySampleWeightedProbabilityInTable(oneGaussianID,this.allGaussian_AllSampleTable);
		}
		//��ÿһ�����
		this.allGaussian_AllSampleTable.setColumnSumOfweightedProbability();
		//����ÿһ����Ԫ��ĺ������
		this.allGaussian_AllSampleTable.setAllCellOfPostProbability();
		//����ÿһ�к�����ʵĺ�
		this.allGaussian_AllSampleTable.setRowSumOfPostProbability();
		//�����Ľ�
		oneGaussianID=0;
		//mixtureGaussian.iterator()���ܱ�֤˳�����
		for (Iterator<OneGaussian> iterator = mixtureGaussian.iterator(); iterator.hasNext();oneGaussianID++) {
			OneGaussian oneGaussian2 = (OneGaussian) iterator.next();
			OneGaussian improvedOneGaussian=oneGaussian2.getImprovedOneGaussian(oneGaussianID,this.allGaussian_AllSampleTable);
			improvedMixtureGaussian.addOneGaussian(improvedOneGaussian);
		}
		
		return improvedMixtureGaussian;

	}

	private void addOneGaussian(OneGaussian improvedOneGaussian) {
		mixtureGaussian.add(improvedOneGaussian);
	}

	/**
	 * ������
	 */
	public void printResult() {
		System.out.print("\n���:");
		OneGaussian oneGaussian=new OneGaussian();
		for (Iterator<OneGaussian> iterator = mixtureGaussian.iterator(); iterator.hasNext();) {
			oneGaussian = (OneGaussian) iterator.next();
			oneGaussian.printResult();
		}
	}


}
