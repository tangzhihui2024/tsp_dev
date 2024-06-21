package com.icbcaxa.eata.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * Created by tangzh on 2018/5/28.
 *
 * 访问授权码校验入参VO类
 *
 */
@Data
public class AccreditVO implements Serializable{

    private String id ;

    private String refGUID ;

    private String callSystem ;

    private String targetSystem ;

    private String targetBusinessModule ;

    private String targetBusinessEntrance ;

    private String systemTime ;

    private String startTime;

    private String endTime;

    private String validateTime ;

    private String accessToken ;

    private String secretKey ;

    private String createTime ;

    private String updateTime ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccreditVO that = (AccreditVO) o;

        if (!callSystem.equals(that.callSystem)) return false;
        if (!targetSystem.equals(that.targetSystem)) return false;
        if (!targetBusinessModule.equals(that.targetBusinessModule)) return false;
        return targetBusinessEntrance.equals(that.targetBusinessEntrance);

    }

    @Override
    public int hashCode() {
        int result = callSystem.hashCode();
        result = 31 * result + targetSystem.hashCode();
        result = 31 * result + targetBusinessModule.hashCode();
        result = 31 * result + targetBusinessEntrance.hashCode();
        return result;
    }
}
