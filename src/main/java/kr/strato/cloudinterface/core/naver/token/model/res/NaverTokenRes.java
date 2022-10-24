package kr.strato.cloudinterface.core.naver.token.model.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverTokenRes {
    private String timestamp;
    private String accessKey;
    private String signature;
}
