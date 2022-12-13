package kr.strato.cloudinterface.core.aws.location.model.dto;

import lombok.*;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwsRegionDto {
    private StaticCredentialsProvider credentials;
    private Region region;
}
