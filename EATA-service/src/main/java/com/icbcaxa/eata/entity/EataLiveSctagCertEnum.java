package com.icbcaxa.eata.entity;

/**
 * 类EataLiveSctagCertController.java实现类：平安壹账通返回字典值
 * Created by gyas-itwb-fsl01 on 2020/4/24.
 */
public enum EataLiveSctagCertEnum {

    GYAS_LANCER_SUCCESS_000000("000000","认证成功"),
    GYAS_LANCER_ERROR_000012("000012","参数解析失败,参数缺失"),
    GYAS_LANCER_ERROR_000014("000014","系统繁忙，请稍候再试"),
    GYAS_LANCER_ERROR_000016("000016","人脸检测失败，请重试"),
    GYAS_LANCER_ERROR_000017("000017","姓名与身份证号码不匹配，请核实后重试"),
    GYAS_LANCER_ERROR_000023("000023","身份证号码格式不正确，请核实后重试"),
    GYAS_LANCER_ERROR_000025("000025","人证比对不通过"),
    GYAS_LANCER_ERROR_000028("000028","认证未通过"),
    GYAS_LANCER_ERROR_000029("000029","姓名格式不正确，请核实后重试"),
    GYAS_LANCER_ERROR_000032("000032","姓名与身份证号码不匹配，请核实后重试"),
    GYAS_LANCER_ERROR_000057("000057","姓名与身份证号码不匹配，请核实后重试"),
    GYAS_LANCER_ERROR_000058("000058","姓名与身份证号码不匹配，请核实后重试"),
    GYAS_LANCER_ERROR_000059("000059","姓名与身份证号码不匹配，请核实后重试"),
    GYAS_LANCER_ERROR_000065("000065","人脸检测失败，请重试"),
    GYAS_LANCER_ERROR_000074("000074","系统繁忙，请稍候再试"),
    GYAS_LANCER_ERROR_000077("000077","人脸检测失败，请重试"),
    GYAS_LANCER_ERROR_000078("000078","人脸检测失败，请重试"),
    GYAS_LANCER_ERROR_910001("910001","非法参数"),
    GYAS_LANCER_ERROR_000099("000099","未知异常"),
    GYAS_LANCER_ERROR_999999("999999","系统繁忙，请稍候再试"),
    GYAS_LANCER_ERROR_999901("999901","人脸检测失败，照片质量检测不通过");

    private String code;
    private String mag;

    public String getCode() {
        return code;
    }

    public String getMag() {
        return mag;
    }

    EataLiveSctagCertEnum(String code, String mag) {
        this.code = code;
        this.mag = mag;
    }
}
