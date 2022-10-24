package kr.strato.cloudinterface.core.google.location.service;

import com.google.cloud.compute.v1.Zone;
import com.google.cloud.compute.v1.ZonesClient;
import com.google.cloud.compute.v1.ZonesSettings;
import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.location.req.ZoneReq;
import kr.strato.cloudinterface.common.model.location.res.ZoneRes;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import kr.strato.cloudinterface.interfaces.location.ZoneService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class GoogleZoneService implements ZoneService {

    /**
     * zone 객체
     * sdk경우 items 제외하고 바로 호출 가능 ex) zone.getRegion();
     * sample: { kind=compute#zoneList, id=projects/mindful-future-353103/zones, items[ id=2231, name=us-east1-b, region=https://www.googleapis.com/compute/v1/projects/mindful-future-353103/regions/us-east1 ] }
     */
    private static final String AUTH_URI = "https://accounts.google.com/o/oauth2/auth";
    private static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String AUTH_PROVIDER_URL= "https://www.googleapis.com/oauth2/v1/certs";

    @SDKConfig(type = "service_account", authUri = AUTH_URI, tokenUri = TOKEN_URI,authProviderUrl = AUTH_PROVIDER_URL)
    @Override
    public List<ZoneRes> getZones(ZoneReq zoneReq) throws IOException {
        List<ZoneRes> result =  new ArrayList<>();
        ZonesSettings zonesSettings=  ZonesSettings.newBuilder().setCredentialsProvider(zoneReq.getTokenIssueRes().getCredentials()).build();

        try (ZonesClient zonesClient = ZonesClient.create(zonesSettings)) {
            System.out.printf("Listing Zone from %s:", zoneReq.getTokenIssueReq().getOptionId_1());
            //log.info("Listing Zone from %s:", projectId);
            for (Zone zone : zonesClient.list(zoneReq.getTokenIssueReq().getOptionId_1()).iterateAll()) {
                String region = zone.getRegion().substring(zone.getRegion().lastIndexOf('/')+1);
                ZoneRes zoneRes = ZoneRes.builder()
                        .zoneKey(String.valueOf(zone.getId()))
                        .zoneName(zone.getName())
                        .zoneDisplayName(zone.getName())
                        .status(zone.getStatus())
                        .endpoint(zone.getSelfLink())
                        .regionKey(region)
                        .build();
//                System.out.println(zone.getName());
                result.add(zoneRes);
            }
            log.info("####### Listing Zone complete #######");
        }
        return result;
    }
}
