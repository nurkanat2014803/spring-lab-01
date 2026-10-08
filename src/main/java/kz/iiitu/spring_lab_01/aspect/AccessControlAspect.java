package kz.iiitu.spring_lab_01.aspect;

import kz.iiitu.spring_lab_01.audit.RequiresRole;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(4)
public class AccessControlAspect {

    private static final Logger log = LoggerFactory.getLogger(AccessControlAspect.class);

    @Before("@annotation(requiresRole)")
    public void checkAccess(JoinPoint jp, RequiresRole requiresRole) {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new SecurityException("No HTTP request context");
        }

        HttpServletRequest request = attrs.getRequest();
        String actualRole = request.getHeader("X-Role");

        log.info("[ACCESS] {} requires role '{}', actual role = '{}'",
                jp.getSignature().toShortString(), requiresRole.value(), actualRole);

        if (!requiresRole.value().equalsIgnoreCase(actualRole)) {
            log.warn("[ACCESS] DENIED: required '{}', got '{}'",
                    requiresRole.value(), actualRole);
            throw new SecurityException(
                    "Access denied: role '" + requiresRole.value() + "' required");
        }
        log.info("[ACCESS] GRANTED: {}", jp.getSignature().toShortString());
    }
}