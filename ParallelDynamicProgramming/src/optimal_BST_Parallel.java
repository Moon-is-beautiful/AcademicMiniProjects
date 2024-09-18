package main;

import java.util.concurrent.atomic.AtomicInteger;

public class optimal_BST_Parallel extends LLP_new{
	private double[] frequency;
	private double[][] min;
	
	public optimal_BST_Parallel(double[] frequency) {
		super(frequency.length);
		this.frequency = frequency;
		this.min = new double[frequency.length][frequency.length];
		int i,j;
		for(int index =0;index<frequency.length*frequency.length;index++) {
        	i = index/frequency.length;
        	j = index%frequency.length;
        	min[i][j] = Double.MAX_VALUE;
        	G[i][j] = 0.0;
        	if(i==j) {
        		G[i][j] = frequency[i];
        		//System.out.println(G[i][j]);
        	}
        }
	}
	

	@Override
	public boolean forbidden(int i, int j) {
		//compute s(i,j)
		System.out.println("current grid i: "+i+" j "+j+" priority: "+priority.get());
		double sum = 0.0;
		for(int index =i;index<=j;index++) {
			sum += frequency[index];
		}
		for(int k=i;k<=j;k++) {
			if(k==i) {
				min[i][j] = Math.min(min[i][j], sum+G[k+1][j]);
			}
			else if(k==j) {
				min[i][j] = Math.min(min[i][j], sum+G[i][k-1]);
			}
			else {
				min[i][j] = Math.min(min[i][j], G[i][k-1]+sum+G[k+1][j]);
			}
		}
		return G[i][j]<min[i][j];
	}

	@Override
	public void advance(int i, int j) {
		// TODO Auto-generated method stub
		
		G[i][j] = min[i][j];
	}
	
	public double getSolution() {
        // Trim the state vector to only the reduce elements
        // Your result should have n-1 elements
    	return G[0][frequency.length-1];
    }
}
