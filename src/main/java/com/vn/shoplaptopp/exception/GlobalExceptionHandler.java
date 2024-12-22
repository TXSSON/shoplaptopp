package com.vn.shoplaptopp.exception;

import com.vn.shoplaptopp.domain.dto.response.ApiResponse;
import com.vn.shoplaptopp.exception.exceptioncustom.RegisterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception) {
//        ApiResponse apiResponse = new ApiResponse<>();
//        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
//        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }


    @ExceptionHandler(value = RegisterException.class)
    public ResponseEntity<ApiResponse> handlingRegisterException(RegisterException registerException) {
        ErrorCode errorCode = registerException.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String handlingNoHandlerFoundException(NoHandlerFoundException noHandlerFoundException) {
        return "error/404/show";
    }
}
