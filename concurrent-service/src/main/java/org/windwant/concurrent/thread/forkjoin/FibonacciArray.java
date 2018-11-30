package org.windwant.concurrent.thread.forkjoin;

import java.util.*;
import java.util.concurrent.RecursiveTask;

class FibonacciArray extends RecursiveTask<Map<String, String>> {
  final int n;
  FibonacciArray(int n) { this.n = n; }
  protected Map<String, String> compute() {
    if (n == 1) return new HashMap(){{put("m", "0"); put("l", "0");}};
    if (n == 2) return new HashMap(){{put("m", "1"); put("l", "0,1");}};
    FibonacciArray f1 = new FibonacciArray(n - 1);
    FibonacciArray f2 = new FibonacciArray(n - 2);
    invokeAll(f1, f2);

    Map<String, String> f2Result = f2.join();
    Map<String, String> f1Result = f1.join();
    String result = String.valueOf(Integer.parseInt(f1Result.get("m")) + Integer.parseInt(f2Result.get("m")));
    return new HashMap(){{put("m", result); put("l", f1Result.get("l") + "," +  String.valueOf(result));}};
  }
}
