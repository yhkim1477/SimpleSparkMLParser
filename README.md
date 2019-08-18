# SimpleSparkMLParser

SimpleSparkMLParser converts a Spark ML's model description to the DecisionTree Class

## Features
* Support DecisionTreeModel
* Support GradientBoostedTreesModel

## Example
```java
GradientBoostedTrees gbt = new GradientBoostedTrees(raw);
double result = gbt.predict(new double[]{1.5D, 2D});
gbt.print(0); // for test
```

## Example of a model description
```
GradientBoostedTreesModel classifier of depth 2 with 8 nodes
Tree 0:
  If (feature 0 <= 1.0)
    If (feature 1 <= 2.0)
      Predict: -0.356328434
    Else (feature 1 > 2.0)
      If (feature 1 <= 2.0)
        Predict: -0.356328434
      Else (feature 1 > 2.0)
        Predict: 0.242148432
  Else (feature 0 > 1.0)
    Predict: 0.32325233
Tree 1:
  If (feature 0 <= 1.0)
    If (feature 1 <= 2.0)
      Predict: -0.356328434
    Else (feature 1 > 2.0)
      Predict: 0.242148432
  Else (feature 0 > 1.0)
    Predict: 0.32325233
```