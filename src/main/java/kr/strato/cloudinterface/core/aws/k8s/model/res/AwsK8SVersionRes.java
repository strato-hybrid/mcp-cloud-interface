package kr.strato.cloudinterface.core.aws.k8s.model.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AwsK8SVersionRes {
    private String clusterVersion;
    private Boolean defaultVersion;
}
