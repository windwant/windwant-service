package org.windwant.spring.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.windwant.spring.core.Anotation.MyTypeAnotation;
import org.windwant.spring.web.service.PerformerCallHelp;
import org.windwant.spring.web.service.impl.PerformerCallHelpImpl;


/**
 * Created by windwant on 2016/2/24.
 */
@Component("aspect")
@Aspect
public class Audience {
    @Pointcut( "execution(* org.windwant.spring.web.service.impl.APerformer.perform(..))")
    public void performance(){}

    @Pointcut( "execution(* org.windwant.spring.web.service.impl.APerformer.count(..))")
    public void counts(){}

    @Pointcut( "execution(* org.windwant.spring.web.service.impl.APerformer.*(..))")
    public void performer(){}

    @DeclareParents(value="org.windwant.spring.web.service.impl.APerformer+",
            defaultImpl=PerformerCallHelpImpl.class)
    public static PerformerCallHelp P;

    public void findSeats(JoinPoint joinPoint){
        System.out.println("findSeats()");
    }

    @Before("performance()")
    public void takeSeats(JoinPoint joinPoint){
        joinPoint.getArgs(); //获取参数
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
        }
        System.out.println("takeSeats()");
    }

    @Around("counts()")
    public void doSomthingIntercept(JoinPoint joinPoint){//(ProceedingJointPoint p)
        Object target = joinPoint.getTarget();
        System.out.println("target: " + target.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            System.out.println("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            System.out.println("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
        }
    }

    @Before("counts()&&args(count)")
    public void turnOffCellphones(JoinPoint joinPoint, int count){
        System.out.println("People know APerformer count:" + count);
        System.out.println("turnOffCellphones()");
    }

    @Before("performer()&&@annotation(at)")
    public void testAnotation(MyTypeAnotation at){
        System.out.println("Anotation value:" + at.value());
        System.out.println("Anotation type:" + at.type());
    }

    @AfterReturning(pointcut = "performance()",  returning="returnValue") //返回值
    public void applaud(JoinPoint point, Object returnValue){
        System.out.println(returnValue);
        System.out.println("applaud()");
    }
    @AfterThrowing("performance()")
    public void demandRefund(){
        System.out.println("demandRefund()");
    }

    @AfterThrowing(pointcut = "performer()")
    public void demandSilent(){
        System.out.println("demandSilent()");
    }
}
