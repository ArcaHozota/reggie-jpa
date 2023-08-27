package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 員工管理實體類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -6540113185665801143L;

	/**
	 * 賬號名
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 密碼
	 */
	private String password;

	/**
	 * 手機號
	 */
	private String phoneNo;

	/**
	 * 性別
	 */
	private String gender;

	/**
	 * 身份證號
	 */
	private String idNumber;

	/**
	 * 賬號狀態：0:禁用，1:正常
	 */
	private Integer status;
}
