package main;

public class matrix_multiplication_parallel extends LLP_new {
	private double dimensions[];
	private double[][] min;
	public matrix_multiplication_parallel(double[] dimensions) {
		super(dimensions.length-1);
		this.dimensions = dimensions;
		this.min = new double[dimensions.length-1][dimensions.length-1];
		int i,j;
		for(int index =0;index<(dimensions.length-1)*(dimensions.length-1);index++) {
        	i = index/(dimensions.length-1);
        	j = index%(dimensions.length-1);
        	min[i][j] = Double.MAX_VALUE;
        	G[i][j] = 0.0;
        }
	}
	@Override
	public boolean forbidden(int i, int j) {
		System.out.println("current grid i: "+i+" j "+j+" priority: "+priority.get());
		for(int k=i;k<j;k++) {
			min[i][j] = Math.min(min[i][j], G[i][k]+G[k+1][j]+dimensions[i]*dimensions[k+1]*dimensions[j+1]);
		}
		return G[i][j]<min[i][j];
	}
	@Override
	public void advance(int i, int j) {
		G[i][j] = min[i][j];
	} 
	
	public double getSolution() {
    	return G[0][dimensions.length-2];
    }
}
