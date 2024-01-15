/*
    This class includes 2 methods which are used to do some calculations which require more than
    one line of code to perform.
*/
package MrThompson;

import java.util.Arrays;

public class avgMaths {
    public static double mean(double[] nums) { //Computes the mean of an array of numbers
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return Math.round(((double) sum / (double) nums.length)*10) / 10.0d; //Returns a double
    }

    public double fitLinearRegression(double[] yValues) {
        //Given a list of Y values, this method computes the linear
        //regression model for these points, and returns the gradient, without the Y-Intercept
        System.out.println("1: 2: " + Arrays.toString(yValues));
        int n = yValues.length;
        double[] xValues = new double[n];
        double sum1 = 0, squaredSum = 0, sumX = 0, sumY = 0;

        for (int j = 0; j < n; j++) {
            xValues[j] = j + 1;
            sum1 += xValues[j] * yValues[j];
            squaredSum += xValues[j] * xValues[j];
            sumX += xValues[j];
            sumY += yValues[j];
        }

        double m = ((n * sum1) - (sumX * sumY)) / ((n * squaredSum) - (sumX * sumX));

        return m;  //returns the gradient (double)
    }
}
