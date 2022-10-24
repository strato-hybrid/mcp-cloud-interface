package kr.strato.cloudinterface.interfaces.token;

import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import org.springframework.http.HttpHeaders;

public interface TokenService {
    HttpHeaders authorizationHeader(TokenIssueReq tokenDto) throws Exception;
    <T> T credentialsProvider(TokenIssueReq tokenDto) throws Exception;
}
