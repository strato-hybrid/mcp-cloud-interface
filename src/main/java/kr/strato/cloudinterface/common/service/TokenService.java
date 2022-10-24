package kr.strato.cloudinterface.common.service;

import kr.strato.cloudinterface.common.config.CommonEnums;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.common.model.auth.res.TokenIssueRes;
import kr.strato.cloudinterface.factories.TokenFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TokenService {

    @Autowired
    private TokenFactory cloudTokenFactory;

    public TokenIssueRes getApiTokenIssue(TokenIssueReq tokenDto, String requestType) throws Exception {
        String cloudType = tokenDto.getCloudType();

        TokenIssueRes result = new TokenIssueRes();

        try {
            kr.strato.cloudinterface.interfaces.token.TokenService tokenService = cloudTokenFactory.cloudTokenService(cloudType);

            if (requestType.equals(CommonEnums.RequestType.API.getValue())) {
                result.setCredentialsHeader(tokenService.authorizationHeader(tokenDto));
            } else {
                result.setCredentials(tokenService.credentialsProvider(tokenDto));
            }

        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }


}
