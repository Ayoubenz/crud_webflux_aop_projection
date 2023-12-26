package com.example.aoptraining.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.aoptraining.service.Impl.ProductServiceImpl.*(..))")
  public void printMessageBeforeEachMethod(JoinPoint joinPoint)
  {
      String method = joinPoint.getSignature().getName();

      System.out.println("Method "+method+" is going to be executed now");
  }



}