package kr.strato.cloudinterface.core.azure.location.model;

import com.azure.resourcemanager.resources.models.Location;
import kr.strato.cloudinterface.common.model.location.res.RegionRes;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AzureRegionRes {
    private String uuid;
    private String name;
    private String displayName;
    private String category;
    private String geographyGroup;

//    public static AzureRegionRes toObject(Location location){
//        AzureRegionRes res = AzureRegionRes.builder()
//                .uuid(location.key())
//                .name(location.name())
//                .displayName(location.displayName())
//                .category(location.regionCategory().toString())
//                .geographyGroup(location.geographyGroup())
//                .build();
//
//        return res;
//    }

    public static RegionRes toRegionRes(Location location) {
        RegionRes regionRes = RegionRes.builder()
                .regionKey(location.key())
                .regionName(location.name())
                .regionDisplayName(location.name())
                .build();
        return regionRes;
    }
}
