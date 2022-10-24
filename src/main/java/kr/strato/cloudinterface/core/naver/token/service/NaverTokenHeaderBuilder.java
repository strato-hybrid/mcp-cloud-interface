package kr.strato.cloudinterface.core.naver.token.service;

import kr.strato.cloudinterface.core.naver.token.model.res.NaverTokenRes;
import org.springframework.http.HttpHeaders;

public class NaverTokenHeaderBuilder {
    private String timestamp;
    private String accessKey;
    private String signature;

    public NaverTokenHeaderBuilder(NaverTokenRes naverTokenRes){
        this.timestamp = naverTokenRes.getTimestamp();
        this.accessKey = naverTokenRes.getAccessKey();
        this.signature = naverTokenRes.getSignature();
    }

    public HttpHeaders build(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-timestamp", this.timestamp);
        headers.set("x-ncp-iam-access-key", this.accessKey);
        headers.set("x-ncp-apigw-signature-v2", this.signature);

        return headers;
    }
}
