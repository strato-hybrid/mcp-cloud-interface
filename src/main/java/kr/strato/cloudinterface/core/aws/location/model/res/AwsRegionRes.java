package kr.strato.cloudinterface.core.aws.location.model.res;

import lombok.*;

/**
 * AWS 리전 조회 객체
 * sample: endPoint=ec2.eu-north-1.amazonaws.com, regionName=eu-north-1, optInStatus=opt-in-not-required
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AwsRegionRes {
    private String endPoint;
    private String regionName;
    private String optInStatus;
}
