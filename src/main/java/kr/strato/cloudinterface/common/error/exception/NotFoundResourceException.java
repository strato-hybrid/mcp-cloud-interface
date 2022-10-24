package kr.strato.cloudinterface.common.error.exception;

import kr.strato.cloudinterface.common.error.enums.ErrorType;

public class NotFoundResourceException extends RuntimeException{
    ErrorType errorType = ErrorType.NOT_FOUND_RESOURCE;

    public NotFoundResourceException(){
        super(ErrorType.NOT_FOUND_RESOURCE.getMessage());
    }

    public NotFoundResourceException(String detail){
        super(ErrorType.NOT_FOUND_RESOURCE.getMessage());
        errorType.setDetail(detail);
    }

    public NotFoundResourceException(Throwable cause){
        super(cause.getMessage(), cause);
        errorType.setDetail(cause.getMessage());
    }

    public NotFoundResourceException(String detail, Throwable cause){
        super(cause.getMessage(), cause);
        errorType.setDetail(detail);
    }

    public ErrorType getErrorType(){
        return errorType;
    }
}
