package kr.strato.cloudinterface.common.model.auth.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "dtoBuilder")
@ToString
public class TokenIssueReq {
    private String cloudType;       // 퍼블릭 클라우드명

    private String clientId;        // accessKey
    private String clientSecret;    // secretKey
    private String privateKeyId;
    private String privateKey;

    private String requestUrl;
    private String queryParameter;
    private String method;
    private String region;

    private String optionId_1;      // tenantId, projectId
    private String optionId_2;      // subscriptionId

    private String serviceName;     // iam, ec2 등의 클라우드 서비스 명
    private String clientCertUrl;
    private String clientEmail;

    private String type;
    private String authUri;
    private String tokenUri;
    private String authProviderUrl;
    private String zone;

    private String scope;


    public static TokenIssueReqBuilder builder(String cloudType) {
        return dtoBuilder().cloudType(cloudType);
    }

    public static class TokenIssueReqBuilder {}
}
