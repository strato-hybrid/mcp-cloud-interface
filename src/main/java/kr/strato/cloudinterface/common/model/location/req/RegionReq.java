package kr.strato.cloudinterface.common.model.location.req;

import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.common.model.auth.res.TokenIssueRes;
import kr.strato.cloudinterface.core.naver.location.model.dto.NaverZoneDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

@Getter
public class RegionReq extends CommonReq {
    private String regionName;

    @Builder(builderMethodName = "dtoBuilder")
    private RegionReq(TokenIssueReq tokenIssueReq, TokenIssueRes tokenIssueRes, RestTemplate restTemplate, Object naverQueryParamDto, String regionName){
        this.setTokenIssueReq(tokenIssueReq);
        this.setTokenIssueRes(tokenIssueRes);
        this.setRestTemplate(restTemplate);
        this.setNaverQueryParamDto(naverQueryParamDto);
        this.regionName = regionName;
    }

    public static RegionReqBuilder builder(TokenIssueReq tokenIssueReq) {
        return dtoBuilder().tokenIssueReq(tokenIssueReq);
    }

    public static class RegionReqBuilder{

        public RegionReqBuilder regionName(String regionName){
            if(tokenIssueReq != null)
                this.tokenIssueReq.setRegion(regionName);

            return this;
        }
    }
}
