package jp.co.reggie.jpadeal.dto;

import jp.co.reggie.jpadeal.entity.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class EmployeeDto extends Employee {

	/**
	 * シリアルナンバー
	 */
	private static final long serialVersionUID = -4359708064533453621L;

	/**
	 * 漢字の名称
	 */
	private String name;
}
