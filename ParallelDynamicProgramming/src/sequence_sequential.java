package main;

import java.util.ArrayList;
import java.util.List;

public class sequence_sequential {


    public static int sequence_sequential(int[] input) {

        if (input.length == 0) return 0;

        int[] dp = new int[input.length];
        dp[0] = 1;
        int maxResult = 1;

        for (int i = 1; i < input.length; i++) {
            int maxVal = 0;
            for (int j = 0; j < i; j++) {
                if (input[i] > input[j]){
                    maxVal = Math.max(maxVal, dp[j]);
                }
            }
            dp[i] = maxVal + 1;
            maxResult = Math.max(maxResult, dp[i]);
        }
        return maxResult;
    }

}
