package kr.strato.cloudinterface.core.naver.location.model.res;

import lombok.*;

/**
 * 네이버 리전 객체
 * sample: regionNo=1, regionCode=KR, regionName=Korea
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NaverRegionRes {
    private String regionNo;
    private String regionCode;
    private String regionName;

}
