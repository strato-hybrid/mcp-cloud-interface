package kr.strato.cloudinterface.core.aws.location.model.res;

import lombok.*;


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
