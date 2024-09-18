package main;

import java.util.concurrent.Callable;

class MyThread implements Callable<Boolean> {
    private int id;
    private LLP llp;
    
    public MyThread(int id, LLP llp) {
        this.id = id;
        this.llp = llp;
    }

    public Boolean call() {
        // Perform some computation
        if(llp.forbidden(id)) {
        	llp.advance(id);
        	return false;
        }
        return true;
    }
}



