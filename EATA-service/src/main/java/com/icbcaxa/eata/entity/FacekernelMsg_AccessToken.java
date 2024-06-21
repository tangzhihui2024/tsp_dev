package com.icbcaxa.eata.entity;

/**
 * 慧眼人脸核身 响应实体
 * 接口文档地址：https://cloud.tencent.com/document/product/1007/35918
 * @author Tzh
 * @time 2020/3/23
 */
public class FacekernelMsg_AccessToken {

    /**
     * 第一步，获取access_token 响应实体
     */
    private String code ; // code 不为0则表示获取失败，可以根据 code 和 msg 字段进行定位和调试。code 详情请参见 错误码。
    private String msg ;
    private String transactionTime;
    private String access_token; // 修改 secret 之后，该 app_id 生成的 access_token 和 ticket 都失效。
    private String expire_time ; // expire_time 为 access_token 失效的绝对时间，由于各服务器时间差异，不能以此作为有效期的判定依据，只作为展示使用。
    private String expire_in ; // expire_in 为 access_token 的最大生存时间，单位：秒，合作伙伴在判定有效期时以此为准。

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

}
