package com.yhkim.parser.spark.mllib;

import org.apache.spark.mllib.linalg.BLAS;
import org.apache.spark.mllib.linalg.DenseVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradientBoostedTrees {
	
	public static int ALGO_SUM = 0;
	public static int ALGO_VOTE = 1;
	private List<DecisionTree> trees;
	private double[] treeWeights;
	
	public GradientBoostedTrees(String raw) throws IOException {
		this(raw, null);
	}
	
	public GradientBoostedTrees(String raw, double[] treeWeights) throws IOException {
		this.treeWeights = treeWeights;
		trees = new ArrayList<>();
		
		String[] modelsRaw = raw.split("(Tree [0-9]{1,2}.*:)");
		for (String model : modelsRaw) {
			if (model.trim().isEmpty()) continue;
			
			DecisionTree tree = new DecisionTree();
			tree.initialize(model);
			if (tree.getFeatures() > 0 && tree.getPredicts() > 0) trees.add(tree);
		}
		
		if (this.treeWeights == null) {
			this.treeWeights = new double[trees.size()];
			this.treeWeights[0] = 1D;
			for (int i = 1; i < trees.size(); i++) this.treeWeights[i] = 0.1D;
		}
	}
	
	public void print(int model) {
		trees.get(model).print();
	}
	
	public double predict(double[] vector) {
		return predict(vector, ALGO_SUM);
	}
	
	public double predict(double[] vector, int algo) {
		double[] predictVec = new double[trees.size()];
		
		for (int i = 0; i < trees.size(); i++) {
			predictVec[i] = trees.get(i).predict(vector);
		}
		
		if (algo == ALGO_SUM) {
			return BLAS.dot(new DenseVector(predictVec), new DenseVector(treeWeights));
		} else {
			return vote(predictVec);
		}
	}
	
	private double sum(double[] predict) {
		double result = 0;
		for (double value : predict) result += value;
		return result;
	}
	
	private double vote(double[] predict) {
		double result = 0;
		for (double value : predict) result += (value > 0) ? 1 : 0;
		return ((result / predict.length) > 0.5) ? 1D : 0D;
	}
}
