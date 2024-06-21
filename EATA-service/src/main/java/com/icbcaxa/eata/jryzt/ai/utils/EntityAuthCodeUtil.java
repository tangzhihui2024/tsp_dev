package com.icbcaxa.eata.jryzt.ai.utils;

import org.apache.commons.lang.math.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntityAuthCodeUtil {

    public static String genEntityAuthCode() {
        StringBuffer entityAuthCode = new StringBuffer();
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmssSSS");
        String dateString = sdfDate.format(now);
        String timeString = sdfTime.format(now);
        StringBuffer randomString1 = new StringBuffer();
        StringBuffer randomString2 = new StringBuffer();
        StringBuffer randomString3 = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            char r = 'a';
            int n = RandomUtils.nextInt(52);
            if (n < 26) {
                r = (char) (65 + n);
            } else {
                n -= 26;
                if (n < 26) {
                    r = (char) (97 + n);
                }
            }
            if (i < 3) {
                randomString1.append(r);
            } else if (i >= 3 && i < 6) {
                randomString2.append(r);
            } else if (i >= 6 && i < 9) {
                randomString3.append(r);
            }
        }
        entityAuthCode.append(randomString1).append(dateString).append(randomString2).append(timeString).append(randomString3);
        return entityAuthCode.toString();
    }

}
