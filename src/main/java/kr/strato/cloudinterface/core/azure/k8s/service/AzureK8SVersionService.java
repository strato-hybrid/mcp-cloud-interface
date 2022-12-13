package kr.strato.cloudinterface.core.azure.k8s.service;

import com.azure.resourcemanager.containerservice.fluent.models.OrchestratorVersionProfileListResultInner;
import com.azure.resourcemanager.containerservice.models.OrchestratorVersionProfile;
import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.k8s.req.K8SVersionReq;
import kr.strato.cloudinterface.common.model.k8s.res.K8SVersionRes;
import kr.strato.cloudinterface.core.azure.k8s.model.dto.AzureK8SVersionDto;
import kr.strato.cloudinterface.core.azure.k8s.model.res.AzureK8SVersionRes;
import kr.strato.cloudinterface.core.azure.location.service.AzureRegionSevice;
import kr.strato.cloudinterface.interfaces.common.config.APIConfig;
import kr.strato.cloudinterface.interfaces.k8s.K8SVersionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AzureK8SVersionService implements K8SVersionService {

    @APIConfig(scope = "https://management.azure.com/.default")
    @Override
    public List<K8SVersionRes> getK8SVersions(K8SVersionReq k8SVersionReq){
        String regionName = k8SVersionReq.getRegionName();
        String apiVersion = "2019-08-01";
        Map<String, String> supportRegionMap = getSupportRegionMap();
        if(!supportRegionMap.containsKey(regionName)){
            return new ArrayList<K8SVersionRes>();
        }

        String url = "https://management.azure.com/subscriptions/"+k8SVersionReq.getTokenIssueReq().getOptionId_2()
                +"/providers/Microsoft.ContainerService/locations/"+k8SVersionReq.getRegionName()+"/orchestrators?api-version=2019-08-01&resource-type=managedClusters";

        RestTemplate restTemplate = k8SVersionReq.getRestTemplate();
        ResponseEntity<OrchestratorVersionProfileListResultInner> response = restTemplate.getForEntity(url, OrchestratorVersionProfileListResultInner.class);
        List<OrchestratorVersionProfile> orchestrators = response.getBody().orchestrators();

        List<K8SVersionRes> results = orchestrators.stream().map(e -> K8SVersionRes.builder()
                .versionKey(e.orchestratorVersion())
                .versionName(e.orchestratorVersion())
                .isDefault(e.defaultProperty() == Boolean.TRUE ? true : false).build()).collect(Collectors.toList());

//        versions.forEach(log::info);
        return results;
    }

    public Map<String, String> getSupportRegionMap(){
        Map<String, String> map = new HashMap<>();
        map.put("australiacentral", "australiacentral");
        map.put("australiacentral2", "australiacentral2");
        map.put("australiaeast", "australiaeast");
        map.put("australiasoutheast", "australiasoutheast");
        map.put("brazilsouth", "brazilsouth");
        map.put("brazilsoutheast", "brazilsoutheast");
        map.put("canadacentral", "canadacentral");
        map.put("canadaeast", "canadaeast");
        map.put("centralindia", "centralindia");
        map.put("centralus", "centralus");
        map.put("eastasia", "eastasia");
        map.put("eastus", "eastus");
        map.put("eastus2", "eastus2");
        map.put("francecentral", "francecentral");
        map.put("francesouth", "francesouth");
        map.put("germanynorth", "germanynorth");
        map.put("germanywestcentral", "germanywestcentral");
        map.put("japaneast", "japaneast");
        map.put("japanwest", "japanwest");
        map.put("jioindiacentral", "jioindiacentral");
        map.put("jioindiawest", "jioindiawest");
        map.put("koreacentral", "koreacentral");
        map.put("koreasouth", "koreasouth");
        map.put("northcentralus", "northcentralus");
        map.put("northeurope", "northeurope");
        map.put("norwayeast", "norwayeast");
        map.put("norwaywest", "norwaywest");
        map.put("qatarcentral", "qatarcentral");
        map.put("southafricawest", "southafricawest");
        map.put("southcentralus", "southcentralus");
        map.put("southeastasia", "southeastasia");
        map.put("southindia", "southindia");
        map.put("swedencentral", "swedencentral");
        map.put("switzerlandnorth", "switzerlandnorth");
        map.put("switzerlandwest", "switzerlandwest");
        map.put("uaecentral", "uaecentral");
        map.put("uaenorth", "uaenorth");
        map.put("uksouth", "uksouth");
        map.put("ukwest", "ukwest");
        map.put("westcentralus", "westcentralus");
        map.put("westeurope", "westeurope");
        map.put("westus", "westus");
        map.put("westus2", "westus2");
        map.put("westus3", "westus3");
        return map;
    }
}
