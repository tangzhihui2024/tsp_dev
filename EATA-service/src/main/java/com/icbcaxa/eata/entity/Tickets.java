package com.icbcaxa.eata.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 慧眼人脸核身 响应实体
 * 接口文档地址：https://cloud.tencent.com/document/product/1007/37305
 */
@XmlType(name = "tickets")
public class Tickets {

    @XmlElement(name = "value")
    private String value ;

    @XmlElement(name = "expire_in")
    private String expire_in ;

    @XmlElement(name = "expire_time")
    private String expire_time ;

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }
}
