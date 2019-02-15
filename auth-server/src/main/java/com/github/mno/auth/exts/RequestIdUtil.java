package com.github.mno.auth.exts;


import java.util.UUID;

/**
 * requestId 整个注入、使用、注销的流程
 * <P>如果使用:
 * <P><code>
 * public class RequestFilter implements Filter {}
 * </code>
 * <P>{@link javax.servlet.Filter#destroy()} 只会在整个程序关闭的时候执行（是指的filter的销毁，并不是指的一个请求的销毁，只会执行一次）
 * <P><code>
 * public class LogInterceptor implements HandlerInterceptor {}
 * </code>
 * <P>{@link org.springframework.web.servlet.HandlerInterceptor} 的<tt>preHandle</tt>和<tt>postHandle</tt>联合起来用也没问题，只是配置麻烦一点：
 * <P>因此我们使用：
 * <p>{@link javax.servlet.ServletRequestListener} 方便，好用
 *
 * @author mno
 * @date 2018/10/23 20:18
 */
public final class RequestIdUtil {
    private RequestIdUtil() {
    }

    /**
     * requestId 常量字段
     */
    public static final String REQUEST_ID_KEY = "requestId";

    /**
     * 多线程安全
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 创建 唯一的request
     *
     * @return requestId
     */
    public static String create() {
        String requestId = UUID.randomUUID().toString();
        return requestId.replaceAll("-", "");
    }

    /**
     * 必须 先set 或者 initialValue，不然可能会报空指针
     *
     * @return requestId
     */
    public static String get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 设置 requestId值
     *
     * @param requestId 请求的唯一标识
     */
    public static void set(String requestId) {
        THREAD_LOCAL.set(requestId);
    }

    /**
     * 移除
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
