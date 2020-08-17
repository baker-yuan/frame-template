package cn.yuanyu.uaa.interceptor;

import cn.yuanyu.uaa.enums.ErrorCodeEnum;
import cn.yuanyu.uaa.model.AuthAccessToken;
import cn.yuanyu.uaa.service.AuthorizationService;
import cn.yuanyu.uaa.utils.DateUtils;
import cn.yuanyu.uaa.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于校验 Access Token 是否为空以及 Access Token 是否已经失效
 */
@Component
public class AuthAccessTokenInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * 检查 Access Token 是否已经失效
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getParameter("access_token");
        if(StringUtils.isNoneBlank(accessToken)){
            // 查询数据库中的 Access Token
            AuthAccessToken authAccessToken = authorizationService.selectByAccessToken(accessToken);
            if(authAccessToken != null){
                Long savedExpiresAt = authAccessToken.getExpiresIn();
                // 过期日期
                LocalDateTime expiresDateTime = DateUtils.ofEpochSecond(savedExpiresAt, null);
                // 当前日期
                LocalDateTime nowDateTime = DateUtils.now();
                // 如果 Access Token已经失效，则返回错误提示
                return expiresDateTime.isAfter(nowDateTime) || this.generateErrorResponse(response, ErrorCodeEnum.EXPIRED_TOKEN);
            }else{
                return this.generateErrorResponse(response, ErrorCodeEnum.INVALID_GRANT);
            }
        }else{
            return this.generateErrorResponse(response, ErrorCodeEnum.INVALID_REQUEST);
        }
    }
    
    /**
     * 组装错误请求的返回
     */
    private boolean generateErrorResponse(HttpServletResponse response,ErrorCodeEnum errorCodeEnum) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        Map<String,String> result = new HashMap<>(2);
        result.put("error", errorCodeEnum.getError());
        result.put("error_description",errorCodeEnum.getErrorDescription());

        response.getWriter().write(JsonUtils.toJson(result));
        return false;
    }

}
