package com.yhkim.parser.spark.mllib;

public class TreeNode {
	TreeNode nodeRoot;
	TreeNode nodeLeft;
	TreeNode nodeRight;
	int feature;
	double threshold;
	double predictLeft;
	double predictRight;
	
	TreeNode() {
		nodeRoot = null;
		nodeLeft = null;
		nodeRight = null;
		feature = 0;
		threshold = 0;
		predictLeft = 0;
		predictRight = 0;
	}
	
	TreeNode(TreeNode nodeRoot, int feature, double threshold) {
		this.nodeRoot = nodeRoot;
		nodeLeft = null;
		nodeRight = null;
		this.feature = feature;
		this.threshold = threshold;
		predictLeft = 0;
		predictRight = 0;
	}
	
	public void print(boolean isLeftLeaf) {
		if (isLeftLeaf) {
			System.out.println("  If feature " + feature + " <= " + threshold);
			if (predictLeft != 0) {
				System.out.println("    Predict " + predictLeft);
			}
		} else {
			System.out.println("  Else feature " + feature + " > " + threshold);
			if (predictRight != 0) {
				System.out.println("    Predict " + predictRight);
			}
		}
		
	}
	
}
