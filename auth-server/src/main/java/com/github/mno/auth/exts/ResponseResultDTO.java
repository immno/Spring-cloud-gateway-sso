package com.github.mno.auth.exts;

/**
 * SISG-analysis
 * <ol>
 * [参考]错误码暂定都是5位数字，并配有相应的英文解释
 * <li>错误码为 0 表示成功，其他都表示错误</li>
 * <li>错误码按模块按功能场景分级分段，前三位错误码表示模块，第四位表示模块下的功能。举例，商城系统里有交易模块和商品模块，则可以这样划分：401开头的表示交易模块，402开头的表示商品模块，4011开头的表示交易模块里的下单场景需要用到的错误码，4021表示商品模块下的添加商品场景里需要用到的错误码。如果某个场景功能下需要的比较多的错误码，则可以使用其他未被使用的码段，即该场景功能可以拥有多个码段，然后通过添加注释等方式让人理解即可。</li>
 * <li>数字 1 开头的错误码表示系统级别的错误，比如缺少某种字符集，连不上数据库之类的，系统级的错误码不需要分模块，可以按照自增方式进行添加</li>
 * <li>数字 4 开头的错误码表示API参数校验失败，比如 “交易模块下单场景中，订单金额参数不能为空” 可以用 40111 错误码来表示</li>
 * <li>数字 5 开头的错误码表示后台业务校验失败，比如 “交易模块下单场景中，该用户没有下单权限” 可以用 50111 错误码来表示</li>
 * <li>数字 4 开头的错误码与数字 5 开头的错误码对应的模块分类需要保持一致，即 4011 表示交易模块下单场景的API错误，5011 表示交易模块下单场景的业务错误</li>
 * <li>错误码按需分配，逐步增加，灵活扩展</li>
 * </ol>
 * <a href="https://blog.csdn.net/yzzst/article/details/54799971">设计参考</a>
 * <a href="https://github.com/moelholm/smallexamples/blob/master/enhanced-logging/src/main/resources/application.properties">request设计参考1</a>
 * <a href="https://stackoverflow.com/questions/47716442/springboot-how-to-pass-argument-request-id-from-controller-through-all-progra">request设计参考2</a>
 *
 * @author mno
 * @date 2018/10/16 19:51
 */
public class ResponseResultDTO<T> {

    private T data;

    private Boolean success;

    private String requestId;

    private String resultCode;

    private String resultMsg;

    private String solution;

    public ResponseResultDTO() {
    }

    /**
     * 请求成功
     *
     * @param data 返回的数据
     * @param <T>  数据对象
     * @return 请求结果
     */
    public static <T> ResponseResultDTO<T> success(T data) {
        return new ResponseResultDTO<>(data, true, null, null, null, null);
    }

    /**
     * 请求失败
     *
     * @param requestId  通过日志追踪处理链
     * @param resultCode 错误码
     * @param resultMsg  错误信息
     * @param solution   解决方案
     * @param <T>        数据对象
     * @return 请求结果
     */
    public static <T> ResponseResultDTO<T> failure(String requestId, String resultCode, String resultMsg, String solution) {
        return new ResponseResultDTO<>(null, false, requestId, resultCode, resultMsg, solution);
    }

    private ResponseResultDTO(T data, boolean success, String requestId, String resultCode, String resultMsg, String solution) {
        this.data = data;
        this.success = success;
        this.requestId = requestId;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.solution = solution;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseResult{");
        sb.append("data=").append(data);
        sb.append(", success=").append(success);
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", resultCode='").append(resultCode).append('\'');
        sb.append(", resultMsg='").append(resultMsg).append('\'');
        sb.append(", solution='").append(solution).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
