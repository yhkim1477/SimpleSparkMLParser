package com.yhkim.parser.spark.mllib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecisionTree {
	
	private int IDX_CLASS;
	private int IDX_FEATURE;
	private int IDX_VALUE;
	private int IDX_PREDICT;
	private TreeNode root = null;
	
	public DecisionTree() {
		IDX_CLASS = 0;
		IDX_FEATURE = 2;
		IDX_VALUE = 4;
		IDX_PREDICT = 1;
	}
	
	public void Initialize(String raw) throws IOException {
		List<String[]> parsedRawModel = parseRawModel(raw);
		
		boolean isLeftLeaf = true;
		for (String[] line : parsedRawModel) {
			if (line[IDX_CLASS].equals("If")) {
				int feature = Integer.parseInt(line[IDX_FEATURE]);
				double value = Double.parseDouble(line[IDX_VALUE]);
				insertFeature(isLeftLeaf, feature, value);
				isLeftLeaf = true;
			} else if (line[IDX_CLASS].equals("Else")) {
				root = root.nodeRoot;
				isLeftLeaf = false;
			} else { // Predict
				double predict = Double.parseDouble(line[IDX_PREDICT]);
				insertPredict(isLeftLeaf, predict);
			}
		}
	}
	
	private void insertFeature(boolean isLeftLeaf, int feature, double value) {
		root = insertNode(isLeftLeaf, feature, value);
	}
	
	private TreeNode insertNode(boolean isLeftLeaf, int feature, double value) {
		if (root == null) {
			return new TreeNode(null, feature, value);
		} else if (isLeftLeaf) {
			root.nodeLeft = new TreeNode(root, feature, value);
			return root.nodeLeft;
		} else {
			root.nodeRight = new TreeNode(root, feature, value);
			return root.nodeRight;
		}
	}
	
	private void insertPredict(boolean isLeftLeaf, double predict) {
		if (isLeftLeaf) root.predictLeft = predict;
		else root.predictRight = predict;
	}
	
	public List<String[]> parseRawModel(String raw) {
		String clean = raw.replaceAll("[():]", "").trim();
		if (clean.isEmpty()) return null;
		
		String[] lines = clean.split("\n");
		List<String[]> arModel = new ArrayList<>(lines.length);
		for (String line : lines) {
			String[] split = line.split(" ");
			if (split.length >= 2) arModel.add(split);
		}
		
		return arModel;
	}
}
