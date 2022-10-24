package kr.strato.cloudinterface.core.google.auth.model.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenRes {
    private String access_token;
    private String scope;
    private String token_type;
    private String expires_in;
}
