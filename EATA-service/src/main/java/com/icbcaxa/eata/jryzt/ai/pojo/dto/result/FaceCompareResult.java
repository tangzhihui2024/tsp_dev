package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class FaceCompareResult extends BaseResult {
	/**
	 * 照片与证件照相似度分数(数字越大相似度越高)
	 */
	private String similarity;
	/**
	 * 相似度参考阀值
	 */
	private String ref_thres;

	/**
	 * 获取照片与证件照相似度分数(数字越大相似度越高)
	 * @return the similarity
	 */
	public String getSimilarity() {
		return similarity;
	}

	/**
	 * 设置照片与证件照相似度分数(数字越大相似度越高)
	 * @param similarity
	 *            the similarity to set
	 */
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

	/**
	 * 获取相似度参考阀值
	 * @return the ref_thres
	 */
	public String getRef_thres() {
		return ref_thres;
	}
	
	/**
	 * 设置相似度参考阀值
	 * @param ref_thres the ref_thres to set
	 */
	public void setRef_thres(String ref_thres) {
		this.ref_thres = ref_thres;
	}
}
