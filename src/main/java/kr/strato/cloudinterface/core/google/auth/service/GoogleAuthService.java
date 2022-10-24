package kr.strato.cloudinterface.core.google.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.core.google.auth.model.res.AccessTokenRes;
import kr.strato.cloudinterface.interfaces.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;


@Service
public class GoogleAuthService implements TokenService {

    @Autowired(required=true)
    private RestTemplate restTemplate;

    /**
     * jwt 발급
     * @return signedJwt
     * @throws IOException
     */

    public String createJwtPayload(TokenIssueReq tokenDto) throws IOException {
        // gcp serviceName = type
        String signedJwt = null;
        String serviceAccount = "{\n" +
                "  \"type\": \""+tokenDto.getServiceName()+"\",\n" +
                "  \"project_id\": \""+ tokenDto.getServiceName() +"\",\n" +
                "  \"private_key_id\": \""+tokenDto.getPrivateKeyId()+"\",\n" +
                "  \"private_key\": \""+tokenDto.getPrivateKey()+"\",\n" +
                "  \"client_email\": \""+tokenDto.getClientEmail()+"\",\n" +
                "  \"client_id\": \""+tokenDto.getClientId()+"\",\n" +
                "  \"auth_uri\": \""+tokenDto.getAuthUri()+"\",\n" +
                "  \"token_uri\": \""+tokenDto.getTokenUri()+"\",\n" +
                "  \"auth_provider_x509_cert_url\": \""+tokenDto.getAuthProviderUrl()+"\",\n" +
                "  \"client_x509_cert_url\": \""+tokenDto.getClientCertUrl()+"\"\n" +
                "}\n";

        InputStream is = new ByteArrayInputStream(serviceAccount.getBytes());
        GoogleCredential credential = GoogleCredential.fromStream(is);
        PrivateKey privateKey = credential.getServiceAccountPrivateKey();
        Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) privateKey);

        long now = System.currentTimeMillis();

        try {

            signedJwt = JWT.create()
                    .withKeyId(tokenDto.getPrivateKeyId())
                    .withIssuer(tokenDto.getClientEmail())
                    .withClaim("scope", "https://www.googleapis.com/auth/cloud-platform")
                    .withAudience("https://oauth2.googleapis.com/token")
                    .withIssuedAt(new Date(now))
                    .withExpiresAt(new Date(now + 3600 * 1000L))
                    .sign(algorithm);

            return signedJwt;

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * jwt 이용한 google AccessToken 발급
     *
     * @return AccessToken
     * @throws IOException
     */
    public ResponseEntity<AccessTokenRes> getAccessToken(TokenIssueReq tokenDto) throws IOException {
        AccessTokenRes accessTokenRes = new AccessTokenRes();
        String signedJwt = createJwtPayload(tokenDto);
        String url = "https://oauth2.googleapis.com/token";


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("assertion", signedJwt);
        params.add("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, AccessTokenRes.class);
    }


    @Override
    public HttpHeaders authorizationHeader(TokenIssueReq tokenDto) throws Exception {
        ResponseEntity<AccessTokenRes> accessTokenRes =  getAccessToken(tokenDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json; charset=UTF-8");
        headers.add("Authorization", "Bearer " + accessTokenRes.getBody().getAccess_token());
        return headers;
    }





    @Override
    public CredentialsProvider credentialsProvider(TokenIssueReq params) throws IOException {
        String serviceAccount = "{\n" +
                "  \"type\": \""+params.getType()+"\",\n" +
                "  \"project_id\": \""+ params.getOptionId_1() +"\",\n" +
                "  \"private_key_id\": \""+params.getPrivateKeyId()+"\",\n" +
                "  \"private_key\": \""+params.getPrivateKey()+"\",\n" +
                "  \"client_email\": \""+params.getClientEmail()+"\",\n" +
                "  \"client_id\": \""+params.getClientId()+"\",\n" +
                "  \"auth_uri\": \""+params.getAuthUri()+"\",\n" +
                "  \"token_uri\": \""+params.getTokenUri()+"\",\n" +
                "  \"auth_provider_x509_cert_url\": \""+params.getAuthProviderUrl()+"\",\n" +
                "  \"client_x509_cert_url\": \""+params.getClientCertUrl()+"\"\n" +
                "}\n";

        InputStream is = new ByteArrayInputStream(serviceAccount.getBytes());
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        GoogleCredentials credentials = GoogleCredentials.fromStream(is)
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        CredentialsProvider credentialsProvider = new CredentialsProvider() {
            @Override
            public Credentials getCredentials() throws IOException {
                return credentials;
            }
        };

        return credentialsProvider;

    }
}
