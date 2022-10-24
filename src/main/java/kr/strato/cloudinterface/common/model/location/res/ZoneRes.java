package kr.strato.cloudinterface.common.model.location.res;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ZoneRes {
    private String zoneKey;
    private String zoneName;
    private String zoneDisplayName;
    private String status;
    private String endpoint;
    private String description;
    private String regionKey;
    private String subscriptionId;
}
