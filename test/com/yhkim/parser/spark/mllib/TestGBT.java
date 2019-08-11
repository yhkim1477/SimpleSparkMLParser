package com.yhkim.parser.spark.mllib;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestGBT {
	
	@Test
	public static void main(String[] args) throws IOException {
		String raw = "GradientBoostedTreesModel classifier of depth 2 with 8 nodes\n" +
				"Tree 0:\n" +
				"  If (feature 0 <= 1.0)\n" +
				"    If (feature 1 <= 2.0)\n" +
				"      Predict: -0.356328434\n" +
				"    Else (feature 1 > 2.0)\n" +
				"      If (feature 1 <= 2.0)\n" +
				"        Predict: -0.356328434\n" +
				"      Else (feature 1 > 2.0)\n" +
				"        Predict: 0.242148432\n" +
				"  Else (feature 0 > 1.0)\n" +
				"    Predict: 0.32325233\n" +
				"Tree 1:\n" +
				"  If (feature 0 <= 1.0)\n" +
				"    If (feature 1 <= 2.0)\n" +
				"      Predict: -0.356328434\n" +
				"    Else (feature 1 > 2.0)\n" +
				"      Predict: 0.242148432\n" +
				"  Else (feature 0 > 1.0)\n" +
				"    Predict: 0.32325233";
		
		GradientBoostedTrees gbt = new GradientBoostedTrees(raw);
		System.out.println(gbt.predict(new double[]{1.5D, 2D}));
		gbt.print(0);
	}
}
