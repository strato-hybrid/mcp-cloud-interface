package kr.strato.cloudinterface.common.error.exception;

import kr.strato.cloudinterface.common.error.enums.ErrorType;

public class FailTokenIssuanceException extends RuntimeException{
    ErrorType errorType = ErrorType.FAIL_TOKEN_ISSUANCE;

    public FailTokenIssuanceException(){
        super(ErrorType.FAIL_TOKEN_ISSUANCE.getMessage());
    }

    public FailTokenIssuanceException(String detail){
        super(ErrorType.FAIL_TOKEN_ISSUANCE.getMessage());
        errorType.setDetail(detail);
    }

    public FailTokenIssuanceException(Throwable cause){
        super(cause.getMessage(), cause);
        errorType.setDetail(cause.getMessage());
    }

    public FailTokenIssuanceException(String detail, Throwable cause){
        super(cause.getMessage(), cause);
        errorType.setDetail(detail);
    }

    public ErrorType getErrorType(){
        return errorType;
    }
}
