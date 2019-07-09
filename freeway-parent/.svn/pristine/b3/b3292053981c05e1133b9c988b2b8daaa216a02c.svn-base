package net.chinahrd.modules.rbac.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.BaseResponse;
import net.chinahrd.common.shiro.AuthcFilter;
import net.chinahrd.enums.ResponseStatusEnum;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wzmo
 *
 * 平台模块的url过滤校验
 *
 */
@Slf4j
@Service
public class AdminAuthcFilter implements AuthcFilter {
    @Override
    public UserFilter getUserFilter() {
        return new AdminUserFilter();
    }

    class AdminUserFilter extends UserFilter {
        @Override
        protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
            if (this.isLoginRequest(request, response)) {
                return true;
            } else {
                return true;
                //return subject.getPrincipal() != null;
            }
        }

        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
            String requestURL = getPathWithinApplication(request);// 获取url
            log.debug("url:" + requestURL);
            sessionTimeOut(response);
            return false;
        }

        private void sessionTimeOut(ServletResponse response) throws IOException {
            log.info("session超时");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            //        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            BaseResponse<String> error = new BaseResponse<String>();
            error.setCode(ResponseStatusEnum.SESSION_TIMEOUT.getCode());
            error.setMessage(ResponseStatusEnum.SESSION_TIMEOUT.getValue());
            httpResponse.getWriter().append(JSONObject.toJSONString(error));
        }
    }
}
