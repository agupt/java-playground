package com.agupt.playground;


public class RateLimiter {
    private final int limit;
    private final long intervalInSec;
    // start of current rate limit session
    private Long start;
    private Integer counter;
    
    private RateLimiter(final int limit, final long intervalInSec) {
        this.limit = limit;
        this.intervalInSec = intervalInSec;
    }
    
    public static RateLimiter create(final int limit, final long intervalInSec) {
        return new RateLimiter(limit, intervalInSec);    
    }
    
    // tries to move counter to next call if ratelimit is reached it throws an exception 
    public void increment() throws Exception {
        if (start == null) {
            // this is first time I am getting called 
            start = System.currentTimeMillis();
            counter = new Integer(0);
            counter++;
            return;
        }
        
        // this is a repeat call
        final Long secBetweenCalls = (System.currentTimeMillis() - start)/1000 ;
        if (secBetweenCalls < intervalInSec) {
            if (counter < limit) {
                counter++;
            } else {
                throw new Exception("RateLimit reached");
            }
        } else {
            // sec between calls is more than interval, reset counter and start time
            start = System.currentTimeMillis();
            counter = new Integer(0);
            counter++;
        }
    }
    
    public void reset() {
        start = null;
        counter = null;
    }
    
    @Override
    public String toString() {
        return String.format("counter %d start %d",counter, start);
    }
}