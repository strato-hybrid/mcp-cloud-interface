package kr.strato.cloudinterface.common.error.exception;

import kr.strato.cloudinterface.common.error.enums.ErrorType;

public class MissingRequiredValueException extends RuntimeException{
    ErrorType errorType = ErrorType.MISSING_REQUIRED_VALUE;

    public MissingRequiredValueException(){
        super(ErrorType.MISSING_REQUIRED_VALUE.getMessage());
    }

    public MissingRequiredValueException(String detail){
        super(ErrorType.MISSING_REQUIRED_VALUE.getMessage());
        errorType.setDetail(detail);
    }

    public MissingRequiredValueException(Throwable cause){
        super(cause.getMessage(), cause);
        errorType.setDetail(cause.getMessage());
    }

    public MissingRequiredValueException(String detail, Throwable cause){
        super(cause.getMessage(), cause);
        errorType.setDetail(detail);
    }

    public ErrorType getErrorType(){
        return errorType;
    }
}
