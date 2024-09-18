package main;

import java.util.concurrent.atomic.AtomicInteger;
public class sequence_parallel extends LLP{
    private int[] A;
    private int[][] pre;
    private boolean[] fixed;
    public sequence_parallel(int[] input){
        super(input.length);
        this.A=input;
        this.fixed = new boolean[input.length];
        for(int i=0;i<A.length;i++){
            G[i] = new AtomicInteger(1);
            fixed[i]=false;
        }
    }
    
    @Override
    public boolean forbidden(int j) {
    	//System.out.println("index: "+j);
        if(fixed[j]==false){
            for(int i=0;i<j;i++){
                if(A[i]<A[j]&&fixed[i]==false){
                	//System.out.println("index: "+j);
                    return false;
                }
            }
        return true;
        }
        //System.out.println("index: "+j);
        return false;
    }

    @Override
    public void advance(int j) {
        int maxI=0;
        int max = -1;
        for(int i=0;i<j;i++){
            if((A[i]<A[j])&&(G[i].get()>max)){
                maxI=i;
                max = G[i].get();
            }
        }
        if(max!=-1) {
        	G[j].set(G[maxI].get()+1);
        }
        
        fixed[j]=true;

    }

    public int getSolution(){
        int max = 0;
        for(int i=0;i<A.length;i++){
            max= Math.max(max, G[i].get());
        }
        return max;
    }
}