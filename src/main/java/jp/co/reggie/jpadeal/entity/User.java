package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 客戶信息實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 2324630650798877027L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手機號
	 */
	@Column(name = "phone_num", nullable = false)
	private String phoneNo;

	/**
	 * 性別：0女，1男
	 */
	@Column(name = "sex")
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
