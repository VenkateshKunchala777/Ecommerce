package com.Kunchala.Ecommerce.Advice;

import com.Kunchala.Ecommerce.Exception.InsufficientStockException;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundHandler(ResourceNotFoundException exception) {
        ApiError er=ApiError.builder().
                 msg(exception.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return buildErrorResponseEntity(er);
    }

    private ResponseEntity<ApiResponse> buildErrorResponseEntity(ApiError er) {
         return new ResponseEntity<>(new ApiResponse(er),er.getStatus());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse> handleInsufficientStock(InsufficientStockException exception) {
        ApiError er = ApiError.builder()
                .msg(exception.getLocalizedMessage())
                .status(HttpStatus.CONFLICT)
                .build();
        return buildErrorResponseEntity(er);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ApiResponse> handleOptimisticLock(OptimisticLockingFailureException exception) {
        ApiError er = ApiError.builder()
                .msg("Order failed due to high demand. Please try again.")
                .status(HttpStatus.CONFLICT)
                .build();
        return buildErrorResponseEntity(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse> handler(MethodArgumentNotValidException exception) {
            List<String> er=exception.getBindingResult().getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList());
            ApiError ers=ApiError.builder()
                    .msg("Input Validation Failed!!")
                    .status(HttpStatus.BAD_REQUEST)
                    .subError(er)
                    .build();
            return buildErrorResponseEntity(ers);
        }

}
