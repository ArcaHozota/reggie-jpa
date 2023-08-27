package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 客戶信息實體類
 *
 * @author Administrator
 */
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 2324630650798877027L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手機號
	 */
	private String phoneNo;

	/**
	 * 性別：0女，1男
	 */
	private String gender;

	/**
	 * 身份證號
	 */
	private String idNumber;

	/**
	 * 頭像
	 */
	private String avatar;

	/**
	 * 狀態： 0禁用，1正常
	 */
	private Integer status;
}
