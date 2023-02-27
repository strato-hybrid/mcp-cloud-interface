package kr.strato.cloudinterface.core.google.location.service;

import com.google.cloud.compute.v1.Region;
import com.google.cloud.compute.v1.RegionsClient;
import com.google.cloud.compute.v1.RegionsSettings;
import kr.strato.cloudinterface.common.model.location.req.RegionReq;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import kr.strato.cloudinterface.interfaces.location.RegionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleRegionService implements RegionService {


    private static final String AUTH_URI = "https://accounts.google.com/o/oauth2/auth";
    private static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String AUTH_PROVIDER_URL= "https://www.googleapis.com/oauth2/v1/certs";

    @SDKConfig(type = "service_account", authUri = AUTH_URI, tokenUri = TOKEN_URI,authProviderUrl = AUTH_PROVIDER_URL)
    @Override
    public List<RegionRes> getRegions(RegionReq params) throws IOException {
        List<RegionRes> result =  new ArrayList<>();
        RegionsSettings regionsSettings = RegionsSettings.newBuilder().setCredentialsProvider(params.getTokenIssueRes().getCredentials()).build();

        try (RegionsClient regionsClient = RegionsClient.create(regionsSettings)) {
//         System.out.printf("Listing Region from %s:", params.getTokenIssueReq().getOptionId_1());
            for (Region region : regionsClient.list(params.getTokenIssueReq().getOptionId_1()).iterateAll()) {
                System.out.println(region.getName());
                RegionRes regionRes = RegionRes.builder()
                        .regionKey(String.valueOf(region.getId()))
                        .regionName(region.getName())
                        .regionDisplayName(region.getName())
                        .description(region.getDescription())
                        .status(region.getStatus())
                        .endpoint(region.getSelfLink()).build();

                result.add(regionRes);
            }
            System.out.println("####### Listing Region complete #######");
        }
        return result;
    }
}
