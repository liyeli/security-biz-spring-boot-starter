package org.springframework.security.boot.biz.exception;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of Error types.
 */
public enum ErrorCode {
	
    GLOBAL(2),

    AUTHENTICATION(10), 
    
    IDENTITY(11), 
    
    TOKEN(12);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
