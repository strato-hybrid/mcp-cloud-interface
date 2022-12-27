package kr.strato.cloudinterface.common.model.k8s.req;


import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.common.model.auth.res.TokenIssueRes;
import lombok.Builder;
import org.springframework.web.client.RestTemplate;

public class K8SImageReq extends CommonReq {
    @Builder
    private K8SImageReq(TokenIssueReq tokenIssueReq, TokenIssueRes tokenIssueRes, RestTemplate restTemplate, Object naverQueryParamDto, Object azureQueryParamDto){
        this.setTokenIssueReq(tokenIssueReq);
        this.setTokenIssueRes(tokenIssueRes);
        this.setRestTemplate(restTemplate);
        this.setNaverQueryParamDto(naverQueryParamDto);
        this.setAzureQueryParamDto(azureQueryParamDto);
    }


    public static class K8SVersionReqBuilder {

    }
}
