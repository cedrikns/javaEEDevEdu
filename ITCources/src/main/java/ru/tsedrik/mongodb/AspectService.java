package ru.tsedrik.mongodb;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AspectService {

    RequestInfoRepository requestInfoRepository;

    public AspectService(RequestInfoRepository requestInfoRepository) {
        this.requestInfoRepository = requestInfoRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(AspectService.class.getName());

    @Pointcut("@annotation(ru.tsedrik.mongodb.MongoDbLog) && execution(public * *(..))")
    public void publicAspectMethod() {
    }

    @Around("publicAspectMethod()")
    public Object aspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LocalDateTime dateTime = LocalDateTime.now();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        String method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getName();
        RequestInfo requestInfo = new RequestInfo(user, dateTime, method);
        requestInfoRepository.insert(requestInfo);

        return proceedingJoinPoint.proceed();
    }

}
