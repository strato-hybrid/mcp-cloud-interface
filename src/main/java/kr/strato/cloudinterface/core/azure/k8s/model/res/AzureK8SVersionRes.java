package kr.strato.cloudinterface.core.azure.k8s.model.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AzureK8SVersionRes {
    private String orchestratorType;
    private String orchestratorVersion;
    private boolean isDefault;

}
