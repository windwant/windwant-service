package org.windwant.concurrent.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 计算斐波那契数列第 n 为数字
 */
class Fibonacci extends RecursiveTask<Integer> {
  final int n;
  Fibonacci(int n) { this.n = n; }
  protected Integer compute() {
    if (n == 1) return 0;
    if (n == 2) return 1;
    Fibonacci f1 = new Fibonacci(n - 1);
    Fibonacci f2 = new Fibonacci(n - 2);
    invokeAll(f1, f2);
    return f2.join() + f1.join();
  }
}
