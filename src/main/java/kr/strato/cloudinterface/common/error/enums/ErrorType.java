package kr.strato.cloudinterface.common.error.enums;

public enum ErrorType {
    FAIL_TOKEN_ISSUANCE("20001", "토큰 발급에 실패하였습니다.", null),
    NOT_FOUND_RESOURCE("30001", "리소스 조회에 실패하였습니다.", null),
    MISSING_REQUIRED_VALUE("30002", "필수 값이 누락되었습니다.", null);

    private String code;
    private String message;
    private String detail;

    private String value;

    ErrorType(String code, String message, String detail){
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }

    public String getMessage(){
        return this.message;
    }

    public String getCode(){
        return this.code;
    }

    public String getDetail(){
        return this.detail;
    }
}
