package net.chinahrd.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.utils.CollectionKit;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Title: clientapi模块控制器基类 <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年1月24日 上午10:20:38
 * @Verdion 1.0 版本
 */
@Slf4j
public abstract class ClientBaseController {

    protected static final int DEFAULT_SUCCESS_CODE = 0;
    protected static final String DEFAULT_ERROR_MSG = "服务器错误";
    protected static final String DEFAULT_SUCCESS_MSG = "操作成功";
    protected static final String RESULT_JSON_BODY_KEY = "body";
    protected static final String RESULT_JSON_CODE_KEY = "code";
    protected static final String RESULT_JSON_MESSAGE_KEY = "message";
    protected static final String CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 构造自义的返回
     *
     * @param req
     * @param res
     * @param body
     * @param code
     * @param message
     * @param needGzip
     */
    public void buildResponse(HttpServletRequest req, HttpServletResponse res, Object body, int code, String message,
                              boolean needGzip) {
        Map<String, Object> resMap = CollectionKit.newMap();
        if (null != body) {
            resMap.put(RESULT_JSON_BODY_KEY, body);
        }
        resMap.put(RESULT_JSON_CODE_KEY, code);
        resMap.put(RESULT_JSON_MESSAGE_KEY, message);

        outJsonStream(req, res, needGzip, resMap);
    }

    /**
     * 构造成功的返回
     *
     * @param req
     * @param res
     * @param body
     */
    public void buildSuccessResponse(HttpServletRequest req, HttpServletResponse res, Object body) {
        boolean needGzip = "true".equals(req.getParameter("needGzip"));

        Map<String, Object> resMap = CollectionKit.newMap();
        if (null != body) {
            resMap.put(RESULT_JSON_BODY_KEY, body);
        }
        resMap.put(RESULT_JSON_CODE_KEY, DEFAULT_SUCCESS_CODE);
        resMap.put(RESULT_JSON_MESSAGE_KEY, DEFAULT_SUCCESS_MSG);
        res.setContentType(CONTENT_TYPE);

        outJsonStream(req, res, needGzip, resMap);
    }


    /**
     * 构造失败的返回-压缩
     *
     * @param req
     * @param res
     * @param errCode
     * @param errMessage
     */
    public void buildFailureResponse(HttpServletRequest req, HttpServletResponse res, int errCode, String errMessage) {
        buildFailureResponse(req, res, errCode, errMessage, true);
    }

    /**
     * 构造失败的返回
     *
     * @param req
     * @param res
     * @param errCode
     * @param errMessage
     * @param needGzip
     */
    public void buildFailureResponse(HttpServletRequest req, HttpServletResponse res, int errCode, String errMessage,
                                     boolean needGzip) {
        Map<String, Object> errorMap = CollectionKit.newMap();
        if (StringUtils.isBlank(errMessage)) {
            errorMap.put(RESULT_JSON_CODE_KEY, "-1");
            errorMap.put(RESULT_JSON_MESSAGE_KEY, DEFAULT_ERROR_MSG);
        } else {
            errorMap.put(RESULT_JSON_CODE_KEY, errCode);
            errorMap.put(RESULT_JSON_MESSAGE_KEY, errMessage);
        }
        outJsonStream(req, res, needGzip, errorMap);
    }

    /**
     * 向客户端写流
     *
     * @param req
     * @param res
     * @param needGzip
     * @param content  向客户端内容
     */
    private void outJsonStream(HttpServletRequest req, HttpServletResponse res, boolean needGzip, Object content) {
        outJson(req, res, handlerNullToEmpty(content));
    }


    /**
     * 向客户端写流
     *
     * @param req
     * @param res
     * @param jsonString
     */
    public static void outJson(HttpServletRequest req, HttpServletResponse res, String jsonString) {
        String responseJson = quote(jsonString);
//        responseJson = jsonString;
        PrintWriter out = null;
        try {
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
            out.write(responseJson);
        } catch (IOException e) {
            log.error(e.toString());
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    private static String quote(String str) {
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            try {
                return quote(str, sw).toString();
            } catch (Exception e) {
            }
        }
        return str;

    }

    private static Writer quote(String str, Writer w) throws IOException {
        if (str == null || str.length() == 0) {
            w.write("\"\"");
            return w;
        }
        char b;
        char c = 0;
        String h;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char d = 0;
            b = c;
            c = str.charAt(i);
            if ((i + 1) < len) {
                d = str.charAt(i + 1);
            }
            if (c == '\\' && d == '\\') {
                w.write('\\');
                w.write('\\');
                c = 0;
                i++;
                continue;
            } else if (c == '\\' && d == 'v') {
                c = 0;
                i++;
                continue;
            } else if (b == '<' && c == '\\' && d == 'n') {
                w.write(c);
                continue;
            }

            switch (c) {
                case '\\':
                case '/':
                    if (b == '<') {
                        w.write('\\');
                    }
                    w.write(c);
                    break;
                case '\b':
                    w.write("\\b");
                    break;
                case '\t':
                    w.write("\\t");
                    break;
                case '\n':
                    w.write("\\n");
                    break;
                case '\f':
                    w.write("\\f");
                    break;
                case '\r':
                    w.write("\\r");
                    break;
                default:
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
                        // 排除\u000b(\v)这个特殊字符
                        h = Integer.toHexString(c);
                        if (!"b".equals(h)) {
                            w.write("\\u");
                            w.write("0000", 0, 4 - h.length());
                            w.write(h);
                        }
                    } else {
                        w.write(c);
                    }
            }

        }
        return w;
    }

    /**
     * 如果对象中有String类型属性值为Null则用空串代替
     *
     * @param obj
     * @return
     */
    private static String handlerNullToEmpty(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty);
    }
}
