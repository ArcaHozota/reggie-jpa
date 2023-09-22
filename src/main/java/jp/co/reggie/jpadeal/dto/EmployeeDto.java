package jp.co.reggie.jpadeal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public final class EmployeeDto {

	/**
	 * 社員ID
	 */
	private Long id;

	/**
	 * 漢字の名称
	 */
	private String name;

	/**
	 * 賬號名
	 */
	private String username;

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

	/**
	 * 創建時間
	 */
	private LocalDateTime createdTime;

	/**
	 * 更新時間
	 */
	private LocalDateTime updatedTime;

	/**
	 * 創建人
	 */
	private Long createdUser;

	/**
	 * 修改者
	 */
	private Long updatedUser;
}
