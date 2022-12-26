package kr.strato.cloudinterface.core.azure.location.model;

import lombok.*;

/**
 * 애저 존 객체 리턴
 * sample: subscriptionId=dc5a8c9a-9fd3-4c57-bb07-e747622dc66d, region=centralindia, availabilityZone=1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AzureZoneRes {
    private String subscriptionId;
    private String region;
    private String availabilityZone;
}
