package in.py.main.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	  
	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	        
	        // Smart routing: If the error message mentions "not found", return a 404 status.
	        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("not found")) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	        }
	        
	        // Otherwise, it's a rule violation, return a 400 Bad Request status.
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }

	    /**
	     * 2. Handles @Valid validation failures from your DTOs (e.g., missing fields)
	     * This automatically turns ugly Java validation errors into a clean JSON map!
	     */
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	    }

	    /**
	     * 3. The Ultimate Safety Net: Catches any other unexpected server crashes (NullPointers, Database drops)
	     */
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneralException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An unexpected internal server error occurred.");
	    }

}
