package kz.iiitu.spring_lab_01.aspect;

import kz.iiitu.spring_lab_01.audit.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp, Audited audited) throws Throwable {
        String args = audited.logArguments()
                ? Arrays.toString(pjp.getArgs())
                : "***";
        log.info("[AUDIT] start {} at {} args={}",
                audited.action(), LocalDateTime.now(), args);
        try {
            Object result = pjp.proceed();
            log.info("[AUDIT] {} success", audited.action());
            return result;
        } catch (Throwable ex) {
            log.info("[AUDIT] {} failure: {}", audited.action(), ex.getMessage());
            throw ex; // ВАЖНО: пробрасываем — иначе клиент получит 200 OK
        }
    }
}