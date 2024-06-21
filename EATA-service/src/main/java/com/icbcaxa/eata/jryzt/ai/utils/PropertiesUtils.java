package com.icbcaxa.eata.jryzt.ai.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取Properties综合类,默认绑定到classpath下的config.properties文件。
 * @author GaoYu
 */
public class PropertiesUtils {

    /**
     * 读取properties的全部信息
     * Jun 26, 2010 9:21:01 PM
     * @author GaoYu
     * @throws FileNotFoundException 配置文件没有找到
     * @throws IOException 关闭资源文件，或者加载配置文件错误
     *
     */
    public Map<String,String> readAllProperties(Properties pros) throws FileNotFoundException,IOException  {
        //保存所有的键值
        Map<String,String> map=new HashMap<String,String>();
        Enumeration<?> en = pros.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String Property = pros.getProperty(key);
            map.put(key, Property);
        }
        return map;
    }
}
