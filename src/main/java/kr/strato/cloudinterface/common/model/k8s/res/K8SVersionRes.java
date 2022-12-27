package kr.strato.cloudinterface.common.model.k8s.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class K8SVersionRes {
    String versionKey;
    String versionName;
    boolean isDefault;
    String channel;
    String zoneName;
}
