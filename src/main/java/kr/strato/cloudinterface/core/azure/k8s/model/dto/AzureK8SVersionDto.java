package kr.strato.cloudinterface.core.azure.k8s.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "dtoBuilder")
public class AzureK8SVersionDto {
    private String regionName;

    public static AzureK8SVersionDtoBuilder builder(String regionName) {
        return AzureK8SVersionDto.dtoBuilder().regionName(regionName);
    }

    public static class AzureK8SVersionDtoBuilder {}
}
