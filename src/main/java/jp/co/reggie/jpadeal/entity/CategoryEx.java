package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分類管理擴展實體類
 *
 * @author ArkamaHozota
 * @since 3.20
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_extend")
public final class CategoryEx implements Serializable {

	private static final long serialVersionUID = 6025422703813573247L;

	/**
	 * ID
	 */
	@Column(nullable = false)
	private Long id;

	/**
	 * 分類名稱
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 菜品名稱
	 */
	private String dishName;

	/**
	 * 套餐ID
	 */
	private Long setmealId;

	/**
	 * 套餐名稱
	 */
	private String setmealName;
}
