package kr.strato.cloudinterface.common.model;

import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.common.model.auth.res.TokenIssueRes;
import lombok.*;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonReq {
    @NonNull
    private TokenIssueReq tokenIssueReq;
    private TokenIssueRes tokenIssueRes;
    private RestTemplate restTemplate;

    public Object naverQueryParamDto;
    public Object azureQueryParamDto;
    public Object awsQueryParamDto;

    public <T> T getAzureQueryParamDto() {
        return (T) azureQueryParamDto;
    }

    public <T> void setAzureQueryParamDto(T azureQueryParamDto) {
        this.azureQueryParamDto = azureQueryParamDto;
    }

    public <T> T getNaverQueryParamDto() {
        return (T) naverQueryParamDto;
    }

    public <T> void setNaverQueryParamDto(T naverQueryParamDto) {
        this.naverQueryParamDto = naverQueryParamDto;
    }

    public <T> T getAwsQueryParamDto() {
        return (T) awsQueryParamDto;
    }

    public <T> void setAwsQueryParamDto(T awsQueryParamDto) {
        this.awsQueryParamDto = awsQueryParamDto;
    }
}
