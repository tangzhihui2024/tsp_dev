package com.icbcaxa.eata.entity;

public class GenerateInfo {

    private String refGUID;

    private String loginSource;

    private String terminalType;

    private String systemInfo;

    private String loginSystemUserId;

    private String systemTime;

    public String getRefGUID() {
        return refGUID;
    }

    public void setRefGUID(String refGUID) {
        this.refGUID = refGUID;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    public String getLoginSystemUserId() {
        return loginSystemUserId;
    }

    public void setLoginSystemUserId(String loginSystemUserId) {
        this.loginSystemUserId = loginSystemUserId;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }
}
