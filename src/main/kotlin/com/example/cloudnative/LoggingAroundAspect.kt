package com.example.cloudnative

import org.apache.juli.logging.LogFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Aspect
class LoggingAroundAspect {
    private val log = LogFactory.getLog(javaClass)

    @Around("execution(* com.example.cloudnative.CustomerService.*(..))")
    fun log(jointPoint: ProceedingJoinPoint): Any {
        val start = LocalDateTime.now()
        lateinit var returnValue: Any
        var toThrow: Throwable? = null

        try {
            returnValue = jointPoint.proceed()
        } catch (t: Throwable) {
            toThrow = t
        }

        val stop = LocalDateTime.now()

        log.info("starting @ $start")
        log.info("finishing @ $stop with duration ${stop.minusNanos(start.nano.toLong()).nano}")

        if (toThrow != null) {
            throw toThrow
        }

        return returnValue
    }
}