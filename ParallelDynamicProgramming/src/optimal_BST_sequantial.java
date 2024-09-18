package main;

public class optimal_BST_sequantial {
	private double[] frequency;
	public optimal_BST_sequantial(double[] frequency) {
		this.frequency = frequency;
	}
	public double solve() {
		double[][] dp = new double[frequency.length][frequency.length];
		double[][] min = new double[frequency.length][frequency.length];
		//initialize
		for(int i = 0;i<frequency.length;i++) {
			for(int j =0;j<frequency.length;j++) {
				dp[i][j] = (i==j)? frequency[i]:0.0;
				min[i][j] = Double.MAX_VALUE;
			}
		}
		for(int i=frequency.length-1;i>=0;i--) {
			for(int j = 0;j<frequency.length;j++) {
				double sum = 0.0;
				for(int index =i;index<=j;index++) {
					sum+=frequency[index];
				}
				if(i>=j) {
					continue;
				}
				else {
					for(int k =i;k<=j;k++) {
						if(k==i) {
							min[i][j] = Math.min(min[i][j], sum+dp[k+1][j]);
						}
						else if(k==j) {
							min[i][j] = Math.min(min[i][j], sum+dp[i][k-1]);
						}
						else {
							min[i][j] = Math.min(min[i][j], dp[i][k-1]+sum+dp[k+1][j]);
						}
					}
					dp[i][j] = min[i][j];
				}
			}
		}
		return dp[0][frequency.length-1];
	}
}
