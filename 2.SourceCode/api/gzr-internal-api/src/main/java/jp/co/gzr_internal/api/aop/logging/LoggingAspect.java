/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.aop.logging;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;
import org.zalando.problem.DefaultProblem;

import io.github.jhipster.config.JHipsterConstants;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.util.ApiResponseUtil;
import jp.co.gzr_internal.api.web.rest.util.ErrorResponseUtil;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class LoggingAspect {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** The env. */
    private final Environment env;

    /** The request. */
    @Autowired(required=false)
    private HttpServletRequest request;

    /**
     * Instantiates a new logging aspect.
     *
     * @param env the env
     */
    public LoggingAspect(Environment env) {
        this.env = env;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(jp.co.gzr_internal.api.repository..*)"+
        " || within(jp.co.gzr_internal.api.service..*)"+
        " || within(jp.co.gzr_internal.api.web.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("execution(* jp.co.gzr_internal.api.repository.*.*(..)) ")
    public void timeExecuteSql() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches send mail, services and Web REST endpoints.
     */
    @Pointcut("execution(public * jp.co.gzr_internal.api.service..send*(..))")
    public void timeExecuteSendMail() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    /**
     * Pointcut that matches controller
     */
    @Pointcut("execution(public * jp.co.gzr_internal.api.web.rest..execute*(..))")
    public void executeApi() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches validation
     */
    @Pointcut("execution(public * jp.co.gzr_internal.api.web.rest.errors..handleMethod*(..))")
    public void responseValidation() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches error
     */
    @Pointcut("execution(public * org.zalando.problem.spring.web.advice.security..handleAuthentication(..))")
    public void responseError() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {

        if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL", e.getMessage(), e);

        } else {
            log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("timeExecuteSendMail()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start(joinPoint.toLongString());
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            stopWatch.stop();
            TaskInfo taskInfo = stopWatch.getLastTaskInfo();
            log.info("Method: " + taskInfo.getTaskName());
            log.info("Total time: " + taskInfo.getTimeMillis() + " ms.");
        }
    }


    /**
     * Log time execute.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("timeExecuteSql()")
    public Object logTimeExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(joinPoint.toLongString());
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            stopWatch.stop();
            TaskInfo taskInfo = stopWatch.getLastTaskInfo();
            log.info("Method: " + taskInfo.getTaskName());
            log.info("Total time: " + taskInfo.getTimeMillis() + " ms.");
        }
    }

    /**
     * Log execute api.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("executeApi()")
    public Object logExecuteApi(ProceedingJoinPoint joinPoint) throws Throwable {

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("\n======================================START======================================");
        log.info("Request :[ Method :{} -- URL : {}://{}:{}{} ]", request.getMethod(), protocol, request.getServerName(),
            request.getServerPort(), request.getRequestURI());
        log.info("Controller: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("Parameter: {}", Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            logResponse(result);
            log.info("\n======================================END======================================");
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Log response Validation
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("responseValidation()")
    public Object logResponseValidation(ProceedingJoinPoint joinPoint) throws Throwable {

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("\n======================================START======================================");
        log.info("Request :[ Method :{} -- URL : {}://{}:{}{} ]", request.getMethod(), protocol, request.getServerName(),
            request.getServerPort(), request.getRequestURI());
        log.info("Function: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("Parameter: {}", Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            logResponse(result);
            log.info("\n======================================END======================================");
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Log response error
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("responseError()")
    public Object logResponseErrors(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            Object result = joinPoint.proceed();
            logResponse(result);
            log.info("\n======================================END======================================");
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     *
     * @param obj
     */
    @SuppressWarnings("unchecked")
    private <T> void logResponse(T obj) {

        Map<String, Object> mapRes = new LinkedHashMap<>();
        ResponseEntity<T> response = (ResponseEntity<T>) obj;

        if(response.getBody() instanceof DefaultProblem) {
            DefaultProblem problem = (DefaultProblem) response.getBody();
            mapRes = problem.getParameters();
        } else {
            if(response.getBody() instanceof UrlResource) {
                UrlResource url = (UrlResource) response.getBody();
                log.info("\n----------------------------Response-----------------------\n\t" +
                    "Status: {} \n\t" +
                    "FileName: {} \n\t" +
                    "FileNameS3: {} \n----------------------------------------------------------",
                    response.getStatusCodeValue(), response.getHeaders().getContentDisposition(), url.getURL());
            } else {
                mapRes =  (Map<String, Object>) response.getBody();
            }
        }

        if(mapRes.size() > 0) {
            for(Map.Entry<String, Object> entry : mapRes.entrySet()){

                ApiResponseUtil res = (ApiResponseUtil) entry.getValue();
                String error = Contants.CONST_STR_BLANK;

                if(res.getErrorData() != null) {
                    ErrorResponseUtil err = (ErrorResponseUtil) res.getErrorData().get(0);
                    error = "\n\t[{\n\t"
                        + "\t ErrorCode: "+ err.getErrorCode() +" \n\t"
                        + "\t Message: "+ err.getMessage() +" \n\t"
                        + "\t FieldName: "+ err.getFieldName() +" \n\t"
                        + "}]";
                }

                log.info("\n----------------------------Response-----------------------\n\t" +
                    "Status: {} \n\t" +
                    "ErrorData: {} \n\t" +
                    "ErrorCode: {} \n\t" +
                    "Message: {} \n----------------------------------------------------------",
                    response.getStatusCodeValue(), error, res.getErrorCode(), res.getMessage());
            }
        }
    }
}
