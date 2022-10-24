package kr.strato.cloudinterface.common.model.auth.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpHeaders;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TokenIssueRes {
    private HttpHeaders credentialsHeader;      // RestTemplate 에 포함 시킬 인증 Header 값
    private Object credentials;                 // 각 서비스별 SDK의 인증 객체

    public <T> T getCredentials() {
        return (T) credentials;
    }

    public <T> void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public HttpHeaders getCredentialsHeader() {
        return credentialsHeader;
    }
    public void setCredentialsHeader(HttpHeaders httpHeaders) {
        this.credentialsHeader = httpHeaders;
    }
}
