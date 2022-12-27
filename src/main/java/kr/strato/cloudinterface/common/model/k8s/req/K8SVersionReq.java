package kr.strato.cloudinterface.common.model.k8s.req;

import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.common.model.auth.res.TokenIssueRes;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

@Getter
public class K8SVersionReq extends CommonReq {
    private String regionName;
    private String zoneName;

    @Builder(builderMethodName = "dtoBuilder")
    private K8SVersionReq(TokenIssueReq tokenIssueReq, TokenIssueRes tokenIssueRes, RestTemplate restTemplate, Object naverQueryParamDto, Object azureQueryParamDto, String regionName, String zoneName){
        this.setTokenIssueReq(tokenIssueReq);
        this.setTokenIssueRes(tokenIssueRes);
        this.setRestTemplate(restTemplate);
        this.setNaverQueryParamDto(naverQueryParamDto);
        this.setAzureQueryParamDto(azureQueryParamDto);
        this.regionName = regionName;
        this.zoneName = zoneName;
    }

    public static K8SVersionReqBuilder builder(TokenIssueReq tokenIssueReq) {
        return dtoBuilder().tokenIssueReq(tokenIssueReq);
    }

    public static class K8SVersionReqBuilder {
        public K8SVersionReqBuilder regionName(String regionName){
            this.regionName = regionName;
            if(tokenIssueReq != null)
                this.tokenIssueReq.setRegion(regionName);

            return this;
        }

        public K8SVersionReqBuilder zoneName(String zoneName){
            this.zoneName = zoneName;
            return this;
        }
    }
}
