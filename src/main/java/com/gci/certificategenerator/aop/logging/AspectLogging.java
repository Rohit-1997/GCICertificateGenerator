package com.gci.certificategenerator.aop.logging;

import com.gci.certificategenerator.logger.Loggers;
import jakarta.annotation.PostConstruct;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class AspectLogging {
    private static final Logger applicationLog = Loggers.APPLICATION_LOG;

    @PostConstruct
    public void postConstructMethod() {
        applicationLog.info("Created AOP");
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut(
            "within(@org.springframework.stereotype.Repository *)" +
                    " || within(@org.springframework.stereotype.Service *)" +
                    " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut(
            "within(com.gci.certificategenerator.web.rest..*)" +
                    " || within(com.gci.certificategenerator.**)"

    )
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String methodArguments = Arrays.toString(joinPoint.getArgs());
        applicationLog.info("Enter: {}() with argument[s] = {}", methodName, methodArguments);
        Object result = joinPoint.proceed();
        applicationLog.info("Exit: {}() with result = {}", methodName, result);
        return result;
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        applicationLog.info("handleRunTimeExceptions called :: {}", joinPoint);
        String cause = String.valueOf(e.getCause());
        String message = e.getMessage();
        String methodName = joinPoint.getSignature().getName();
        applicationLog.error("Exception in {} with cause : {} with message : {}",methodName, cause, message);
    }
}
