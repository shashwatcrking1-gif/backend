package com.civicshield.api;
import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;import org.springframework.web.servlet.NoHandlerFoundException;import java.time.Instant;import java.util.stream.Collectors;import jakarta.servlet.http.HttpServletRequest;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ApiModels.ErrorResponse> bad(IllegalArgumentException e,HttpServletRequest r){return response(400,"Bad Request",e.getMessage(),r);}
 @ExceptionHandler(IllegalStateException.class) ResponseEntity<ApiModels.ErrorResponse> conflict(IllegalStateException e,HttpServletRequest r){return response(409,"Conflict",e.getMessage(),r);}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiModels.ErrorResponse> validation(MethodArgumentNotValidException e,HttpServletRequest r){String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining("; "));return response(400,"Validation failed",m,r);}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiModels.ErrorResponse> internal(Exception e,HttpServletRequest r){return response(500,"Internal Server Error","Request could not be completed",r);}
 private ResponseEntity<ApiModels.ErrorResponse> response(int s,String e,String m,HttpServletRequest r){return ResponseEntity.status(s).body(new ApiModels.ErrorResponse(Instant.now(),s,e,m,r.getRequestURI()));}
}
