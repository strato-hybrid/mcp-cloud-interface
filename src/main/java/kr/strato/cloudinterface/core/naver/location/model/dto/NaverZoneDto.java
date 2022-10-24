package kr.strato.cloudinterface.core.naver.location.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "dtoBuilder")
public class NaverZoneDto {
    private String regionNo;
//    private String zoneId;

    public static NaverZoneDtoBuilder builder(String regionNo) {
        return dtoBuilder().regionNo(regionNo);
    }

    public static class NaverZoneDtoBuilder {}
}
