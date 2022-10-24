package kr.strato.cloudinterface.core.naver.location.service;

import com.ntruss.apigw.ncloud.model.GetRegionListResponse;
import kr.strato.cloudinterface.common.model.location.req.RegionReq;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import kr.strato.cloudinterface.core.naver.NaverApiV2;
import kr.strato.cloudinterface.interfaces.common.config.APIConfig;
import kr.strato.cloudinterface.interfaces.location.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NaverRegionService implements RegionService {

    @APIConfig(reqUrl = "https://ncloud.apigw.ntruss.com/server/v2/getRegionList")
    @Override
    public List<RegionRes> getRegions(RegionReq commonDto){
        String url = NaverApiV2.GET_REGION_LIST.getUrl();

        RestTemplate restTemplate = commonDto.getRestTemplate();
        ResponseEntity<GetRegionListResponse> response = restTemplate.getForEntity(
                commonDto.getTokenIssueReq().getRequestUrl(),
                GetRegionListResponse.class);

        List<RegionRes> regions = response.getBody().getRegionList().stream().map(r -> RegionRes.builder()
                .regionKey(r.getRegionNo())
                .regionName(r.getRegionCode())
                .regionDisplayName(r.getRegionName())
                .build()
        ).collect(Collectors.toList());

        return regions;
    }
}
