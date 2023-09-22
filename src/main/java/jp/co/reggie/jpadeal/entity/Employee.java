package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
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
@NamedQuery(name = "Employee.selectByUserName", query = "select em from Employee em where em.username =:username")
public final class Employee implements Serializable {

	private static final long serialVersionUID = -6540113185665801143L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 姓名
	 */
	@Column(name = "name", nullable = false)
	private String kanjiName;

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

	/**
	 * 創建時間
	 */
	@Column(nullable = false)
	private LocalDateTime createdTime;

	/**
	 * 更新時間
	 */
	@Column(nullable = false)
	private LocalDateTime updatedTime;

	/**
	 * 創建人
	 */
	@Column(nullable = false)
	private Long createdUser;

	/**
	 * 修改者
	 */
	@Column(nullable = false)
	private Long updatedUser;
}
