package kr.strato.cloudinterface.interfaces.k8s;

import kr.strato.cloudinterface.common.model.k8s.req.K8SImageReq;
import kr.strato.cloudinterface.common.model.k8s.res.K8SImageRes;

import java.io.IOException;
import java.util.List;

public interface K8SImageService {
    public List<K8SImageRes> getImages(K8SImageReq k8SImageReq) throws IOException;
}
