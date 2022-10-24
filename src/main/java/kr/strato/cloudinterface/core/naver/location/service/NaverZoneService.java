package kr.strato.cloudinterface.core.naver.location.service;

import com.ntruss.apigw.ncloud.model.GetZoneListResponse;
import kr.strato.cloudinterface.common.error.exception.NotFoundResourceException;
import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.location.req.RegionReq;
import kr.strato.cloudinterface.common.model.location.req.ZoneReq;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import kr.strato.cloudinterface.common.model.location.res.ZoneRes;
import kr.strato.cloudinterface.interfaces.common.config.APIConfig;
import kr.strato.cloudinterface.interfaces.location.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NaverZoneService implements ZoneService {

    @Autowired NaverRegionService regionService;

    NaverZoneService naverZoneService;

    public NaverZoneService(@Lazy NaverZoneService naverZoneService){
        this.naverZoneService = naverZoneService;
    }

    private static final String requestZonesUrl = "https://ncloud.apigw.ntruss.com/server/v2/getZoneList";




    /**
     * 특정 리전에 대한 존 조회
     *
     * @param zoneReq : "https://ncloud.apigw.ntruss.com/server/v2/getZoneList?regionNo={regionNo}"
     * @return
     * @throws IOException
     */
//    @APIConfig(url = NaverApiV2.GET_NULL)
        // TODO ZoneNo에 따라 조회할 수 있도록 수정 필요
    @APIConfig(reqUrl = requestZonesUrl)
    @Override
    public List<ZoneRes> getZones(ZoneReq zoneReq) throws IOException {
        RestTemplate restTemplate = zoneReq.getRestTemplate();
        String requestUrl = Stream.of(zoneReq.getTokenIssueReq().getRequestUrl(), zoneReq.getTokenIssueReq().getQueryParameter())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(""));
        ResponseEntity<GetZoneListResponse> response =
                restTemplate.getForEntity(requestUrl, GetZoneListResponse.class);
        try {
            return Objects.requireNonNull(response.getBody()).getZoneList().stream().map(e -> ZoneRes.builder()
                    .zoneKey(e.getZoneCode())
                    .zoneName(e.getZoneNo())
                    .zoneDisplayName(e.getZoneName())
                    .description(e.getZoneDescription())
                    .regionKey(e.getRegionNo())
                    .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
