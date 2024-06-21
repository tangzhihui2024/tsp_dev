package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class BioDetectResult extends BaseResult {
	/**
	 * 图片活体检测的分数
	 */
	private String bio_score;
	/**
	 * 图片中是否有人脸标识（返回字段：Have face, No face）
	 */
	private String face_detect_result;
	/**
	 * 活体检测结果（返回字段：true, false）
	 */
	private String is_alive;

	/**
	 * 获取图片活体检测的分数
	 * 
	 * @return the bio_score
	 */
	public String getBio_score() {
		return bio_score;
	}

	/**
	 * 设置图片活体检测的分数
	 * 
	 * @param bio_score
	 *            the bio_score to set
	 */
	public void setBio_score(String bio_score) {
		this.bio_score = bio_score;
	}

	/**
	 * 获取图片中是否有人脸标识（返回字段：Have face, No face）
	 * 
	 * @return the face_detect_result
	 */
	public String getFace_detect_result() {
		return face_detect_result;
	}

	/**
	 * 设置图片中是否有人脸标识（返回字段：Have face, No face）
	 * 
	 * @param face_detect_result
	 *            the face_detect_result to set
	 */
	public void setFace_detect_result(String face_detect_result) {
		this.face_detect_result = face_detect_result;
	}

	/**
	 * 获取活体检测结果（返回字段：true, false）
	 * 
	 * @return the is_alive
	 */
	public String getIs_alive() {
		return is_alive;
	}

	/**
	 * 设置活体检测结果（返回字段：true, false）
	 * 
	 * @param is_alive
	 *            the is_alive to set
	 */
	public void setIs_alive(String is_alive) {
		this.is_alive = is_alive;
	}
}
