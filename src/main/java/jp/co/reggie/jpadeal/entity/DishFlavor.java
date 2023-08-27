package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜品口味實體類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DishFlavor extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 6752106293794210881L;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 口味名稱
	 */
	private String name;

	/**
	 * 口味數據list
	 */
	private String value;

	/**
	 * 邏輯刪除字段
	 */
	private String logicDeleteFlg;
}
