package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 員工管理實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "employee")
public class Employee extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -6540113185665801143L;

	/**
	 * 姓名
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 賬號名
	 */
	@Column(nullable = false)
	private String username;

	/**
	 * 密碼
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * 手機號
	 */
	@Column(name = "phone_num", nullable = false)
	private String phoneNo;

	/**
	 * 性別
	 */
	@Column(name = "sex", nullable = false)
	private String gender;

	/**
	 * 身份證號
	 */
	@Column(nullable = false)
	private String idNumber;

	/**
	 * 賬號狀態：0:禁用，1:正常
	 */
	@Column(nullable = false)
	private Integer status;
}
