package com.icbcaxa.eata.entity;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 慧眼人脸核身 响应实体
 * 接口文档地址：https://cloud.tencent.com/document/product/1007/37305
 */
@XmlRootElement(name = "FacekernelMsg_Tickets")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class FacekernelMsg_Tickets {

    /**
     * 第2步，获取tickets 响应实体
     *
     * code 不为0则表示获取失败，可以根据 code 和 msg 字段进行定位和调试。code 详情请参见 错误码。
     * expire_in 为 SIGN ticket 的最大生存时间，单位：秒，合作伙伴在判定有效期时以此为准。
     * expire_time 为 SIGN ticket 失效的绝对时间，由于各服务器时间差异，不能以此作为有效期的判定依据，只作为展示使用。
     * access_token 失效时，该 access_token 生成的 ticket 都失效。
     * tickets只有一个。
     */

    @XmlElement(name = "code")
    private String code ;
    @XmlElement(name = "msg")
    private String msg ;
    @XmlElement(name = "transactionTime")
    private String transactionTime ;
    @XmlElement(name = "tickets")
    private Tickets tickets  ;

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

    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }
}
