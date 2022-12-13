package kr.strato.cloudinterface.core.aws.k8s.service;

import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.k8s.req.K8SVersionReq;
import kr.strato.cloudinterface.common.model.k8s.res.K8SVersionRes;
import kr.strato.cloudinterface.core.aws.k8s.model.res.AwsK8SVersionRes;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import kr.strato.cloudinterface.interfaces.k8s.K8SVersionService;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eks.EksClient;
import software.amazon.awssdk.services.eks.model.DescribeAddonVersionsRequest;
import software.amazon.awssdk.services.eks.model.DescribeAddonVersionsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Log4j2
@Service
public class AwsK8SVersionService implements K8SVersionService {

    @SDKConfig
    @Override
    public List<K8SVersionRes> getK8SVersions(K8SVersionReq k8SVersionReq) throws IOException {
        List<K8SVersionRes> result = new ArrayList<>();

        EksClient eksClient = EksClient.builder()
                .region(Region.of(k8SVersionReq.getTokenIssueReq().getRegion()))
                .credentialsProvider(k8SVersionReq.getTokenIssueRes().getCredentials())
                .build();
        DescribeAddonVersionsRequest request = DescribeAddonVersionsRequest.builder().build();
        DescribeAddonVersionsResponse response = eksClient.describeAddonVersions(request);

        HashSet<String> setVersions = new HashSet<>();

        response.addons().forEach(addon -> {
            addon.addonVersions().forEach(version -> {
                version.compatibilities().forEach(k8sVersion -> setVersions.add(k8sVersion.clusterVersion()));
            });
        });

        List<String> k8sVersions = new ArrayList<>(setVersions);
        k8sVersions.stream().sorted(Comparator.reverseOrder()).forEach(e -> {
            K8SVersionRes res = K8SVersionRes.builder()
                    .versionKey(e)
                    .versionName(e)
                    .isDefault(false)// default version 여부 알 수 없음
                    .build();
            result.add(res);
        });

        return result;
    }

}
