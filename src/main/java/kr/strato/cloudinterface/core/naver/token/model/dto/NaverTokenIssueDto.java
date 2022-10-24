package kr.strato.cloudinterface.core.naver.token.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverTokenIssueDto {
    private String accessKey;
    private String secretKey;
    private String method;
    private String url;
}
