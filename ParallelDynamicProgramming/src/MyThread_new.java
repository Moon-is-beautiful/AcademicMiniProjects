package main;

import java.util.concurrent.*;

class MyThread_new implements Callable<Boolean> {
    private int i;
    private int j;
    private LLP_new llp;
    
    public MyThread_new(int i, int j, LLP_new llp) {
        this.i = i;
        this.j = j;
        this.llp = llp;
    }

    public Boolean call() {
        // Perform some computation
        if(llp.forbidden(i,j)) {
        	llp.advance(i,j);
        	return false;
        }
        return true;
    }
}



