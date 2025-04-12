package com.siriuslab.onec.widget.app.aspect.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Логирование только методов с аннотацией @Loggable (если требуется)
    @Before("execution(* com.siriuslab.onec.widget.domain.activation.api..*(..)) && " +
            "@annotation(com.siriuslab.onec.widget.app.aspect.logging.Loggable)")
    public void logAnnotatedMethods(JoinPoint joinPoint) {
        log.info("Аннотированный метод вызван: " + joinPoint.getSignature().getName());
    }
}
