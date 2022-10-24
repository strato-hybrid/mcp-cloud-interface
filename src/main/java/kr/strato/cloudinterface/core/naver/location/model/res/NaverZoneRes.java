package kr.strato.cloudinterface.core.naver.location.model.res;

import lombok.*;

/**
 * 네이버 존 객체 리턴
 * sample: ZoneNo=3, zonName=KR-2, zoneCode=KR-2, zoneDescription=평촌 zone, regionNo=1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NaverZoneRes {
    private String ZoneNo;
    private String zonName;
    private String zoneCode;
    private String zoneDescription;
    private String regionNo;
}
