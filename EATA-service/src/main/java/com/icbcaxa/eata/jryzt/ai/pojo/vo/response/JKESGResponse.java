/**
 * 
 */
package com.icbcaxa.eata.jryzt.ai.pojo.vo.response;


import com.icbcaxa.eata.jryzt.ai.pojo.vo.BaseVo;

public class JKESGResponse<T> extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 响应码
	 */
	private String ret;
	
	/**
	 * 响应码说明
	 */
	private String msg;
	
	/**
	 * 响应业务数据
	 */
	private JKOpenAPIResponse<Object> data;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JKOpenAPIResponse<Object> getData() {
		return data;
	}

	public void setData(JKOpenAPIResponse<Object> data) {
		this.data = data;
	}
	
	
}
