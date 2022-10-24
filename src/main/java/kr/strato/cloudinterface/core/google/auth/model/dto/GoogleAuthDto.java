package kr.strato.cloudinterface.core.google.auth.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleAuthDto {
    private String type;
    private String projectId;
    private String privateKeyId;
    private String privateKey;
    private String clientEmail;
    private String clientId;
    private String clientSecret;
    private String authUri;
    private String tokenUri;
    private String authProviderUrl;
    private String clientCertUrl;

    private String zoneId;

}
