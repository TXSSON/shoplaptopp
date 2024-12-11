package com.vn.shoplaptopp.exception.exceptioncustom;

import com.vn.shoplaptopp.exception.ErrorCode;

public class RegisterException extends Exception {


    private ErrorCode errorCode;

	public RegisterException(ErrorCode errorCode) {
		super(errorCode.getMessage());
        this.errorCode = errorCode;
	}

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
