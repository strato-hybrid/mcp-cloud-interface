package kr.strato.cloudinterface.interfaces.k8s;

import kr.strato.cloudinterface.common.model.k8s.req.K8SVersionReq;
import kr.strato.cloudinterface.common.model.k8s.res.K8SVersionRes;

import java.io.IOException;
import java.util.List;

public interface K8SVersionService {
    public List<K8SVersionRes> getK8SVersions(K8SVersionReq k8SVersionReq) throws IOException;
}
