package org.windwant.concurrent.thread.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore
 * Semaphore维护者一组权限，无实体权限，只是一组代表权限的数字，
 * 每一次执行acquire都会被阻塞，直到获取获取权限，
 * 每一次执行release都会释放权限，唤醒阻塞acquire线程
 *
 * Semaphore通常应用于限制资源访问
 *
 *
 * Created by windwant on 2016/5/27.
 */
public class MySemaphore {

    public static void main(String[] args) {
        Semaphore sp = new Semaphore(5, true);  //公平性 构造函数 true
        ExecutorService es = Executors.newCachedThreadPool();
        Random r = new Random();
        for (int i = 0; i < 10; i++){
            es.execute(new Needer(sp, r.nextInt(10), "needer" + i));
        }
        es.shutdown();
    }
}

