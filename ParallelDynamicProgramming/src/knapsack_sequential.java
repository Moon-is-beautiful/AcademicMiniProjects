package main;

import java.util.ArrayList;
import java.util.List;

public class knapsack_sequential {


    public static int knapsack_sequential(int[] weights, int[] values, int maxWeight) {

        if (weights == null || values == null || weights.length != values.length || maxWeight < 0)
            throw new IllegalArgumentException("Invalid input");

        final int N = weights.length;
        int[][] DP = new int[N + 1][maxWeight + 1];

        for (int i = 1; i <= N; i++) {
            int w = weights[i - 1], v = values[i - 1];
            for (int sz = 1; sz <= maxWeight; sz++) {
                DP[i][sz] = DP[i - 1][sz];
                if (sz >= w && DP[i - 1][sz - w] + v > DP[i][sz]) DP[i][sz] = DP[i - 1][sz - w] + v;
            }
        }

        int sz = maxWeight;
        List<Integer> itemsSelected = new ArrayList<>();

        for (int i = N; i > 0; i--) {
            if (DP[i][sz] != DP[i - 1][sz]) {
                int itemIndex = i - 1;
                itemsSelected.add(itemIndex);
                sz -= weights[itemIndex];
            }
        }

        return DP[N][maxWeight];
    }

}
