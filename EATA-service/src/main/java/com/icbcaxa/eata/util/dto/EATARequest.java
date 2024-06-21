package com.icbcaxa.eata.util.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EATARequest<T> implements Serializable {

	private static final long serialVersionUID = -9066973991292937567L;

	@NotNull(message = "head参数不能为空")
	@Valid
	private Head head;

	@Valid
	private T body;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}
