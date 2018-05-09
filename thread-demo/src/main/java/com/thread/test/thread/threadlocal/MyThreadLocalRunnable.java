package com.thread.test.thread.threadlocal;

class MyThreadLocalRunnable implements Runnable
{

    private static ThreadLocal<Integer> num = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    public void run() {
        while (num.get() < 10) {
            Integer tmp = num.get();
            tmp++;
            num.set(tmp);
            System.out.println("Current thread: " + Thread.currentThread().getName() + "  My num: " + num.get());
        }
    }
}
