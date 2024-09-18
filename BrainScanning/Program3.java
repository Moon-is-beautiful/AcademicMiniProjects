//Name: (Fill this in)
//EID: (Fill this in)
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class Program3 {

    // DO NOT REMOVE OR MODIFY THESE VARIABLES (calculator and treatment_plan)
    ImpactCalculator calculator;    // the impact calculator
    int[] treatment_plan;           // array to store the treatment plan

    public Program3() {
        this.calculator = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3.
     * 
     *  DO NOT MODIFY THIS METHOD
     * 
     */
    public void initialize(ImpactCalculator ic) {
        this.calculator = ic;
        this.treatment_plan = new int[ic.getNumMedicines()];
    }


    /*
     * This method computes and returns the total impact of the treatment plan. It should
     * also fill in the treatment_plan array with the correct values.
     * 
     * Each element of the treatment_plan array should contain the number of hours
     * that medicine i should be administered for. For example, if treatment_plan[2] = 5,
     * then medicine 2 should be administered for 5 hours.
     * 
     */

    public int computeImpact() {

        int totalTime = calculator.getTotalTime();
        int numMedicines = calculator.getNumMedicines();
        treatment_plan=new int[numMedicines];

        
        // Put your code here
        int W=totalTime;
        int n=numMedicines;
        int i, w; 
        int K[][] = new int[n][W + 1]; 
        int P[][]=new int [n][W+1];
  
        // Build table K[][] in bottom up manner 
        for (i = 0; i <= n-1; i++) { 
            for (w = 0; w <= W; w++) { 
                if (w == 0) 
                    K[i][w] = 0; 
                else if(i == 0){
                    P[i][w]=w;
                    if(w>0){
                        if(calculator.calculateImpact(0, w)==calculator.calculateImpact(0, w-1)){
                            P[i][w]=P[i][w-1];
                        }
                    }
                    K[i][w]=calculator.calculateImpact(0, w);
                }
                else{
                    List<Integer> temp=new ArrayList<>();
                    for(int x=0;x<=W&&x<=w;x++){
                        temp.add(K[i-1][w-x]+calculator.calculateImpact(i, x));
                    }
                    P[i][w]=temp.indexOf(findMax(temp));
                    K[i][w]  = findMax(temp); 
                }
            } 
        } 
        int temp=0;
        for(i=n-1;i>=0;i--){
            treatment_plan[i]=P[i][W-temp];
            temp+=P[i][W-temp];
        }
        return K[n-1][W];

    }

    public int opt(int index,int hours,int numMedicines){
        if(hours==0||index==numMedicines){
            return 0;
        }else{
            List<Integer> temp=new ArrayList<>();

            for(int i=0;i<hours;i++){
                temp.add(opt(index+1,hours-i,numMedicines)+calculator.calculateImpact(index, i));
            }
            treatment_plan[index]=temp.indexOf(findMax(temp));
            return findMax(temp);
        }
    }

    public int findMax(List<Integer> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }

        int max = Integer.MIN_VALUE; // Initialize max to the smallest possible integer value

        for (int num : list) {
            if (num > max) {
                max = num;
            }
        }

        return max;
    }

    /*
     * This method prints the treatment plan.
     */
    public void printTreatmentPlan() {
        System.out.println("Please administer medicines 1 through n for the following amounts of time:\n");
        int hoursForI = 0;
        int n = calculator.getNumMedicines();
        for(int i = 0; i < n; i++){
            // retrieve the amount of hours for medicine i
            hoursForI = treatment_plan[i]; // ... fill in here ...
            System.out.println("Medicine " + i + ": " + hoursForI); 
        }
    }
}


