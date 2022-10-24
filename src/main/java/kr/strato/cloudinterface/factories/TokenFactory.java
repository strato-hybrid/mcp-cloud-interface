package kr.strato.cloudinterface.factories;

import kr.strato.cloudinterface.common.config.CommonEnums;
import kr.strato.cloudinterface.core.aws.token.service.AwsTokenService;
import kr.strato.cloudinterface.core.azure.auth.service.AzureAuthService;
import kr.strato.cloudinterface.core.google.auth.service.GoogleAuthService;
import kr.strato.cloudinterface.core.naver.token.service.NaverTokenService;
import kr.strato.cloudinterface.interfaces.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory {

    @Autowired
    private AwsTokenService awsTokenService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private NaverTokenService naverAuthService;

    @Autowired
    private AzureAuthService azureAuthService;


    public TokenService cloudTokenService(String type) {
        if (type.toUpperCase().equals(CommonEnums.CloudType.AWS.getValue())) {
            return awsTokenService;
        } else if (type.toUpperCase().equals(CommonEnums.CloudType.AZURE.getValue())) {
            return azureAuthService;
        } else if (type.toUpperCase().equals(CommonEnums.CloudType.GOOGLE.getValue())) {
            return googleAuthService;
        } else if (type.toUpperCase().equals(CommonEnums.CloudType.NAVER.getValue())){
            return naverAuthService;
        }
        return null;
    }
}
