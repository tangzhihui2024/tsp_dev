package com.icbcaxa.eata.constant;


/**
 * yangzexu
 */
public enum CatConstant {

    TYPE_URL("URL"),TYPE_SERVICE("Service"),TYPE_REMOTE("Remote"),TYPE_SQL("SQL"),TYPE_FEIGN("Feign"),
    TYPE_REMOTE_REST("RestTemplate"),TYPE_REMOTE_HTTP("HttpClient"),TYPE_REMOTE_FEIGN("FeignUrl");

    private CatConstant(String type){
        this.type = type;
    }

    private String type;

    public String value() {
        return type;
    }
}
