package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 菜品口味實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dish_flavor")
public class DishFlavor extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 6752106293794210881L;

	/**
	 * 菜品ID
	 */
	@Column(nullable = false)
	private Long dishId;

	/**
	 * 口味名稱
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 口味數據list
	 */
	private String value;

	/**
	 * 邏輯刪除字段
	 */
	@Column(nullable = false)
	private String logicDeleteFlg;
}
