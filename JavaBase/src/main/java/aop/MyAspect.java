package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Before("execution(public int aop.impl.CalcSericeImpl.*(..))")
    public void beforeNotify() {
        System.out.println("前置通知");
    }

    @After("execution(public int aop.impl.CalcSericeImpl.*(..))")
    public void afterNotify() {
        System.out.println("后置通知");
    }

    @AfterReturning("execution(public int aop.impl.CalcSericeImpl.*(..))")
    public void afterReturningNotify() {
        System.out.println("返回后通知");
    }

    @AfterThrowing("execution(public int aop.impl.CalcSericeImpl.*(..))")
    public void afterThrowingNotify() {
        System.out.println("异常后通知");
    }

    @Around("execution(public int aop.impl.CalcSericeImpl.*(..))")
    public Object aroundNotify(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object retValue = null;
        System.out.println("环绕通知之前");
        retValue = proceedingJoinPoint.proceed();
        System.out.println("环绕通知之后");
        return retValue;
    }
}
