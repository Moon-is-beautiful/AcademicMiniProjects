package main;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class LLP_new {
    // Feel free to add any methods here. Common parameters (e.g. number of processes)
    // can be passed up through a super constructor. Your code will be tested by creating
    // an instance of a sub-class, calling the solve() method below, and then calling the
    // sub-class's getSolution() method. You are free to modify anything else as long as
    // you follow this API (see SimpleTest.java)

    // Checks whether process j is forbidden in the state vector G
	protected int num_threads;
	protected double[][] G;
	//private Advance[] advance_threadPool;
	protected ExecutorService executor;
    protected Future<Boolean>[] futures;
    protected MyThread_new[] threads; 
    protected AtomicInteger priority;
	
	public LLP_new(int num_threads) {
		this.num_threads = num_threads;
		this.G = new double[num_threads][num_threads];
		this.executor = Executors.newFixedThreadPool(num_threads*num_threads);
        this.futures = new Future[num_threads*num_threads];
        this.threads = new MyThread_new[num_threads*num_threads];
        this.priority = new AtomicInteger(1);
        int i,j;
        
		for (int index=0;index<(int) Math.pow(num_threads, 2);index++) {
			i = index/num_threads;
			j = index%num_threads;
            threads[index] = new MyThread_new(i, j, this);    
		}		
	}
	
    public abstract boolean forbidden(int i, int j);

    // Advances on process j
    public abstract void advance(int i, int j);
   
    
    public boolean no_forbidden() {
    	boolean predicate = true;
    	for(int index=0;index<num_threads-priority.get();index++) {
    		
    			int i = index;
    			int j = index+priority.get();
    			int cur = i*num_threads+j;
        		futures[cur] = executor.submit(threads[cur]);
    
    }
    	/*
    	 * */
    	for(int index=0;index<num_threads-priority.get();index++) {
    		try {
    			int i = index;
    			int j = index+priority.get();
    			int cur = i*num_threads+j;
        		if(futures[cur].get()==false) {
					predicate = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
    	}
    	return predicate;
    	//return true;
    }
    

    public void solve() {
        // Implement this method. There are many ways to do this but you
        // should follow the following basic steps:
        // 1. Compute the forbidden states
        // 2. Advance on forbidden states in parallel
        // 3. Repeat 1 and 2 until there are no forbidden states
    	
    	for(int cur_pri=priority.get();cur_pri<num_threads;cur_pri++) {
    		priority.set(cur_pri);
    		while(!no_forbidden());
    	}

    }
}
