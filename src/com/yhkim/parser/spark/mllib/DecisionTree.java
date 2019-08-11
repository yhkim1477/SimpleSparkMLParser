package com.yhkim.parser.spark.mllib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecisionTree {
	
	private int IDX_CLASS = 0;
	private int IDX_FEATURE = 2;
	private int IDX_THRESHOLD = 4;
	private int IDX_PREDICT = 1;
	private TreeNode root = null;
	private int cntPredict = 0;
	private int cntDecision = 0;
	
	public DecisionTree() {
	}
	
	public void initialize(String raw) throws IOException {
		List<String[]> model = parseRawModel(raw);
		if (model == null) return;
		
		boolean isLeftLeaf = true;
		for (String[] line : model) {
			if (line[IDX_CLASS].equals("If")) {
				int feature = Integer.parseInt(line[IDX_FEATURE]);
				double threshold = Double.parseDouble(line[IDX_THRESHOLD]);
				insertFeature(isLeftLeaf, feature, threshold);
				isLeftLeaf = true;
				cntDecision++;
			} else if (line[IDX_CLASS].equals("Else")) {
				if (!isLeftLeaf) moveToEmptyRoot();
				isLeftLeaf = false;
				cntDecision++;
			} else if (line[IDX_CLASS].equals("Predict")) {
				double predict = Double.parseDouble(line[IDX_PREDICT]);
				insertPredict(isLeftLeaf, predict);
				cntPredict++;
			} else {
				return;
			}
		}
	}
	
	public void print() {
		moveToRoot();
		printAll(root);
	}
	
	private void printAll(TreeNode node) {
		node.print(true);
		if (node.nodeLeft != null) printAll(node.nodeLeft);
		node.print(false);
		if (node.nodeRight != null) printAll(node.nodeRight);
	}
	
	public int getFeatures() {
		return cntDecision;
	}
	
	public int getPredicts() {
		return cntPredict;
	}
	
	private void insertFeature(boolean isLeftLeaf, int feature, double threshold) {
		root = insertNode(isLeftLeaf, feature, threshold);
	}
	
	private TreeNode insertNode(boolean isLeftLeaf, int feature, double threshold) {
		if (root == null) {
			return new TreeNode(null, feature, threshold);
		} else if (isLeftLeaf) {
			root.nodeLeft = new TreeNode(root, feature, threshold);
			return root.nodeLeft;
		} else {
			root.nodeRight = new TreeNode(root, feature, threshold);
			return root.nodeRight;
		}
	}
	
	private void insertPredict(boolean isLeftLeaf, double predict) {
		if (isLeftLeaf) root.predictLeft = predict;
		else root.predictRight = predict;
	}
	
	private void moveToRoot() {
		while (root != null && root.nodeRoot != null) root = root.nodeRoot;
	}
	
	private void moveToEmptyRoot() {
		do {
			root = root.nodeRoot;
		} while (root != null && root.nodeRight != null);
	}
	
	public double predict(double[] vector) {
		moveToRoot();
		
		while (root != null && root.nodeLeft != null && root.nodeRight != null) {
			if (vector[root.feature] <= root.threshold) root = root.nodeLeft;
			else root = root.nodeRight;
		}
		
		if (vector[root.feature] <= root.threshold) return root.predictLeft;
		else return root.predictRight;
	}
	
	private List<String[]> parseRawModel(String raw) {
		String clean = raw.replaceAll("[():]", "").trim();
		if (clean.isEmpty()) {
			System.out.println("Raw return");
			return null;
		}
		
		String[] lines = clean.split("\n");
		List<String[]> arModel = new ArrayList<>(lines.length);
		for (String line : lines) {
			String[] split = line.trim().split(" ");
			if (split.length >= 2) arModel.add(split);
		}
		
		return arModel;
	}
}
