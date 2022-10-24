package kr.strato.cloudinterface.common.model.location.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegionRes {
    private String regionKey;
    private String regionName;
    private String regionDisplayName;
    private String description;
    private String status;
    private String endpoint;
}
