package kr.strato.cloudinterface.core.azure.location.model;

import lombok.*;

/**
 * 애저 존 객체 리턴
 * 
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
