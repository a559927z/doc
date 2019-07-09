package net.chinahrd.common;

import net.chinahrd.enums.ResponseStatusEnum;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guanjian
 *
 */
public abstract class AbstractBaseController {
    /**
     * 日志对象
     */
    protected Logger log = LoggerFactory.getLogger(getClass());
    /**
     * ThreadLocal确保高并发下每个请求的request，response都是独立的
     */
    private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
    private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

    /**
     * 线程安全初始化reque，respose对象
     * 
     * @param request
     * @param response
     */
    @ModelAttribute
    public void initReqAndRep(HttpServletRequest request, HttpServletResponse response) {
        currentRequest.set(request);
        currentResponse.set(response);
    }

    /**
     * 线程安全
     * 
     */
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) currentRequest.get();
    }

    /**
     * 线程安全
     * 
     * @return
     */
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) currentResponse.get();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }
    
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public Session getSubjectSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    public CurrentUser getCurrentUser() {
        HttpSession session = getSession();
        CurrentUser user = (CurrentUser) session.getAttribute("user");
        return user;
    }

    public String getCurrentUserName() {
        HttpSession session = getSession();
        CurrentUser user = (CurrentUser) session.getAttribute("user");
        return user == null ? null:user.getUserId();
    }
    
    public String getCurrentUserId() {
        HttpSession session = getSession();
        CurrentUser user = (CurrentUser) session.getAttribute("user");
        return user == null ? null:user.getUserName();
    }
    
    public String getCurrentTenantId() {
        HttpSession session = getSession();
        CurrentUser user = (CurrentUser) session.getAttribute("user");
        return user == null ? null:user.getTenantId();
    }

    /**
     * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                 setValue(text == null ? null :
                 StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        /*
         * binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
         * 
         * @Override public void setAsText(String text) {
         * setValue(DateUtils.parseDate(text)); }
         * 
         * @Override public String getAsText() { Object value = getValue();
         * return value != null ? DateUtils.formatDateTime((Date) value) : ""; }
         * });
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    protected <T> BaseResponse<T> setFailedMsg(String msg) {
        BaseResponse<T> vo = new BaseResponse<T>();
        vo.error(msg);
        return vo;
    }
    
    protected <T> BaseResponse<T> setSuceessMsg(String msg) {
        BaseResponse<T> vo = new BaseResponse<T>();
        vo.setCode(ResponseStatusEnum.SUCCESS.getCode());
        vo.setMessage(msg);
        return vo;
    }
    
    protected <T> BaseResponse<T> setResultData(T data) {
        BaseResponse<T> vo = new BaseResponse<T>();
        vo.success(data);
        return vo;
    }

}
