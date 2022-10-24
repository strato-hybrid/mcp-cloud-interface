package kr.strato.cloudinterface.core.naver.token.service;

import com.ntruss.apigw.ncloud.auth.Credentials;
import kr.strato.cloudinterface.common.error.exception.FailTokenIssuanceException;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.core.naver.token.model.res.NaverTokenRes;
import kr.strato.cloudinterface.interfaces.token.TokenService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class NaverTokenService implements TokenService {

    @Override
    public HttpHeaders authorizationHeader(TokenIssueReq tokenDto) throws Exception {
        NaverTokenRes naverTokenRes = getToken(tokenDto);

        HttpHeaders headers = new NaverTokenHeaderBuilder(naverTokenRes).build();
        return headers;
    }

    @Override
    public Credentials credentialsProvider(TokenIssueReq tokenDto) throws Exception {
        Credentials credentials =
                new NaverCredentialsService(null, tokenDto.getClientId(), tokenDto.getClientSecret());
        return credentials;
    }

    private static NaverTokenRes getToken(TokenIssueReq tokenIssueDto) {
        String space = " ";
        String newLine = "\n";
        String timestamp = Long.toString(System.currentTimeMillis());
        URI requestUrl = URI.create(tokenIssueDto.getRequestUrl());
        String joinUrl = Stream.of(requestUrl.getPath(), tokenIssueDto.getQueryParameter())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(""));


        String message = new StringBuilder()
                .append(tokenIssueDto.getMethod())
                .append(space)
                .append(joinUrl)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(tokenIssueDto.getClientId())
                .toString();

        SecretKeySpec signingKey =
                new SecretKeySpec(tokenIssueDto.getClientSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            String encodeBase64String = Base64.encodeBase64String(rawHmac);

            if(tokenIssueDto.getQueryParameter() == null){
                log.info("request URI: "+tokenIssueDto.getRequestUrl());
            }else{
                log.info("request URI: "+tokenIssueDto.getRequestUrl()+tokenIssueDto.getQueryParameter());
            }
            log.info("x-ncp-apigw-timestamp: "+timestamp);
            log.info("x-ncp-iam-access-key: "+tokenIssueDto.getClientId());
            log.info("x-ncp-apigw-signature-v2: "+encodeBase64String);

            NaverTokenRes tokenRes = NaverTokenRes.builder()
                    .timestamp(timestamp)
                    .accessKey(tokenIssueDto.getClientId())
                    .signature(encodeBase64String)
                    .build();

            return tokenRes;
        } catch (NoSuchAlgorithmException e) {
            throw new FailTokenIssuanceException("Ncp 토큰 발급에 실패하였습니다.", e);
        } catch (InvalidKeyException e) {
            throw new FailTokenIssuanceException("Ncp 토큰 발급에 실패하였습니다.", e);
        }
    }
}
