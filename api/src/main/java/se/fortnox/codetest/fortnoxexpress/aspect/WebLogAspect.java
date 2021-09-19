package se.fortnox.codetest.fortnoxexpress.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Profile({"dev", "test"})
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    private static final String NEW_LINE = System.lineSeparator();

    @Pointcut("@annotation(se.fortnox.codetest.fortnoxexpress.aspect.WebLog)")
    public void webLog() {}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String methodDescription = getAspectLogDescription(joinPoint);

        logger.debug("========================================== Start ==========================================");
        logger.debug("URL            : {}", request.getRequestURL().toString());
        logger.debug("Description    : {}", methodDescription);
        logger.debug("HTTP Method    : {}", request.getMethod());
        logger.debug("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.debug("Request Args   : {}", JSONObject.toJSON(joinPoint.getArgs()));
    }

    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.debug("=========================================== End ===========================================" + NEW_LINE);
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();

        logger.debug("Response Args  : {}", JSONObject.toJSON(result));
        logger.debug("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

        return result;
    }

    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }

}
