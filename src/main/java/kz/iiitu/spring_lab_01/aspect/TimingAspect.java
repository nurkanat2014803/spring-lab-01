package kz.iiitu.spring_lab_01.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
public class TimingAspect {

    private static final Logger log = LoggerFactory.getLogger(TimingAspect.class);

    @Around("kz.iiitu.spring_lab_01.aspect.Pointcuts.serviceOperation()")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long ms = System.currentTimeMillis() - start;
            if (ms > 200) {
                log.warn("[TIME] SLOW: {} - {} ms", pjp.getSignature(), ms);
            } else {
                log.info("[TIME] {} - {} ms", pjp.getSignature(), ms);
            }
        }
    }
}