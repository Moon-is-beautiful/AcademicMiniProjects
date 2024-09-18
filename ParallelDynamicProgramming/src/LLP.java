package main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class LLP {
    // Feel free to add any methods here. Common parameters (e.g. number of processes)
    // can be passed up through a super constructor. Your code will be tested by creating
    // an instance of a sub-class, calling the solve() method below, and then calling the
    // sub-class's getSolution() method. You are free to modify anything else as long as
    // you follow this API (see SimpleTest.java)

    // Checks whether process j is forbidden in the state vector G
	protected int num_threads;
	protected AtomicInteger[] G;
	//private Advance[] advance_threadPool;
	protected ExecutorService executor;
    protected Future<Boolean>[] futures;
    protected MyThread[] threads; 
	
	public LLP(int num_threads) {
		this.num_threads = num_threads;
		this.G = new AtomicInteger[num_threads];
		this.executor = Executors.newFixedThreadPool(num_threads);
        this.futures = new Future[num_threads];
        this.threads = new MyThread[num_threads];
        
		for (int i=0;i<num_threads;i++) {
            threads[i] = new MyThread(i, this);    
		}		
	}
	
    public abstract boolean forbidden(int j);

    // Advances on process j
    public abstract void advance(int j);
   
    public boolean no_forbidden() {
    	boolean predicate = true;
    	for(int i=0;i<num_threads;i++) {
            futures[i] = executor.submit(threads[i]);
    	}

    	for(int i=0;i<num_threads;i++) {
    		try {
				if(futures[i].get()==false) {
					predicate = false;
					//return false;
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
    	
    	while(true) {
    		if(no_forbidden()) {
    			break;
    		}
    	}

    }
}
