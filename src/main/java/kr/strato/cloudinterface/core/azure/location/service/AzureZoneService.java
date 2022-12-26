package kr.strato.cloudinterface.core.azure.location.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.exception.ManagementException;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.resources.ResourceManager;
import com.azure.resourcemanager.resources.fluent.SubscriptionClient;
import com.azure.resourcemanager.resources.fluent.models.CheckZonePeersResultInner;
import com.azure.resourcemanager.resources.models.AvailabilityZonePeers;
import com.azure.resourcemanager.resources.models.CheckZonePeersRequest;
import com.azure.resourcemanager.resources.models.Location;
import kr.strato.cloudinterface.common.error.exception.NotFoundResourceException;
import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.location.req.ZoneReq;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import kr.strato.cloudinterface.common.model.location.res.ZoneRes;
import kr.strato.cloudinterface.core.azure.auth.model.dto.AzureCredentialDto;
import kr.strato.cloudinterface.core.azure.location.model.AzureRegionRes;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import kr.strato.cloudinterface.interfaces.location.ZoneService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AzureZoneService implements ZoneService {

    @SDKConfig
    @Override
    public List<ZoneRes> getZones(ZoneReq zoneReq) {
        AzureCredentialDto azureCredentialDto = zoneReq.getTokenIssueRes().getCredentials();
        List<RegionRes> regions = getRegions(azureCredentialDto);
        if(regions == null || regions.size() == 0){
            throw new NotFoundResourceException("azure region 조회 실패");
        }
        List<ZoneRes> zones = regions.stream().map(e-> getZonePeers(azureCredentialDto, e.getRegionName()))
                .flatMap(s -> s.stream()).collect(Collectors.toList());

        return zones;
    }

    private List<RegionRes> getRegions(AzureCredentialDto azureCredentialDto) {
        AzureResourceManager resourceManager = AzureResourceManager.authenticate(azureCredentialDto.getClientSecretCredential(), azureCredentialDto.getProfile()).withDefaultSubscription();
        PagedIterable<Location> pagedIterable = resourceManager.getCurrentSubscription().listLocations();

        return pagedIterable.stream().filter(location -> "Physical".equals(location.regionType().toString()))
                .map(location -> AzureRegionRes.toRegionRes(location)).collect(Collectors.toList());
    }

    private List<ZoneRes> getZonePeers(AzureCredentialDto azureCredentialDto, String regionName){
        String subscriptionId = azureCredentialDto.getProfile().getSubscriptionId();
        List<String> subscriptionIds = Collections.singletonList("subscriptions/"+subscriptionId);

        ResourceManager resourceManager = ResourceManager.authenticate(azureCredentialDto.getClientSecretCredential(), azureCredentialDto.getProfile()).withDefaultSubscription();
        SubscriptionClient subscriptionClient = resourceManager.subscriptionClient();

        CheckZonePeersRequest checkZonePeersRequest = new CheckZonePeersRequest();
        checkZonePeersRequest.withLocation(regionName);
        checkZonePeersRequest.withSubscriptionIds(subscriptionIds);

        try {
            CheckZonePeersResultInner resultInner = subscriptionClient.getSubscriptions().checkZonePeers(subscriptionId, checkZonePeersRequest);

            String subId = resultInner.subscriptionId();
            String region = resultInner.location();
            List<AvailabilityZonePeers> availabilityZonePeers = resultInner.availabilityZonePeers();

            List<ZoneRes> zoneRes = availabilityZonePeers.stream().map(e -> {
                return ZoneRes.builder()
                        .zoneKey(e.availabilityZone())
                        .zoneName(e.availabilityZone())
                        .zoneDisplayName(e.availabilityZone())
                        .subscriptionId(subscriptionId)
                        .regionKey(region)
                        .build();
            }).collect(Collectors.toList());

            return zoneRes;
        }catch (ManagementException e){
//            System.out.println("value:"+e.getValue());
        }
        return new ArrayList<>();
    }
}
