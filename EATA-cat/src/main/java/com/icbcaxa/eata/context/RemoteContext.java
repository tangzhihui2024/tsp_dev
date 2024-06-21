package com.icbcaxa.eata.context;

import com.dianping.cat.Cat.Context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RemoteContext implements Context,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1588751197470682101L;
	
	private Map<String,String> properties = new HashMap<String,String>();

	@Override
	public void addProperty(String key, String value) {
		properties.put(key,value);
	}

	@Override
	public String getProperty(String key) {
		return properties.get(key);
	}

}
