package kr.strato.cloudinterface.core.azure.location.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.resources.models.Location;
import kr.strato.cloudinterface.common.model.location.req.RegionReq;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import kr.strato.cloudinterface.core.azure.auth.model.dto.AzureCredentialDto;
import kr.strato.cloudinterface.core.azure.location.model.AzureRegionRes;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import kr.strato.cloudinterface.interfaces.location.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AzureRegionSevice implements RegionService {


    /**
     * 애저 물리적 리전 리스트 조회
     * @param params
     * @return
     */
    @Override
    @SDKConfig
    public List<RegionRes> getRegions(RegionReq params) {
        AzureCredentialDto azureCredentialDto = params.getTokenIssueRes().getCredentials();
        AzureResourceManager resourceManager = AzureResourceManager.authenticate(azureCredentialDto.getClientSecretCredential(), azureCredentialDto.getProfile()).withDefaultSubscription();
        PagedIterable<Location> pagedIterable = resourceManager.getCurrentSubscription().listLocations();

        return pagedIterable.stream().filter(location -> "Physical".equals(location.regionType().toString()))
                .map(location -> AzureRegionRes.toRegionRes(location)).collect(Collectors.toList());
    }
}
