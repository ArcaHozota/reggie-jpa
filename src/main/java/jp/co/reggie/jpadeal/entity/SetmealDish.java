package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 套餐與菜品關係實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "setmeal_dish")
public class SetmealDish extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -641135780975738908L;

	/**
	 * 套餐ID
	 */
	@Column(nullable = false)
	private Long setmealId;

	/**
	 * 菜品ID
	 */
	@Column(nullable = false)
	private Long dishId;

	/**
	 * 菜品名稱(冗餘字段)
	 */
	private String name;

	/**
	 * 菜品原價(冗餘字段)
	 */
	private BigDecimal price;

	/**
	 * 份數
	 */
	@Column(nullable = false)
	private Integer copies;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 邏輯刪除字段
	 */
	@Column(nullable = false)
	private String logicDeleteFlg;
}
