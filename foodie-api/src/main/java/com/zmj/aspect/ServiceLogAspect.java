package com.zmj.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceLogAspect {

    final static Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * 切面表达式:
     * execution
     * 1.第一处 * 代表方法返回类型，*代表所有类型
     * 2.第二处 包名代表AOP监控的类所在的包
     *3.第三处 .. 代表该包及其子包下的所有方法
     * 4.第四处 * 代表类名， *代表所有类
     * 5.第五处 *(..) *代表方法中的方法名，(..)代表方法中的任何参数
     */
    @Around("execution(* com.zmj.service.impl..*.*(..))")
    public Object recondTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("===开始执行 {}.{} ===",
                        joinPoint.getTarget().getClass(),
                        joinPoint.getSignature().getName());
        Long begin = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        Long end = System.currentTimeMillis();
        Long taskTime = end - begin;
        if (taskTime > 3000) {
            logger.error("=== 执行结束，耗时: {} 毫秒 ===", taskTime);
        } else if (taskTime > 2000) {
            logger.warn("=== 执行结束，耗时: {} 毫秒 ===", taskTime);
        } else {
            logger.info("=== 执行结束，耗时: {} 毫秒 ===", taskTime);
        }
        return result;
    }
}
