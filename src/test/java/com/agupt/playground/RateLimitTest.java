package com.agupt.playground;

import org.junit.*;

public class RateLimitTest {
	
	@Test
	public void testRateLimit() {
		final RateLimiter rl = RateLimiter.create(10,10L);
        final Long start = System.currentTimeMillis();
        int count = 0 ;
        while(System.currentTimeMillis() - start < 10000L) {
            try {
            	count++;
                rl.increment();
            } catch (Exception e) {
                Assert.assertTrue("got exception before reaching count under given limit",count >= 10);
                Assert.assertTrue("got exception before reaching time limit",System.currentTimeMillis() - start < 10000L);
                break;
            }
        }
	}

}