package com.example.aoptraining.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class CheckNotNullFieldAspect {

    @Before("@annotation(com.example.aoptraining.aspect.CheckNotNullFields)")
    public void checkNotNullFields(JoinPoint joinPoint)
    {
        Object[] args = joinPoint.getArgs();
        if(args.length >0)
        {
        for (Object arg : args)
        {
            if (isValid(arg)) {
                System.out.println("Perfect");
                return;
            }

        }
        }
        throw new IllegalArgumentException("Either ProductRef or Name must not be null");
    }

    private boolean isValid(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if ("productRef".equals(field.getName()) && field.get(obj) != null) {
                    return true;
                }
                if ("name".equals(field.getName()) && field.get(obj) != null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

//    public Boolean atLeastOneFieldNull(Object obj)
//    {
//        for(Field field : obj.getClass().getDeclaredFields())
//        {   field.setAccessible(true);
//
//            try {
//                System.out.println(field.get(obj));
//                if(field.get(obj) == null)
//                    return true;
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return false;
//    }
}
