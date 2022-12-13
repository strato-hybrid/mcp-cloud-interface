package kr.strato.cloudinterface.core.aws.location.service;

import kr.strato.cloudinterface.common.error.exception.NotFoundResourceException;
import kr.strato.cloudinterface.common.model.location.req.RegionReq;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import kr.strato.cloudinterface.interfaces.location.RegionService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeRegionsResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AwsRegionService implements RegionService {

    @SDKConfig
    @Override
    public List<RegionRes> getRegions(RegionReq regionReq) throws IOException {
        Ec2Client ec2Client = Ec2Client.builder()
                .region(Region.of(regionReq.getTokenIssueReq().getRegion()))
                .credentialsProvider(regionReq.getTokenIssueRes().getCredentials())
                .build();

        try{
            DescribeRegionsResponse regionsResponse = ec2Client.describeRegions();
            return regionsResponse.regions().stream().map(e -> RegionRes.builder()
                    .endpoint(e.endpoint())
                    .regionName(e.regionName())
                    .regionKey(e.regionName())
                    .regionDisplayName(e.regionName())
                    .build()).collect(Collectors.toList());
        }catch(Exception e){
            throw new NotFoundResourceException("aws region 목록 조회 실패", e);
        }
    }
}
