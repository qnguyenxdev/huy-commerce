package com.qnguyenxdev.common.exception;

import com.qnguyenxdev.common.configuration.Translator;
import com.qnguyenxdev.common.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception when validate data
     *
     * @param e
     * @param request
     * @return errorResponse
     */
    @ExceptionHandler({ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Handle exception when the data invalid. (@RequestBody, @RequestParam, @PathVariable)",
                                    summary = "Handle Bad Request",
                                    value = """
                                            {
                                                 "timestamp": "2024-04-07T11:38:56.368+00:00",
                                                 "status": 400,
                                                 "path": "/api/v1/...",
                                                 "error": "Invalid Payload",
                                                 "message": "{data} must be not blank"
                                             }
                                            """
                            ))})
    })
    public ErrorResponse handleValidationException(Exception e, WebRequest request) {
        log.error(e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setPath(buildErrorPath(request));

        String message = e.getMessage();
        if (e instanceof ConstraintViolationException) {
            errorResponse.setError(Translator.toLocale("error.global.invalid.parameter"));
            errorResponse.setMessage(message.substring(message.indexOf(" ") + 1));
        } else if (e instanceof MissingServletRequestParameterException) {
            errorResponse.setError(Translator.toLocale("error.global.invalid.parameter"));
            errorResponse.setMessage(message);
        } else if (e instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[") + 1;
            int end = message.lastIndexOf("]") - 1;
            message = message.substring(start, end);
            errorResponse.setError(Translator.toLocale("error.global.invalid.payload"));
            errorResponse.setMessage(message);
        } else {
            errorResponse.setError("error.global.invalid.data");
            errorResponse.setMessage(message);
        }

        return errorResponse;
    }

//    /**
//     * Handle exception when user not authenticated
//     *
//     * @param e
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(InternalAuthenticationServiceException.class)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "401", description = "Unauthorized",
//                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
//                            examples = @ExampleObject(
//                                    name = "401 Response",
//                                    summary = "Handle exception when resource not found",
//                                    value = """
//                                            {
//                                              "timestamp": "2023-10-19T06:07:35.321+00:00",
//                                              "status": 401,
//                                              "path": "/api/v1/...",
//                                              "error": "Unauthorized",
//                                              "message": "Username or password is incorrect"
//                                            }
//                                            """
//                            ))})
//    })

    /**
     * Handle exception when the request not found data
     *
     * @param e
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "403 Response",
                                    summary = "Handle exception when access forbidden",
                                    value = """
                                            {
                                              "timestamp": "2023-10-19T06:07:35.321+00:00",
                                              "status": 403,
                                              "path": "/api/v1/...",
                                              "error": "Forbidden",
                                              "message": "You do not have permission to access this resource"
                                            }
                                            """
                            ))})
    })
    public ErrorResponse handleAccessDeniedException(Exception e, WebRequest request) {
        log.error(e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(FORBIDDEN.value());
        errorResponse.setPath(buildErrorPath(request));
        errorResponse.setError(Translator.toLocale("error.global.forbidden"));
        errorResponse.setMessage(e.getMessage());

        return errorResponse;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "503 Response",
                                    summary = "Image upload failed",
                                    value = """
                    {
                      "timestamp": "2023-10-19T06:07:35.321+00:00",
                      "status": 503,
                      "path": "/api/v1/product/upload",
                      "error": "Service Unavailable",
                      "message": "Failed to upload image"
                    }
                """
                            )
                    )
            )
    })
    @ExceptionHandler(value = AppException.class)
    public ErrorResponse handlingAppException(AppException e, WebRequest request) {
        log.error(e.getMessage());

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus((errorCode.getCode()));
        errorResponse.setPath(buildErrorPath(request));
        errorResponse.setError(errorCode.getStatus().getReasonPhrase());
        errorResponse.setMessage(Translator.toLocale(errorCode.getMessage()));

        return errorResponse;
    }


    /**
     * Handle exception when internal server error
     *
     * @param e
     * @param request
     * @return error
     */
    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "9999", description = "Internal Server Error",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "9999 Response",
                                    summary = "Handle exception when internal server error",
                                    value = """
                                            {
                                              "timestamp": "2023-10-19T06:35:52.333+00:00",
                                              "status": 9999,
                                              "path": "/api/v1/...",
                                              "error": "Internal Server Error",
                                              "message": "Connection timeout, please try again"
                                            }
                                            """
                            ))})
    })
    public ErrorResponse handleException(Exception e, WebRequest request) {
        log.error(e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(buildErrorPath(request));
        errorResponse.setStatus(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        errorResponse.setError(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatus().getReasonPhrase());
        errorResponse.setMessage(Translator.toLocale(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()));

        return errorResponse;
    }
    
    private String buildErrorPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
