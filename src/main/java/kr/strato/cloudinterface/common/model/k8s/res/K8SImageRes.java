package kr.strato.cloudinterface.common.model.k8s.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class K8SImageRes {
    private String name;
    private String type;
    private String code;
    private String architecture;
}
