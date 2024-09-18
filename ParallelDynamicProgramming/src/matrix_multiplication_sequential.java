package main;

public class matrix_multiplication_sequential{
	private double[] dimensions;
	public matrix_multiplication_sequential(double[] dimensions) {
		this.dimensions = dimensions;
	}
	
	public double solve() {
		double[][] dp = new double[dimensions.length-1][dimensions.length-1];
		double[][] min = new double[dimensions.length-1][dimensions.length-1];
		//initialize
		for(int i = 0;i<dimensions.length-1;i++) {
			for(int j =0;j<dimensions.length-1;j++) {
				dp[i][j] = 0.0;
				min[i][j] = Double.MAX_VALUE;
			}
		}
		for(int i=dimensions.length-2;i>=0;i--) {
			for(int j = 0;j<dimensions.length-1;j++) {
				if(i>=j) {
					continue;
				}
				else {
					for(int k =i;k<j;k++) {
						min[i][j] = Math.min(min[i][j], dp[i][k]+dp[k+1][j]+dimensions[i]*dimensions[k+1]*dimensions[j+1]);
					}
					dp[i][j] = min[i][j];
			}
		}
		
	}
		return dp[0][dimensions.length-2];
}
}
