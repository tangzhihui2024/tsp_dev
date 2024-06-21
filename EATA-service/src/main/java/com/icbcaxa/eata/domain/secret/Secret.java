package com.icbcaxa.eata.domain.secret;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * token秘钥
 * </p>
 *
 * @author yangzexu
 * @since 2018-05-30
 */
@Data
public class Secret implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;

	private String system;

	private String secretKey;

	private String type;

	private Date createTime;

	private Date updateTime;
}
