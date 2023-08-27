package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分類管理實體類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -5583580956537498025L;

	/**
	 * 類型：1、菜品分類；2、套餐分類；
	 */
	private Integer type;

	/**
	 * 分類名稱
	 */
	private String name;

	/**
	 * 順序
	 */
	private Integer sort;

	/**
	 * 邏輯刪除字段
	 */
	private String logicDeleteFlg;
}
