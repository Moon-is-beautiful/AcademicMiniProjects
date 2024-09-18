package main;

public class knapsack_parallel extends LLP_new {
    private int[] weights;
    private int[] values;
    private int maxWeight;

    public knapsack_parallel(int[] weights, int[] values, int maxWeight) {
        super(weights.length);
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
        initializeG();
    }

    private void initializeG() {
        this.G = new double[num_threads][maxWeight + 1];
        for (int i = 0; i < num_threads; i++) {
            for (int j = 0; j <= maxWeight; j++) {
                G[i][j] = 0;
            }
        }
    }

    @Override
    public boolean forbidden(int i, int j) {
        if (j < weights[i]) {
            return G[i][j] != (i > 0 ? G[i-1][j] : 0);
        }
        double includedValue = (i > 0 ? G[i-1][j-weights[i]] : 0) + values[i];
        double excludedValue = i > 0 ? G[i-1][j] : 0;
        double optimalValue = Math.max(includedValue, excludedValue);
        return G[i][j] < optimalValue;
    }

    @Override
    public void advance(int i, int j) {
        if (j < weights[i]) {
            G[i][j] = i > 0 ? G[i-1][j] : 0;
        } else {
            double includedValue = (i > 0 ? G[i-1][j-weights[i]] : 0) + values[i];
            double excludedValue = i > 0 ? G[i-1][j] : 0;
            G[i][j] = Math.max(includedValue, excludedValue);
        }
    }

    public void solve() {
        for (int i = 0; i < num_threads; i++) {
            for (int j = 0; j <= maxWeight; j++) {
                if (forbidden(i, j)) {
                    advance(i, j);
                }
            }
        }
    }


    public double getSolution() {
        return G[num_threads-1][maxWeight];
    }

}




















