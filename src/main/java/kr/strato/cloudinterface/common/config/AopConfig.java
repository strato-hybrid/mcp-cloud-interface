package kr.strato.cloudinterface.common.config;


import kr.strato.cloudinterface.common.model.CommonReq;
import kr.strato.cloudinterface.common.model.auth.req.TokenIssueReq;
import kr.strato.cloudinterface.common.model.auth.res.TokenIssueRes;
import kr.strato.cloudinterface.common.service.TokenService;
import kr.strato.cloudinterface.interfaces.common.config.APIConfig;
import kr.strato.cloudinterface.interfaces.common.config.SDKConfig;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Aspect
@Component
public class AopConfig {
    private TokenIssueReq tokenDto;
    private TokenIssueRes tokenRes;
    private RestTemplate restTemplate;

    private Object naverQueryParamDto;

    @Autowired
    private TokenService requestTokenService;

    /**
     * API request용 인증 토큰 발급 관련 공통 기능
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before(value = "@annotation(kr.strato.cloudinterface.interfaces.common.config.APIConfig)")
    public void setApiConfiguration(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        APIConfig apiConfig = methodSignature.getMethod().getAnnotation(APIConfig.class);

        extraParameter(joinPoint);

        String requestMethod = apiConfig.method();
        if (requestMethod.equals("GET")) {
            setQueryParameter();
        }

        this.tokenDto.setRequestUrl(apiConfig.reqUrl());
        this.tokenDto.setServiceName(apiConfig.serviceName());
        this.tokenDto.setMethod(requestMethod);
        this.tokenDto.setScope(apiConfig.scope());
        TokenIssueRes authData = requestTokenService.getApiTokenIssue(this.tokenDto, CommonEnums.RequestType.API.getValue());

        // RestTemplate header 값 추가
//        List<ClientHttpRequestInterceptor> interceptors
//                = this.restTemplate.getInterceptors();
//        if (CollectionUtils.isEmpty(interceptors)) {
//            interceptors = new ArrayList<>();
//        }
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RestTemplateHeaderModifierInterceptor(authData.getCredentialsHeader()));
        this.restTemplate.setInterceptors(interceptors);
    }

    @Before(value = "@annotation(kr.strato.cloudinterface.interfaces.common.config.SDKConfig)")
    public void setSdkConfiguration(JoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SDKConfig sdkConfig = methodSignature.getMethod().getAnnotation(SDKConfig.class);

        extraParameter(joinPoint);

        this.tokenDto.setType(sdkConfig.type());
        this.tokenDto.setAuthUri(sdkConfig.authUri());
        this.tokenDto.setTokenUri(sdkConfig.tokenUri());
        this.tokenDto.setAuthProviderUrl(sdkConfig.authProviderUrl());

        TokenIssueRes authData = requestTokenService.getApiTokenIssue(this.tokenDto, CommonEnums.RequestType.SDK.getValue());
        this.tokenRes.setCredentials(authData.getCredentials());
    }

    private void extraParameter(JoinPoint joinPoint) {
        // 필요한 공통 parameter 추출
        Object[] args = joinPoint.getArgs();
        int index = 0, dtoIndex = -1;

        for(Object arg: args) {
            if (arg instanceof CommonReq) {
                dtoIndex = index;
            }
            index += 1;
        }

        // 공통 parameter의 값이 null일 때 처리
        if (((CommonReq) args[dtoIndex]).getTokenIssueRes() == null) {
            ((CommonReq) args[dtoIndex]).setTokenIssueRes(new TokenIssueRes());
        }
        if (((CommonReq) args[dtoIndex]).getRestTemplate() == null) {
            ((CommonReq) args[dtoIndex]).setRestTemplate(new RestTemplate());
        }
        this.tokenDto = ((CommonReq) args[dtoIndex]).getTokenIssueReq();
        this.tokenRes = ((CommonReq) args[dtoIndex]).getTokenIssueRes();
        this.restTemplate = ((CommonReq) args[dtoIndex]).getRestTemplate();
        this.naverQueryParamDto = ((CommonReq) args[dtoIndex]).getNaverQueryParamDto();
    }

    private void setQueryParameter() {
        Object obj = this.naverQueryParamDto;
        try {
            if (obj != null) {
                int idx = 0;
                String queryParameter = "";
                for (Field field : obj.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value != null) {
                        String param = field.getName() + "=" + value.toString();
                        queryParameter += (idx == 0 ? "?" + param : param);
                    }
                }
                this.tokenDto.setQueryParameter(queryParameter);
            }
        } catch (IllegalAccessException e) {
            log.error(e);
        }
    }
}
