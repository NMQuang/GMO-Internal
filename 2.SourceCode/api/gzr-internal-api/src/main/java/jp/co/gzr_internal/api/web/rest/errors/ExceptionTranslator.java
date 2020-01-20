/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.errors;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.util.ApiResponseUtil;
import jp.co.gzr_internal.api.web.rest.util.ErrorResponseUtil;
import jp.co.gzr_internal.api.web.rest.validator.MessageInterpolatorContext;
import jp.co.gzr_internal.api.web.rest.validator.NoFallbackControlResourceBundleLocator;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The error response
 * follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

    /** The message source. */
    @Autowired
    MessageSource messageSource;

    /**
     * Process.
     *
     * @param entity the entity
     * @param request the request
     * @return the response entity
     */
    /* (non-Javadoc)
     * @see org.zalando.problem.spring.web.advice.AdviceTrait#process(org.springframework.http.ResponseEntity, org.springframework.web.context.request.NativeWebRequest)
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {

        if (entity == null) {
            return entity;
        }

        Problem problem = entity.getBody();
        ProblemBuilder builder = Problem.builder();

        try {

            /* Checks if the body entity is defaultProblem and is file too large exception.
             * 
             * @author DatNT
             * @time 07/05/2019
             */
            if (entity.getBody() instanceof DefaultProblem && isFileTooLargeException(problem)) {
                ApiResponseUtil response = ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_IMAGE_SIZE);
                builder.with(Contants.CONST_STR_RESPONSE, response);
                return new ResponseEntity<>(builder.build(), entity.getHeaders(), HttpStatus.BAD_REQUEST);
            }

            // Handler exception
            if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {

                ApiResponseUtil response = handlerException(problem);
                builder.with(Contants.CONST_STR_RESPONSE, response);
                return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
            }

            if (problem instanceof ConstraintViolationProblem) {
                builder.with("violations", ((ConstraintViolationProblem) problem).getViolations()).with("message",
                    ErrorConstants.ERR_VALIDATION);
            } else {

                ApiResponseUtil response;
                // Error when login not success
                if (problem.getStatus() != null
                    && problem.getStatus().getStatusCode() == MessageContants.CONST_STATUS_UNAUTHORIZED) {

                    response = ResponseCommon
                        .createResponseError(MessageContants.CONST_ERROR_CODE_EMAIL_OR_PASSWORD_NOT_MATCH);

                    builder.with(Contants.CONST_STR_RESPONSE, response);
                    return new ResponseEntity<>(builder.build(), entity.getHeaders(), HttpStatus.BAD_REQUEST);
                } else if (problem.getStatus() != null
                    && problem.getStatus().getStatusCode() == MessageContants.CONST_STATUS_METHOD_NOT_ALLOWED) {

                    // Handler error with status 405
                    response = ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_METHOD_NOT_ALLOWED);

                    builder.with(Contants.CONST_STR_RESPONSE, response);
                    return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
                } else if (problem.getStatus() != null
                    && problem.getStatus().getStatusCode() == MessageContants.CONST_STATUS_BAD_REQUEST) {

                    // Handler error with status 400
                    response = ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_BAD_REQUEST);

                    builder.with(Contants.CONST_STR_RESPONSE, response);
                    return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
                } else {

                    // Handler internal server error
                    response = ResponseCommon
                        .createResponseError(MessageContants.CONST_ERROR_CODE_INTERNAL_SERVER_ERROR);
                    builder.with(Contants.CONST_STR_RESPONSE, response);
                    return new ResponseEntity<>(builder.build(), entity.getHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
        }

        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    /**
     * Handle method argument not valid.
     *
     * @param ex the ex
     * @param request the request
     * @return the response entity
     */
    /* (non-Javadoc)
     * @see org.zalando.problem.spring.web.advice.validation.MethodArgumentNotValidAdviceTrait#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException, org.springframework.web.context.request.NativeWebRequest)
     */
    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();

        // Get first validate error
        List<ErrorResponseUtil> fieldErrors = result.getFieldErrors().stream().map(f -> {
            ErrorResponseUtil errorResponse = new ErrorResponseUtil();

            String message = null;

            try {
                // Get constraint violation from annotation constraint
                ConstraintViolationImpl<?> violation = f.unwrap(ConstraintViolationImpl.class);
                ResourceBundleMessageInterpolator interpolator = new ResourceBundleMessageInterpolator(new NoFallbackControlResourceBundleLocator());

                MessageInterpolatorContext messageInterpolatorContext = new MessageInterpolatorContext(violation);

                if (violation.getConstraintDescriptor() != null
                    && violation.getConstraintDescriptor().getAttributes() != null
                    && violation.getConstraintDescriptor().getAttributes().get(Contants.CONST_STR_ERROR_CODE) != null) {
                    errorResponse.setErrorCode((String) violation.getConstraintDescriptor().getAttributes().get(Contants.CONST_STR_ERROR_CODE));
                } else {
                    errorResponse.setErrorCode(f.getCode());

                }
                message = interpolator.interpolate(violation.getMessage(), messageInterpolatorContext);

                int retryCount = 0;
                // Check if there is exist parameter in message, re-interpolate again
                while (message.indexOf(Contants.CONST_STR_ANGLE_BRACKET) > -1 && retryCount <= 3) {
                    message = interpolator.interpolate(message, messageInterpolatorContext);
                    retryCount++;
                }
            } catch (IllegalArgumentException e) {
                // Error when received constraint violation from custom validator
                message = messageSource.getMessage(f.getCode(), f.getArguments(), null);
                errorResponse.setErrorCode(f.getCode());
            }

            errorResponse.setMessage(message);
            errorResponse.setFieldName(f.getField());
            return errorResponse;
        }).collect(Collectors.toList());

        ApiResponseUtil response = new ApiResponseUtil();
        response.setErrorData(fieldErrors);

        Problem problem = Problem.builder().with(Contants.CONST_STR_RESPONSE, response)
            .build();
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler exception.
     *
     * @param problem the problem
     * @return the api response util
     * @throws Exception the exception
     */
    private ApiResponseUtil handlerException(Problem problem) throws Exception {

        String titleProblem = problem.getTitle();

        switch (titleProblem) {
        case MessageContants.CONST_ERROR_CODE_INSERT_FAILURE:

            // Handler exception when insert data
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_INSERT_FAILURE);

        case MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND:

            // Handler exception when data not found
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND);

        case MessageContants.CONST_ERROR_CODE_SELECT_FAILURE:

            // Handler exception when data already exist
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_SELECT_FAILURE);

        case MessageContants.CONST_ERROR_CODE_UPDATE_FAILURE:

            // Handler exception when update data
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_UPDATE_FAILURE);

        case MessageContants.CONST_ERROR_CODE_EMAIL_NOT_REGISTER:

            // Handler exception when email not registered
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_EMAIL_NOT_REGISTER);

        case MessageContants.CONST_ERROR_CODE_MULTI_USER_ACTION:

            // Handler exception when multi user interactive to record
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_MULTI_USER_ACTION);
            
        case MessageContants.CONST_CODE_INSERT_DUPLICATE_EXCEPTION:

            // Handler exception when multi user create user with one email
            return ResponseCommon.createResponseError(MessageContants.CONST_CODE_INSERT_DUPLICATE_EXCEPTION);

        default:

            // Handler exception other error
            return ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Checks if detail of problem is file too large exception.
     * 
     * @author DatNT
     * @param problem the problem
     * @return true, if is file too large exception
     */
    private boolean isFileTooLargeException(Problem problem) {
        /* Checks if the problem or detail of problem is null or empty
         * then return false. */
        if (problem == null || StringValidation.isNullOrEmpty(problem.getDetail())) {
            return false;
        }

        /*
         * Split from the string 'io.undertow.server.handlers.form.MultiPartParserDefinition$FileTooLargeException'
         * to detect is the FileTooLargeException.
         */
        String[] detail = problem.getDetail().split("\\$");

        /* Checks if the exception is FileTooLargeException return true */
        if (detail.length == 2 && detail[1].trim().startsWith("FileTooLargeException")) {
            return true;
        }

        return false;
    }
}
