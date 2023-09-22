package jp.co.reggie.jpadeal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class EmployeeDto {

	/**
	 * 漢字の名称
	 */
	private String name;
}
